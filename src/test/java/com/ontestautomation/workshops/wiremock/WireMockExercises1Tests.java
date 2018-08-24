package com.ontestautomation.workshops.wiremock;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class WireMockExercises1Tests {
	
	WireMockExercises1 wme = new WireMockExercises1();
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(9876);
	
	@Test
	public void testExercise101() {
        
	    wme.setupStubExercise101();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise101").
	    then().
	    	log().
	    	body().
	    	and().
	        assertThat().
	        statusCode(200);
	}
	
	@Test
	public void testExercise102() {
        
	    wme.setupStubExercise102();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise102").
	    then().
	    	log().
	    	body().
	    	and().
	        assertThat().
	        contentType(ContentType.TEXT);
	}
	
	@Test
	public void testExercise103() {
        
	    wme.setupStubExercise103();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise103").
	    then().
	    	log().
	    	body().
	    	and().
	        assertThat().
	        body(equalTo("Exercise 103"));
	}
}
