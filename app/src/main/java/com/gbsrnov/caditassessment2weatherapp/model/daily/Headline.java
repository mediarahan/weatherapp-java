package com.gbsrnov.caditassessment2weatherapp.model.daily;

import com.google.gson.annotations.SerializedName;

public class Headline{

	@SerializedName("Category")
	private String category;

	@SerializedName("EndEpochDate")
	private int endEpochDate;

	@SerializedName("EffectiveEpochDate")
	private int effectiveEpochDate;

	@SerializedName("Severity")
	private int severity;

	@SerializedName("Text")
	private String text;

	@SerializedName("EndDate")
	private String endDate;

	@SerializedName("Link")
	private String link;

	@SerializedName("EffectiveDate")
	private String effectiveDate;

	@SerializedName("MobileLink")
	private String mobileLink;

	public String getCategory(){
		return category;
	}

	public int getEndEpochDate(){
		return endEpochDate;
	}

	public int getEffectiveEpochDate(){
		return effectiveEpochDate;
	}

	public int getSeverity(){
		return severity;
	}

	public String getText(){
		return text;
	}

	public String getEndDate(){
		return endDate;
	}

	public String getLink(){
		return link;
	}

	public String getEffectiveDate(){
		return effectiveDate;
	}

	public String getMobileLink(){
		return mobileLink;
	}
}