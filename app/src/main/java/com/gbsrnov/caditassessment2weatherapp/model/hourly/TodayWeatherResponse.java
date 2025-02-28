package com.gbsrnov.caditassessment2weatherapp.model.hourly;

import com.google.gson.annotations.SerializedName;

public class TodayWeatherResponse {
	@SerializedName("Temperature")
	private Temperature temperature;

	@SerializedName("RelativeHumidity")
	private int relativeHumidity;

	@SerializedName("Pressure")
	private Pressure pressure;

	public ResponseItem toResponseItem() {
		return new ResponseItem(temperature, relativeHumidity, pressure);
	}
}
