package com.project.covid19Tracker.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.covid19Tracker.models.LocationStats;
import com.project.covid19Tracker.service.CovidDataService;
import java.text.DateFormat;  
import java.util.Date;  

@Controller
@RequestMapping("/")
public class CovidTrackerController {
	
	public CovidDataService data;
	
	public String todaysDate;
	
	public CovidTrackerController(@Autowired CovidDataService data) {
		this.data = data;
	}
	
	
	@GetMapping("/")
	public String getStats(Model theModel) throws IOException, InterruptedException {
		
		ArrayList<LocationStats> locationStats = data.parseCSVData();
		
		String todaysDate = DateFormat.getInstance().format(new Date());  
		
		theModel.addAttribute("locationStats", locationStats);
		
		theModel.addAttribute("theDate", todaysDate);
		
		return "us-covid-stats";
		
	}

}
