package com.ontestautomation.workshops.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

public class WireMockExamples {
		
	public WireMockExamples() {		
	}
		
	public void setupExampleStub() {

		stubFor(
			post(urlEqualTo("/pingpong"))
			.withRequestBody(equalToXml("<input>PING</input>"))
			.willReturn(
				aResponse()
				.withStatus(200)
				.withHeader("Content-Type","application/xml")
				.withBody("<output>PONG</output>")));
	}
	
	public void setupStubURLMatching() {
		
		stubFor(get(urlEqualTo("/urlmatching"))
				.willReturn(aResponse()
						.withBody("URL matching")
				));
	}
	
	public void setupStubRequestBodyMatching() {
		
		stubFor(post(urlEqualTo("/requestbodymatching"))
				.withRequestBody(containing("RequestBody"))
				.willReturn(aResponse()
						.withBody("Request body matching")
				));
	}
	
	public void setupStubHeaderMatching() {
		
		stubFor(get(urlEqualTo("/headermatching"))
				.withHeader("Content-Type", containing("application/json"))
				.withHeader("DoesntExist", absent())
				.willReturn(aResponse()
						.withBody("Header matching")
				));
	}
	
	public void setupStubAuthorizationMatching() {
		
		stubFor(get(urlEqualTo("/authorizationmatching"))
				.withBasicAuth("username", "password")
				.willReturn(aResponse()
						.withBody("Authorization matching")
				));
	}
	
	public void setupStubReturningErrorCode() {
		
		stubFor(get(urlEqualTo("/errorcode"))
				.willReturn(aResponse()
						.withStatus(500)
				));
	}
	
	public void setupStubFixedDelay() {
		
		stubFor(get(urlEqualTo("/fixeddelay"))
				.willReturn(aResponse()
						.withFixedDelay(2000)
				));
	}
	
	public void setupStubBadResponse() {
		
		stubFor(get(urlEqualTo("/badresponse"))
				.willReturn(aResponse()
						.withFault(Fault.MALFORMED_RESPONSE_CHUNK)
				));
	}
	
	public void setupStubStateful() {
		
		stubFor(get(urlEqualTo("/order")).inScenario("Order processing")
				.whenScenarioStateIs(Scenario.STARTED)
				.willReturn(aResponse()
						.withBody("Your shopping cart is empty")
				));
		
		stubFor(post(urlEqualTo("/order")).inScenario("Order processing")
				.whenScenarioStateIs(Scenario.STARTED)
				.withRequestBody(equalTo("Ordering 1 item"))
				.willReturn(aResponse()
						.withBody("Item placed in shopping cart")
				)
				.willSetStateTo("ORDER_PLACED"));
		
		stubFor(get(urlEqualTo("/order")).inScenario("Order processing")
				.whenScenarioStateIs("ORDER_PLACED")
				.willReturn(aResponse()
						.withBody("There is 1 item in your shopping cart")
				));
	}
	
}
