package com.gbsrnov.caditassessment2weatherapp.model.daily;

import com.google.gson.annotations.SerializedName;

public class Day{
	@SerializedName("Wind")
	private Wind wind;

	@SerializedName("Rain")
	private Rain rain;

	@SerializedName("RelativeHumidity")
	private RelativeHumidity relativeHumidity;

	public Wind getWind(){
		return wind;
	}

	public Rain getRain(){
		return rain;
	}

	public RelativeHumidity getRelativeHumidity(){
		return relativeHumidity;
	}
}