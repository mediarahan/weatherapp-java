package com.gbsrnov.caditassessment2weatherapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gbsrnov.caditassessment2weatherapp.model.daily.DailyForecastsItem;
import com.gbsrnov.caditassessment2weatherapp.model.daily.DailyWeatherResponse;
import com.gbsrnov.caditassessment2weatherapp.model.hourly.TodayWeatherResponse;
import com.gbsrnov.caditassessment2weatherapp.remote.ApiConfig;
import com.gbsrnov.caditassessment2weatherapp.model.hourly.ResponseItem;
import com.levitnudi.legacytableview.LegacyTableView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    private final MutableLiveData<ResponseItem> _weatherData = new MutableLiveData<>();

    public LiveData<ResponseItem> getWeatherData() {
        return _weatherData;
    }

    private final MutableLiveData<List<ResponseItem>> _hourlyWeatherData = new MutableLiveData<>();

    public LiveData<List<ResponseItem>> getHourlyWeatherData() {
        return _hourlyWeatherData;
    }

    private final MutableLiveData<List<DailyForecastsItem>> _dailyWeatherData = new MutableLiveData<>();

    public LiveData<List<DailyForecastsItem>> getDailyWeatherData() {
        return _dailyWeatherData;
    }
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading() {
        return _isLoading;
    }

    public void fetchcurrentWeatherData() {
        _isLoading.setValue(true);
        final Call<List<TodayWeatherResponse>> client =
                ApiConfig.getApiService().getCurrentWeatherData("208977", true);

        client.enqueue(new Callback<List<TodayWeatherResponse>>() {
            @Override
            public void onResponse(Call<List<TodayWeatherResponse>> call, Response<List<TodayWeatherResponse>> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    TodayWeatherResponse weatherResponse = response.body().get(0); // Extract first object
                    _weatherData.setValue(weatherResponse.toResponseItem()); // Convert to ResponseItem
                } else {
                    Log.e(TAG, "API returned an empty list");
                }
            }

            @Override
            public void onFailure(Call<List<TodayWeatherResponse>> call, Throwable t) {
                _isLoading.setValue(false);
                Log.e(TAG, "API call failed: " + t.getMessage(), t);
            }
        });

    }

    public void fetchHourlyWeatherData() {
        _isLoading.setValue(true);
        final Call<List<TodayWeatherResponse>> client =
                ApiConfig.getApiService().getHourlyWeatherData("208977", true);

        client.enqueue(new Callback<List<TodayWeatherResponse>>() {
            @Override
            public void onResponse(Call<List<TodayWeatherResponse>> call, Response<List<TodayWeatherResponse>> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<ResponseItem> responseItems = new ArrayList<>();
                    for (TodayWeatherResponse weatherResponse : response.body()) {
                        responseItems.add(weatherResponse.toResponseItem());  // Convert each to ResponseItem
                    }
                    _hourlyWeatherData.setValue(responseItems);
                } else {
                    Log.e(TAG, "API returned an empty hourly list");
                }
            }

            @Override
            public void onFailure(Call<List<TodayWeatherResponse>> call, Throwable t) {
                _isLoading.setValue(false);
                Log.e(TAG, "Hourly API call failed: " + t.getMessage(), t);
            }
        });
    }

    public void fetchDailyWeatherData() {
        _isLoading.setValue(true);

        final Call<DailyWeatherResponse> client =
                ApiConfig.getApiService().getDailyWeatherData("208977", true, true);

        client.enqueue(new Callback<DailyWeatherResponse>() {
            @Override
            public void onResponse(Call<DailyWeatherResponse> call, Response<DailyWeatherResponse> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<DailyForecastsItem> dailyForecasts = response.body().getDailyForecasts();
                    if (dailyForecasts != null && !dailyForecasts.isEmpty()) {
                        _dailyWeatherData.setValue(dailyForecasts);
                    } else {
                        Log.e(TAG, "API returned an empty daily forecast list");
                    }
                } else {
                    Log.e(TAG, "API returned an empty object");
                }
            }

            @Override
            public void onFailure(Call<DailyWeatherResponse> call, Throwable t) {
                _isLoading.setValue(false);
                Log.e(TAG, "Daily API call failed: " + t.getMessage(), t);
            }
        });
    }




}
