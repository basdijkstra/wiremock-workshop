package com.ontestautomation.workshops.wiremock.exercises;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.stubbing.Scenario;

public class WireMockExercises4 {
	
	public WireMockExercises4() {
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

	}
}
