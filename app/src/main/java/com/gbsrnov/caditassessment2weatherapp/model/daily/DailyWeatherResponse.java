package com.gbsrnov.caditassessment2weatherapp.model.daily;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DailyWeatherResponse{

	@SerializedName("Headline")
	private Headline headline;

	@SerializedName("DailyForecasts")
	private List<DailyForecastsItem> dailyForecasts;

	public Headline getHeadline(){
		return headline;
	}

	public List<DailyForecastsItem> getDailyForecasts(){
		return dailyForecasts;
	}
}