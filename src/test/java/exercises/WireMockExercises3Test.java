package exercises;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

@WireMockTest(httpPort = 9876)
public class WireMockExercises3Test {

    private RequestSpecification requestSpec;

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void setupStubExercise301() {

        /************************************************
         * Create a stub that exerts the following behavior:
         * - The scenario is called 'Loan processing'
         * - 1. A first GET to /loan/12345 returns HTTP 404
         * - 2. A POST to /requestLoan with body 'Loan ID: 12345' returns HTTP 201
         * 		and causes a transition to state 'LOAN_GRANTED'
         * - 3. A second GET (when in state 'LOAN_GRANTED') to /loan/12345
         *      returns HTTP 200 and body 'Loan ID: 12345'
         ************************************************/

    }

    @Test
    public void testExercise301() {

        /***
         * Use this test to test the Java implementation of exercise 301
         */

        setupStubExercise301();

        given().
                spec(requestSpec).
        when().
                get("/loan/12345").
        then().
                assertThat().
                statusCode(404);

        given().
                spec(requestSpec).
        and().
                body("Loan ID: 12345").
        when().
                post("/requestLoan").
        then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
        when().
                get("/loan/12345").
        then().
                assertThat().
                statusCode(200).
        and().
                body(org.hamcrest.Matchers.equalTo("Loan ID: 12345"));
    }
}
