package exercises;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@WireMockTest(httpPort = 9876)
public class WireMockExercises1Test {

	private RequestSpecification requestSpec;

	@BeforeEach
	public void createRequestSpec() {

		requestSpec = new RequestSpecBuilder().
				setBaseUri("http://localhost").
				setPort(9876).
				build();
	}

	public void setupStubExercise101() {

		/************************************************
		 * Create a stub that will respond to a POST
		 * to /requestLoan with an HTTP status code 200
		 ************************************************/

	}

	public void setupStubExercise102() {

		/************************************************
		 * Create a stub that will respond to a POST
		 * to /requestLoan with a response that contains
		 * a Content-Type header with value application/json
		 ************************************************/

	}

	public void setupStubExercise103() {

		/************************************************
		 * Create a stub that will respond to a POST
		 * to /requestLoan with a plain text response body
		 * equal to 'Loan application received!'
		 ************************************************/

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
				post("/requestLoan").
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
				post("/requestLoan").
		then().
				assertThat().
				contentType(ContentType.JSON);
	}

	@Test
	public void testExercise103() {

		setupStubExercise103();

		given().
				spec(requestSpec).
		when().
				post("/requestLoan").
		then().
				assertThat().
				body(org.hamcrest.Matchers.equalTo("Loan application received!"));
	}
}
