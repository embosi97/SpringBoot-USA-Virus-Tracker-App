package com.project.covid19Tracker.service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.lang.Math;

import com.project.covid19Tracker.models.LocationStats;

/**
 * This class will parse a CSV file containing daily confirmed 
 * Covid-19 cases in the USA on a daily basis and parse the data. 
 */
//So it will be scanned by the main class and will create an instance of this class
@Service
public class CovidDataService {
	
	private static String covidDataURL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv";
	
	private ArrayList<LocationStats> locations = new ArrayList<>();
	
	private int weeklyCases;
	
	//When the application start, this will be executed
	//Execute the task every minute starting at 9:00 AM and ending at 9:59 AM
	@PostConstruct
	@Scheduled(cron = "0 * 9 * * ?")
	public ArrayList<LocationStats> parseCSVData() throws IOException, InterruptedException {
		//An HttpClient can be used to send requests and retrieve their responses.
		HttpClient httpClient = HttpClient.newHttpClient();
		
		HttpRequest httpRequest = HttpRequest.newBuilder()
		        .uri(URI.create(covidDataURL))
		        .header("Content-Type", "text/csv").build();
		
		//Response is stored 
		HttpResponse <String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		return parseRawCSVData(httpResponse);
	}
	
	//Apache Commons CSV can parse the header names from the first record
	@SuppressWarnings({"deprecation"})
	public ArrayList<LocationStats> parseRawCSVData(HttpResponse<String> response) throws IOException {
				
		Reader reader = new StringReader(response.body());

		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);

		for (CSVRecord record : records) {
			
			LocationStats statistic = new LocationStats();
			
			weeklyCases = 0;
			
			for(int i = 0; i < 7; i++) {
				weeklyCases += Integer.parseInt(record.get(record.size() -1 - i));
			}
						
			statistic.setLocation(record.get("Combined_Key"));
			
			statistic.setDailyCases(Integer.parseInt(record.get(record.size()-1)));
			
			statistic.setWeeklyCases(weeklyCases);
			
			statistic.setAverageCases((Integer)Math.round(weeklyCases/7));
			
			locations.add(statistic);
			
		}
		
		//Collections.sort(locations);
		
		return locations;
	
	}

}
