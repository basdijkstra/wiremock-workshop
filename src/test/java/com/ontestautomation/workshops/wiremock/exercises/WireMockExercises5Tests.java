package com.ontestautomation.workshops.wiremock.exercises;

import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.http.ContentType;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class WireMockExercises5Tests {
	
	WireMockExercises5 wme = new WireMockExercises5();

	@Rule
	public WireMockRule wm = new WireMockRule(wireMockConfig()
			.port(9876)
			.extensions(new ResponseTemplateTransformer(false))
	);
	
	@Test
	public void testExercise501() {

	    wme.setupStubExercise501();
	         
	    given().
	    when().
	        get("http://localhost:9876/exercise501").
	    then().
	    	assertThat().
	    	statusCode(200).
	    and().
	    	body(equalTo("9876"));
	}

	@Test
	public void testExercise502() {

		wme.setupStubExercise502();

		given().
			queryParam("search", "banana").
		when().
			get("http://localhost:9876/exercise502").
		then().
			assertThat().
			statusCode(200).
		and().
			body(equalTo("banana"));
	}

	@Test
	public void testExercise503() {

		wme.setupStubExercise503();

		given().
			contentType(ContentType.JSON).
			body("{\"car\": {\"make\": \"Alfa Romeo\", \"model\": \"Giulia 2.9 V6 Quadrifoglio\",\"top_speed\": 307}}").
		when().
			post("http://localhost:9876/exercise503").
		then().
			assertThat().
			body(equalTo("Giulia 2.9 V6 Quadrifoglio"));
	}
}
