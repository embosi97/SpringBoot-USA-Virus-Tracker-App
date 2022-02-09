package com.project.covid19Tracker.models;

//POJO for Covid-19 statistics based on location 
public class LocationStats implements Comparable<LocationStats>{
	
	private String location;
	
	private int dailyCases;
	
	private int weeklyCases;
	
	private int averageCases;

	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getDailyCases() {
		return dailyCases;
	}

	public void setDailyCases(int dailyCases) {
		this.dailyCases = dailyCases;
	}

	public int getWeeklyCases() {
		return weeklyCases;
	}

	public void setWeeklyCases(int weeklyCases) {
		this.weeklyCases = weeklyCases;
	}

	public float getAverageCases() {
		return averageCases;
	}

	public void setAverageCases(int averageCases) {
		this.averageCases = averageCases;
	}

	@Override
	public String toString() {
		return "Location= " + location + ", Daily Cases= " + dailyCases + ", Weekly Cases= " + weeklyCases
				+ ", Average Cases= " + averageCases + "\n";
	}
	
	@Override
	public int compareTo(LocationStats stats) {
		int compareDailyCases = ((LocationStats) stats).getDailyCases();
		return compareDailyCases - this.dailyCases;
	}

	
}
