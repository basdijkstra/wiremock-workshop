package com.ontestautomation.workshops.wiremock.answers;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.http.Fault;

public class WireMockAnswers3 {
	
	public WireMockAnswers3() {
	}
	
	public void setupStubExercise301() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise301
		 * and responds to all GET requests with HTTP
		 * status code 503 and a status message equal to
		 * 'Service unavailable'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise301"))
				.willReturn(aResponse()
						.withStatus(503)
						.withStatusMessage("Service unavailable")
				));
	}
	
	public void setupStubExercise302() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise302
		 * and responds to all GET requests with a fixed
		 * delay of 2000 milliseconds
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise302"))
				.willReturn(aResponse()
						.withFixedDelay(2000)
				));
	}
	
	public void setupStubExercise303() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise303
		 * and responds to all GET requests with garbage
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise303"))
				.willReturn(aResponse()
						.withFault(Fault.RANDOM_DATA_THEN_CLOSE)
				));
	}
}
