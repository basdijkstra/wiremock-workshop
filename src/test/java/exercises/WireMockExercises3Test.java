package exercises;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
         * - The scenario is called 'Stateful mock exercise'
         * - 1. A first GET to /nl/3825 returns HTTP 404
         * - 2. A POST to /nl/3825 with body 'DATA FOR /nl/3825' returns HTTP 201
         * 		and causes a transition to state 'DATA_CREATED'
         * - 3. A second GET (when in state 'DATA_CREATED ') to /nl/3825
         *      returns HTTP 200 and body "DATA FOR /nl/3825"
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
            get("/nl/3825").
        then().
            assertThat().
            statusCode(404);

        given().
            spec(requestSpec).
        and().
            body("DATA FOR /nl/3825").
        when().
            post("/nl/3825").
        then().
            assertThat().
            statusCode(201);

        given().
            spec(requestSpec).
        when().
            get("/nl/3825").
        then().
            assertThat().
            statusCode(200).
        and().
            body(org.hamcrest.Matchers.equalTo("DATA FOR /nl/3825"));
    }
}
