package com.gbsrnov.caditassessment2weatherapp.model.daily;

import com.google.gson.annotations.SerializedName;

public class Speed {
	@SerializedName("UnitType")
	private int unitType;

	@SerializedName("Value")
	private float value; // ✅ FIXED: Changed from Object to float

	@SerializedName("Unit")
	private String unit;

	public int getUnitType(){
		return unitType;
	}

	public float getValue(){ // ✅ NOW RETURNS FLOAT
		return value;
	}

	public String getUnit(){
		return unit;
	}
}
