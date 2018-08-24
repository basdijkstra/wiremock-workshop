package com.ontestautomation.workshops.wiremock;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class WireMockExercises2Tests {
	
	WireMockExercises2 wme = new WireMockExercises2();
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(9876);
	
	@Test
	public void testExercise201() {
        
	    wme.setupStubExercise201();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise201").
	    then().
	    	assertThat().
	    	body(equalTo("Exercise 201 passed"));
	}
	
	@Test
	public void testExercise202() {
        
	    wme.setupStubExercise202();
	         
	    given().
	    	body("DefineExercise202BodyLikeThis").
	    when().
	        get("http://localhost:9876/exercise202").
	    then().
	        assertThat().
	        body(equalTo("Exercise 202 passed"));
	}
	
	@Test
	public void testExercise203() {
        
	    wme.setupStubExercise203();
	         
	    given().
	    	header("MyHeader","MyHeaderValue").
	    when().
	        get("http://localhost:9876/exercise203").
	    then().
	        assertThat().
	        body(equalTo("Exercise 203 passed"));
	}
	
	@Test
	public void testExercise204() {
        
	    wme.setupStubExercise204();
	         
	    given().
	    	auth().
	    	preemptive().
	    	basic("wiremock","workshop").
	    when().
	        get("http://localhost:9876/exercise204").
	    then().
	        assertThat().
	        body(equalTo("Exercise 204 passed"));
	}
	
	@Test
	public void testExercise205() {
        
	    wme.setupStubExercise205();
	         
	    given().
	    	cookie("MyCookie","ChocolateChip").
	    when().
	        get("http://localhost:9876/exercise205").
	    then().
	        assertThat().
	        body(equalTo("Exercise 205 passed"));
	}
}
