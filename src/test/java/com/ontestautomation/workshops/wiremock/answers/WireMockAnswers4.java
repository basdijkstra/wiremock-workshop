package com.ontestautomation.workshops.wiremock.answers;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.stubbing.Scenario;

public class WireMockAnswers4 {
	
	public WireMockAnswers4() {
	}
	
	public void setupStubExercise401() {

		/************************************************
		 * Create a stub that listens at path
		 * /exercise401 and exerts the following behavior:
		 * - The scenario is called 'Light bulb'
		 * - All responses have HTTP status 200
		 * - 1. A GET returns a body 'No light bulb found'
		 * - 2. A POST with body 'Insert light bulb' returns a body 'Light bulb inserted'
		 * 		and causes a transition to state 'LIGHT_OFF'
		 * - 3. A 2nd GET returns a body 'Light is OFF'
		 * - 4. A 2nd POST with body 'Switch light ON' returns a body 'Light has been turned ON'
		 * 		and causes a transition to state 'LIGHT_ON'
		 * - 5. A 3rd GET returns a body 'Light is ON'
		 ************************************************/
		
		stubFor(get(urlEqualTo("/exercise401")).inScenario("Light bulb")
				.whenScenarioStateIs(Scenario.STARTED)
				.willReturn(aResponse()
						.withStatus(200)
						.withBody("No light bulb found")
				));
		
		stubFor(post(urlEqualTo("/exercise401")).inScenario("Light bulb")
				.whenScenarioStateIs(Scenario.STARTED)
				.withRequestBody(equalTo("Insert light bulb"))
				.willReturn(aResponse()
						.withStatus(200)
						.withBody("Light bulb inserted")
				)
				.willSetStateTo("LIGHT_OFF"));
		
		stubFor(get(urlEqualTo("/exercise401")).inScenario("Light bulb")
				.whenScenarioStateIs("LIGHT_OFF")
				.willReturn(aResponse()
						.withStatus(200)
						.withBody("Light is OFF")
				));
		
		stubFor(post(urlEqualTo("/exercise401")).inScenario("Light bulb")
				.whenScenarioStateIs("LIGHT_OFF")
				.withRequestBody(equalTo("Switch light ON"))
				.willReturn(aResponse()
						.withStatus(200)
						.withBody("Light has been turned ON")
				)
				.willSetStateTo("LIGHT_ON"));
		
		stubFor(get(urlEqualTo("/exercise401")).inScenario("Light bulb")
				.whenScenarioStateIs("LIGHT_ON")
				.willReturn(aResponse()
						.withStatus(200)
						.withBody("Light is ON")
				));				
	}
}
