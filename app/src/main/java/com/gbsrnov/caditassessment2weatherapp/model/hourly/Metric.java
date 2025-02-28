package com.gbsrnov.caditassessment2weatherapp.model.hourly;

import com.google.gson.annotations.SerializedName;

public class Metric{

	@SerializedName("UnitType")
	private int unitType;

	@SerializedName("Value")
	private Double value;

	@SerializedName("Unit")
	private String unit;

	public int getUnitType(){
		return unitType;
	}

	public Double getValue(){
		return value;
	}

	public String getUnit(){
		return unit;
	}
}