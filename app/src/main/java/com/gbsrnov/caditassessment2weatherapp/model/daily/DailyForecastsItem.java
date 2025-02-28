package com.gbsrnov.caditassessment2weatherapp.model.daily;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DailyForecastsItem{

	@SerializedName("Temperature")
	private Temperature temperature;

	@SerializedName("EpochDate")
	private int epochDate;

	@SerializedName("HoursOfSun")
	private Object hoursOfSun;

	@SerializedName("Sources")
	private List<String> sources;

	@SerializedName("Date")
	private String date;


	@SerializedName("Day")
	private Day day;

	@SerializedName("Link")
	private String link;

	@SerializedName("MobileLink")
	private String mobileLink;

	public Temperature getTemperature(){
		return temperature;
	}

	public int getEpochDate(){
		return epochDate;
	}

	public Object getHoursOfSun(){
		return hoursOfSun;
	}

	public List<String> getSources(){
		return sources;
	}

	public String getDate(){
		return date;
	}

	public Day getDay(){
		return day;
	}

	public String getLink(){
		return link;
	}

	public String getMobileLink(){
		return mobileLink;
	}
}