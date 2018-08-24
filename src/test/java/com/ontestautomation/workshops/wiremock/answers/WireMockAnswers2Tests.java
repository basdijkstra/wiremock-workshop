package com.ontestautomation.workshops.wiremock.answers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class WireMockAnswers2Tests {
	
	WireMockAnswers2 wma = new WireMockAnswers2();
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(9876);
	
	@Test
	public void testExercise201() {
        
	    wma.setupStubExercise201();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise201").
	    then().
	    	assertThat().
	    	body(equalTo("Exercise 201 passed"));
	}
	
	@Test
	public void testExercise202() {
        
	    wma.setupStubExercise202();
	         
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
        
	    wma.setupStubExercise203();
	         
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
        
	    wma.setupStubExercise204();
	         
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
        
	    wma.setupStubExercise205();
	         
	    given().
	    	cookie("MyCookie","ChocolateChip").
	    when().
	        get("http://localhost:9876/exercise205").
	    then().
	        assertThat().
	        body(equalTo("Exercise 205 passed"));
	}
}
