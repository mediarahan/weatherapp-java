package com.gbsrnov.caditassessment2weatherapp.model.daily;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DailyForecastsItem{

	@SerializedName("Temperature")
	private Temperature temperature;

	@SerializedName("Date")
	private String date;

	@SerializedName("Day")
	private Day day;

	public Temperature getTemperature(){
		return temperature;
	}

	public String getDate(){
		return date;
	}

	public Day getDay(){
		return day;
	}
}