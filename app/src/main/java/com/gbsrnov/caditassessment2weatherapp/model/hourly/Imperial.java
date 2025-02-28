package com.gbsrnov.caditassessment2weatherapp.model.hourly;

import com.google.gson.annotations.SerializedName;

public class Imperial{

	@SerializedName("UnitType")
	private int unitType;

	@SerializedName("Value")
	private int value;

	@SerializedName("Unit")
	private String unit;

	public int getUnitType(){
		return unitType;
	}

	public int getValue(){
		return value;
	}

	public String getUnit(){
		return unit;
	}
}