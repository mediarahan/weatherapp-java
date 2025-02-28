package com.gbsrnov.caditassessment2weatherapp.model.daily;

import com.google.gson.annotations.SerializedName;

public class Maximum{

	@SerializedName("UnitType")
	private int unitType;

	@SerializedName("Value")
	private Double value;

	@SerializedName("Unit")
	private String unit;

	@SerializedName("Phrase")
	private String phrase;

	public int getUnitType(){
		return unitType;
	}

	public Double getValue(){
		return value;
	}

	public String getUnit(){
		return unit;
	}

	public String getPhrase(){
		return phrase;
	}
}