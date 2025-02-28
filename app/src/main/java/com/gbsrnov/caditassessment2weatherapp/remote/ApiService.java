package com.gbsrnov.caditassessment2weatherapp.remote;

import com.gbsrnov.caditassessment2weatherapp.model.daily.DailyWeatherResponse;
import com.gbsrnov.caditassessment2weatherapp.model.hourly.TodayWeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("currentconditions/v1/{locationKey}")
    Call<List<TodayWeatherResponse>> getCurrentWeatherData(
            @Path("locationKey") String locationKey,
            @Query("details") boolean details
    );

    @GET("currentconditions/v1/{locationKey}/historical")
    Call<List<TodayWeatherResponse>> getHourlyWeatherData(
            @Path("locationKey") String locationKey,
            @Query("details") boolean details
    );

    @GET("forecasts/v1/daily/5day/{locationKey}")
    Call<DailyWeatherResponse> getDailyWeatherData(
            @Path("locationKey") String locationKey,
            @Query("details") boolean details,
            @Query("metric") boolean metric
    );



}

//key Qzf5OndsbgiC3MCE5hivnYHjK3bvdfji
//city code 208977