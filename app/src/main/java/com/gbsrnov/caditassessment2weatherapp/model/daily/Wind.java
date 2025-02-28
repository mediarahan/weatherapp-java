package com.gbsrnov.caditassessment2weatherapp.model.daily;

import com.google.gson.annotations.SerializedName;

public class Wind{

	@SerializedName("Speed")
	private Speed speed;

	public Speed getSpeed(){
		return speed;
	}
}