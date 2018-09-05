package com.ontestautomation.workshops.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockExercises5 {

	public WireMockExercises5() {
	}
	
	public void setupStubExercise501() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise501
		 * and responds to all GET requests with HTTP
		 * status code 200 and a response body containing
		 * the port number on which the request was
		 * received.
		 * Don't forget to enable response templating!
		 ************************************************/

	}

	public void setupStubExercise502() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise502
		 * and responds to all GET requests with HTTP
		 * status code 200 and a response body containing
		 * the value of the query parameter 'search' (no quotes).
		 * Don't forget to enable response templating!
		 * HINT: you'll need to use urlPathEqualTo here,
		 * not urlEqualTo to achieve request matching (why?)
		 ************************************************/

	}

	public void setupStubExercise503() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise503
		 * and responds to all POST requests with HTTP
		 * status code 200 and a response body containing
		 * the value of the JSON element car.model extracted
		 * from the request body
		 ************************************************/

	}
}
