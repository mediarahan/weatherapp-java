package com.gbsrnov.caditassessment2weatherapp.model.daily;

import com.google.gson.annotations.SerializedName;

public class RelativeHumidity{

	@SerializedName("Minimum")
	private int minimum;

	@SerializedName("Maximum")
	private int maximum;

	@SerializedName("Average")
	private int average;

	public int getMinimum(){
		return minimum;
	}

	public int getMaximum(){
		return maximum;
	}

	public int getAverage(){
		return average;
	}
}