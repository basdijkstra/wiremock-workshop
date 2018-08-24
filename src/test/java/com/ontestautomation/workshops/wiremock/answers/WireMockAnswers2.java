package com.ontestautomation.workshops.wiremock.answers;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockAnswers2 {
	
	public WireMockAnswers2() {
	}
	
	public void setupStubExercise201() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise201
		 * and responds only to GET requests where the path
		 * is equal to /exercise 201
		 * Response body should equal 'Exercise 201 passed'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise201"))
				.willReturn(aResponse()
						.withBody("Exercise 201 passed")
				));
	}
	
	public void setupStubExercise202() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise202
		 * and responds only to GET requests that contain
		 * the text 'Exercise202Body' in the body of the request
		 * Response body should equal 'Exercise 202 passed'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise202"))
				.withRequestBody(containing("Exercise202Body"))
				.willReturn(aResponse()
						.withBody("Exercise 202 passed")
				));
	}
	
	public void setupStubExercise203() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise203
		 * and responds only to GET requests that have a header element
		 * 'MyHeader' with value 'MyHeaderValue' and that do NOT have
		 * a header element 'NoSuchElement' 
		 * Response body should equal 'Exercise 203 passed'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise203"))
				.withHeader("MyHeader",matching("MyHeaderValue"))
				.withHeader("NoSuchElement", absent())
				.willReturn(aResponse()
						.withBody("Exercise 203 passed")
				));
	}
	
	public void setupStubExercise204() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise204
		 * and responds only to GET requests with Basic
		 * authentication set to
		 * username: wiremock
		 * password: workshop
		 * Response body should equal 'Exercise 204 passed'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise204"))
				.withBasicAuth("wiremock", "workshop")
				.willReturn(aResponse()
						.withBody("Exercise 204 passed")
				));
	}
	
	public void setupStubExercise205() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise205
		 * and responds only to GET requests that contain
		 * a cookie 'MyCookie' with value 'ChocolateChip'
		 * Response body should equal 'Exercise 205 passed'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise205"))
				.withCookie("MyCookie",matching("ChocolateChip"))
				.willReturn(aResponse()
						.withBody("Exercise 205 passed")
				));
	}
}
