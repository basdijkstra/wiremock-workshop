package com.ontestautomation.workshops.wiremock;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class WireMockExamplesTests {
	
	WireMockExamples wme = new WireMockExamples();	
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(9876);
	
	@Test
	public void testPingPongPositive() {
        
	    wme.setupExampleStub();
	         
	    given().
	        body("<input>PING</input>").
	    when().
	        post("http://localhost:9876/pingpong").
	    then().
	    	log().
	    	body().
	    	and().
	        assertThat().
	        statusCode(200).
	        and().
	        body("output", equalTo("PONG"));
	}
	
	@Test
	public void testURLMatching() {
		
		wme.setupStubURLMatching();
		
		given().
		when().
			get("http://localhost:9876/urlmatching").
		then().
			assertThat().
			body(equalTo("URL matching"));
	}
	
	@Test
	public void testRequestBodyMatching() {
		
		wme.setupStubRequestBodyMatching();
		
		given().
			body("TestRequestBodyMatching").
		when().
			post("http://localhost:9876/requestbodymatching").
		then().
			assertThat().
			body(equalTo("Request body matching"));
	}
	
	@Test
	public void testHeaderMatching() {
		
		wme.setupStubHeaderMatching();
		
		given().			
			contentType("application/json").
		when().
			get("http://localhost:9876/headermatching").
		then().
			assertThat().
			body(equalTo("Header matching"));
	}
	
	@Test
	public void testAuthorizationMatching() {
		
		wme.setupStubAuthorizationMatching();
		
		given().			
			auth().
			preemptive().
			basic("username", "password").
		when().
			get("http://localhost:9876/authorizationmatching").
		then().
			assertThat().
			body(equalTo("Authorization matching"));
	}
	
	@Test
	public void testErrorCode() {
		
		wme.setupStubReturningErrorCode();
		
		given().
		when().
			get("http://localhost:9876/errorcode").
		then().
			assertThat().
			statusCode(500);
	}
	
	@Test
	public void testFixedDelay() {
		
		wme.setupStubFixedDelay();
		
		given().
		when().
			get("http://localhost:9876/fixeddelay").
		then().
			assertThat().
			time(greaterThan(2000L),TimeUnit.MILLISECONDS);
			
	}
	
	@Test
	public void testStatefulStub() {
		
		wme.setupStubStateful();
		
		given().
		when().
			get("http://localhost:9876/order").
		then().
			assertThat().
			body(equalTo("Your shopping cart is empty"));
		
		given().
			body("Ordering 1 item").
		when().
			post("http://localhost:9876/order").
		then().
			assertThat().
			body(equalTo("Item placed in shopping cart"));
		
		given().
		when().
			get("http://localhost:9876/order").
		then().
			assertThat().
			body(equalTo("There is 1 item in your shopping cart"));
	}
}
