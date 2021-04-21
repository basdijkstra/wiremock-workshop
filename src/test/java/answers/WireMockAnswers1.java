package answers;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class WireMockAnswers1 {

	private RequestSpecification requestSpec;

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(9876);

	@Before
	public void createRequestSpec() {

		requestSpec = new RequestSpecBuilder().
				setBaseUri("http://localhost").
				setPort(9876).
				build();
	}

	public void setupStubExercise101() {

		/************************************************
		 * Create a stub that will respond to a POST
		 * to /pl/80-862 with an HTTP status code 200
		 ************************************************/

		stubFor(post(urlEqualTo("/pl/80-862"))
				.willReturn(aResponse()
						.withStatus(200)
				));
	}
	
	public void setupStubExercise102() {

		/************************************************
		 * Create a stub that will respond to a POST
		 * to /pl/80-863 with a response that contains
		 * a Content-Type header with value text/plain
		 ************************************************/
		
		stubFor(post(urlEqualTo("/pl/80-863"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "text/plain")
				));
	}
	
	public void setupStubExercise103() {

		/************************************************
		 * Create a stub that will respond to a POST
		 * to /pl/80-864 with a response body 'Posted!'
		 ************************************************/

		stubFor(post(urlEqualTo("/pl/80-864"))
				.willReturn(aResponse()
						.withBody("Posted!")
				));
	}

	@Test
	public void testExercise101() {

		/***
		 * Use this test to test your implementation of exercise 101
		 */

		setupStubExercise101();

		given().
			spec(requestSpec).
		when().
			post("/pl/80-862").
		then().
			assertThat().
			statusCode(200);
	}

	@Test
	public void testExercise102() {

		setupStubExercise102();

		given().
			spec(requestSpec).
		when().
			post("/pl/80-863").
		then().
			assertThat().
			contentType(ContentType.TEXT);
	}

	@Test
	public void testExercise103() {

		setupStubExercise103();

		given().
			spec(requestSpec).
		when().
			post("/pl/80-864").
		then().
			assertThat().
			body(org.hamcrest.Matchers.equalTo("Posted!"));
	}
}
