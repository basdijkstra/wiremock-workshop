package com.ontestautomation.workshops.wiremock.exercises;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class WireMockExercises4Tests {
	
	WireMockExercises4 wme = new WireMockExercises4();
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(9876);
	
	@Test
	public void testExercise401() {
		
		String url = "http://localhost:9876/exercise401";
        
	    wme.setupStubExercise401();
	         
	    given().
	    when().
	        get(url).
	    then().
	    	assertThat().
	    	statusCode(200).
	    and().
	    	body(equalTo("No light bulb found"));
	    
	    given().
	    	body("Insert light bulb").
	    when().
	        post(url).
	    then().
	    	assertThat().
	    	statusCode(200).
	    and().
	    	body(equalTo("Light bulb inserted"));
	    
	    given().
	    when().
	        get(url).
	    then().
	    	assertThat().
	    	statusCode(200).
	    and().
	    	body(equalTo("Light is OFF"));
	    
	    given().
	    	body("Switch light ON").
	    when().
	        post(url).
	    then().
	    	assertThat().
	    	statusCode(200).
	    and().
	    	body(equalTo("Light has been turned ON"));
	    
	    given().
	    when().
	        get(url).
	    then().
	    	assertThat().
	    	statusCode(200).
	    and().
	    	body(equalTo("Light is ON"));
	}
}
