package com.gbsrnov.caditassessment2weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gbsrnov.caditassessment2weatherapp.model.daily.DailyForecastsItem;
import com.gbsrnov.caditassessment2weatherapp.MainActivity.BottomButtonClickListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.levitnudi.legacytableview.LegacyTableView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements BottomButtonClickListener {
    private BarChart barChart;
    private List<BarEntry> temperatureEntries = new ArrayList<>();
    private List<BarEntry> humidityEntries = new ArrayList<>();
    private List<BarEntry> pressureEntries = new ArrayList<>();

    //viewModel
    private HomeViewModel homeViewModel;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // ViewModel initialization
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        TextView temperatureTv = view.findViewById(R.id.temp_data_text_view);
        TextView humidityTv = view.findViewById(R.id.humidity_data_text_view);
        TextView pressureTv = view.findViewById(R.id.pressure_data_text_view);
        barChart = view.findViewById(R.id.barChart);
        RadioGroup barChartRadioGroup = view.findViewById(R.id.chartRadioGroup);
        LegacyTableView legacyTableView = view.findViewById(R.id.legacy_table_view);

        setupBarChart();
        LegacyTableView.insertLegacyTitle("Date", "Min Temp (°C)", "Max Temp (°C)", "Humidity (%)", "Wind Speed (km/h)");

        // Observe current weather data
        homeViewModel.fetchCurrentWeatherData();
        homeViewModel.getWeatherData().observe(getViewLifecycleOwner(), item -> {
            if (item != null) {
                temperatureTv.setText(item.getTemperature().getMetric().getValue() + "°C");
                humidityTv.setText(item.getRelativeHumidity() + "%");
                pressureTv.setText(item.getPressure().getMetric().getValue() + " hPa");
            } else {
                Toast.makeText(getContext(), "No results fetched from API", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe hourly weather data
        homeViewModel.fetchHourlyWeatherData();
        homeViewModel.getHourlyWeatherData().observe(getViewLifecycleOwner(), responseItems -> {
            if (responseItems != null && !responseItems.isEmpty()) {
                temperatureEntries.clear();
                humidityEntries.clear();
                pressureEntries.clear();

                for (int i = 0; i < responseItems.size(); i++) {
                    temperatureEntries.add(new BarEntry(i, responseItems.get(i).getTemperature().getMetric().getValue().floatValue()));
                    humidityEntries.add(new BarEntry(i, responseItems.get(i).getRelativeHumidity()));
                    pressureEntries.add(new BarEntry(i, responseItems.get(i).getPressure().getMetric().getValue().floatValue()));
                }

                updateBarChart(temperatureEntries, "Temperature (°C)", R.color.temperature);
            } else {
                Log.e("HistoricalData", "No historical data fetched from API");
            }
        });

        // Observe daily weather data
        homeViewModel.fetchDailyWeatherData();
        homeViewModel.getDailyWeatherData().observe(getViewLifecycleOwner(), responseItems -> {
            if (responseItems != null && !responseItems.isEmpty()) {

                for (DailyForecastsItem item : responseItems) {
                    LegacyTableView.insertLegacyContent(
                            DateUtil.formatDate(item.getDate()),
                            String.valueOf(item.getTemperature().getMinimum().getValue()),
                            String.valueOf(item.getTemperature().getMaximum().getValue()),
                            item.getDay() != null && item.getDay().getRelativeHumidity() != null ? String.valueOf(item.getDay().getRelativeHumidity().getAverage()) : "N/A",
                            item.getDay() != null && item.getDay().getWind() != null && item.getDay().getWind().getSpeed() != null ? String.valueOf(item.getDay().getWind().getSpeed().getValue()) : "N/A"
                    );
                }
                legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
                legacyTableView.setContent(LegacyTableView.readLegacyContent());
                legacyTableView.build();
            } else {
                Log.e("LegacyTableView", "No weather data available");
            }
        });

        // Observe loading state
        homeViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        // Handle bar chart radio group selection
        barChartRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbTemperature) {
                updateBarChart(temperatureEntries, "Temperature (°C)", R.color.temperature);
            } else if (checkedId == R.id.rbHumidity) {
                updateBarChart(humidityEntries, "Humidity (%)", R.color.humidity);
            } else if (checkedId == R.id.rbPressure) {
                updateBarChart(pressureEntries, "Pressure (hPa)", R.color.pressure);
            }
        });
    }

    private void setupBarChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(6);

        String[] labels = {"-1h", "-2h", "-3h", "-4h", "-5h", "-6h"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(1f);
        barChart.getAxisRight().setEnabled(false);

        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }

    private void updateBarChart(List<BarEntry> entries, String label, int colorResId) {
        BarDataSet dataSet = new BarDataSet(entries, label);
        dataSet.setColor(ContextCompat.getColor(requireContext(), colorResId));
        barChart.setData(new BarData(dataSet));
        barChart.invalidate();
    }

    @Override
    public void onCloseApp() {
        requireActivity().finish();
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getContext(), "Refreshing data...", Toast.LENGTH_SHORT).show();

        homeViewModel.fetchCurrentWeatherData();
        homeViewModel.fetchHourlyWeatherData();
        homeViewModel.fetchDailyWeatherData();
    }
}
