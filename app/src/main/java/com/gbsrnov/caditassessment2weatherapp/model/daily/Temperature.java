package com.gbsrnov.caditassessment2weatherapp.model.daily;

import com.google.gson.annotations.SerializedName;

public class Temperature{

	@SerializedName("Minimum")
	private Minimum minimum;

	@SerializedName("Maximum")
	private Maximum maximum;

	public Minimum getMinimum(){
		return minimum;
	}

	public Maximum getMaximum(){
		return maximum;
	}
}