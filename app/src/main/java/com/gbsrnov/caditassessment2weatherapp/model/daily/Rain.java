package com.gbsrnov.caditassessment2weatherapp.model.daily;

import com.google.gson.annotations.SerializedName;

public class Rain{

	@SerializedName("UnitType")
	private int unitType;

	@SerializedName("Value")
	private Object value;

	@SerializedName("Unit")
	private String unit;

	public int getUnitType(){
		return unitType;
	}

	public Object getValue(){
		return value;
	}

	public String getUnit(){
		return unit;
	}
}