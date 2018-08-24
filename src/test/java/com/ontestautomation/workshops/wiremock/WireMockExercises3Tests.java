package com.ontestautomation.workshops.wiremock;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.apache.http.client.ClientProtocolException;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class WireMockExercises3Tests {
	
	WireMockExercises3 wme = new WireMockExercises3();
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(9876);
	
	@Test
	public void testExercise301() {
        
	    wme.setupStubExercise301();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise301").
	    then().
	    	assertThat().
	    	statusCode(503).
	    and().
	    	statusLine(containsString("Service unavailable"));	    	
	}
	
	@Test
	public void testExercise302() {
        
	    wme.setupStubExercise302();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise302").
	    then().
	        assertThat().
	        time(greaterThan(2000L));
	}
	
	@Test(expected = ClientProtocolException.class)
	public void testExercise303() {
        
	    wme.setupStubExercise303();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise303").
	    then();        
	}
}
