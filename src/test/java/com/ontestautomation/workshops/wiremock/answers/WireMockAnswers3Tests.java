package com.ontestautomation.workshops.wiremock.answers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.apache.http.client.ClientProtocolException;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class WireMockAnswers3Tests {
	
	WireMockAnswers3 wma = new WireMockAnswers3();
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(9876);
	
	@Test
	public void testExercise301() {
        
	    wma.setupStubExercise301();
	         
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
        
	    wma.setupStubExercise302();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise302").
	    then().
	        assertThat().
	        time(greaterThan(2000L));
	}
	
	@Test(expected = ClientProtocolException.class)
	public void testExercise303() {
        
	    wma.setupStubExercise303();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise303").
	    then();
	}
}
