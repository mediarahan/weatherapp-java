package com.gbsrnov.caditassessment2weatherapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gbsrnov.caditassessment2weatherapp.model.daily.DailyForecastsItem;
import com.gbsrnov.caditassessment2weatherapp.model.hourly.ResponseItem;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.levitnudi.legacytableview.LegacyTableView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private BarChart barChart;
    private RadioGroup barChartRadioGroup;
    private List<BarEntry> tempEntries = new ArrayList<>();
    private List<BarEntry> humidityEntries = new ArrayList<>();
    private List<BarEntry> pressureEntries = new ArrayList<>();

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeViewModel homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        TextView temperatureTv = view.findViewById(R.id.temp_data_text_view);
        TextView humidityTv = view.findViewById(R.id.humidity_data_text_view);
        TextView pressureTv = view.findViewById(R.id.pressure_data_text_view);

        LegacyTableView.insertLegacyTitle("Date", "Min Temp (°C)", "Max Temp (°C)", "Humidity (%)", "Wind Speed (km/h)");

        //homeViewModel.fetchcurrentWeatherData();

        homeViewModel.getWeatherData().observe(getViewLifecycleOwner(), responseItems -> {
            if (responseItems != null) {
                ResponseItem item = responseItems;
                temperatureTv.setText(item.getTemperature().getMetric().getValue() + "°C");
                humidityTv.setText(item.getRelativeHumidity() + "%");
                pressureTv.setText(item.getPressure().getMetric().getValue() + " hPa");

            } else {
                Toast.makeText(getContext(), "No results fetched from API", Toast.LENGTH_SHORT).show();
            }
        });

        //=== Bar Chart ===
        barChart = view.findViewById(R.id.barChart);
        barChartRadioGroup = view.findViewById(R.id.chartRadioGroup);
        setupBarChart();

        //homeViewModel.fetchHourlyWeatherData();

        homeViewModel.getHourlyWeatherData().observe(getViewLifecycleOwner(), responseItems -> {
            if (responseItems != null && !responseItems.isEmpty()) {
                tempEntries.clear();
                humidityEntries.clear();
                pressureEntries.clear();

                for (int i = 0; i < responseItems.size(); i++) {
                    float temp = responseItems.get(i).getTemperature().getMetric().getValue().floatValue();
                    float humidity = responseItems.get(i).getRelativeHumidity();
                    float pressure = responseItems.get(i).getPressure().getMetric().getValue().floatValue();

                    tempEntries.add(new BarEntry(i, temp));
                    humidityEntries.add(new BarEntry(i, humidity));
                    pressureEntries.add(new BarEntry(i, pressure));
                }

                // Default: Show Temperature chart
                updateBarChart(tempEntries, "Temperature (°C)", R.color.temperature);
            } else {
                Log.e("HistoricalData", "No historical data fetched from API");
            }
        });

        LegacyTableView legacyTableView = view.findViewById(R.id.legacy_table_view);

        homeViewModel.fetchDailyWeatherData();

        homeViewModel.getDailyWeatherData().observe(getViewLifecycleOwner(), responseItems -> {
            if (responseItems != null && !responseItems.isEmpty()) {
                for (DailyForecastsItem item : responseItems) {
                    // Extract weather data safely
                    String date = item.getDate();
                    String minTemp = item.getTemperature() != null ? String.valueOf(item.getTemperature().getMinimum().getValue()) : "N/A";
                    String maxTemp = item.getTemperature() != null ? String.valueOf(item.getTemperature().getMaximum().getValue()) : "N/A";
                    String humidity = item.getDay() != null && item.getDay().getRelativeHumidity() != null ?
                            String.valueOf(item.getDay().getRelativeHumidity().getAverage()) : "N/A";
                    String windSpeed = item.getDay() != null && item.getDay().getWind() != null &&
                            item.getDay().getWind().getSpeed() != null ?
                            String.valueOf(item.getDay().getWind().getSpeed().getValue()) : "N/A";

                    // Insert row data
                    LegacyTableView.insertLegacyContent(date, minTemp, maxTemp, humidity, windSpeed);
                }

                // Set table data and build the table
                legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
                legacyTableView.setContent(LegacyTableView.readLegacyContent());
                legacyTableView.build();
            } else {
                Log.e("LegacyTableView", "No weather data available");
            }
        });



        homeViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        barChartRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbTemperature) {
                updateBarChart(tempEntries, "Temperature (°C)", R.color.temperature);
            } else if (checkedId == R.id.rbHumidity) {
                updateBarChart(humidityEntries, "Humidity (%)", R.color.humidity);
            } else if (checkedId == R.id.rbPressure) {
                updateBarChart(pressureEntries, "Pressure (hPa)", R.color.pressure);
            }
        });

    }

    private void setupBarChart() {
        barChart.getDescription().setEnabled(false); // Remove description label
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(6);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // Ensure no negative values
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false); // Hide right Y-axis

        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setDrawInside(false);
    }

    private void updateBarChart(List<BarEntry> entries, String label, int colorResId) {
        BarDataSet dataSet = new BarDataSet(entries, label);
        dataSet.setColor(ContextCompat.getColor(requireContext(), colorResId));

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.5f);

        barChart.setData(barData);
        barChart.invalidate();
    }

}