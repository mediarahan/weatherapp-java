package com.gbsrnov.caditassessment2weatherapp.model.hourly;

import com.google.gson.annotations.SerializedName;

public class ResponseItem {

	@SerializedName("Temperature")
	private Temperature temperature;

	@SerializedName("RelativeHumidity")
	private int relativeHumidity;

	@SerializedName("Pressure")
	private Pressure pressure;

	public ResponseItem(Temperature temperature, int relativeHumidity, Pressure pressure) {
		this.temperature = temperature;
		this.relativeHumidity = relativeHumidity;
		this.pressure = pressure;
	}

	public Temperature getTemperature(){
		return temperature;
	}

	public int getRelativeHumidity(){
		return relativeHumidity;
	}

	public Pressure getPressure(){
		return pressure;
	}
}