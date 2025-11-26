package answers;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.LoanDetails;
import models.LoanRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

@WireMockTest(httpPort = 9876)
public class WireMockAnswers6Test {

    private RequestSpecification requestSpec;

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void setupStubExercise601() {

        /**
         * Add two stub definitions:
         * - One that responds to an HTTP POST to /requestLoan with an HTTP status code 201,
         *   but only if the value of the 'Authorization' header is equal to 'Bearer validToken',
         *   i.e., the authorization token used is equal to 'validToken'
         * - Another that responds to an HTTP POST to /requestLoan with an HTTP status code 401,
         *   but only if any other authorization token value is used (hint: use a regular expression),
         *   or if there is no 'Authorization' header at all present in the request
         *
         * Use stub priority to have WireMock check that the token is equal to 'validToken' first,
         * before rejecting the request because the token has any other value
         * or because the Authorization header is absent
         */

        stubFor(post(urlMatching("/requestLoan")).atPriority(1)
                .withHeader("Authorization", equalTo("Bearer validToken"))
                .willReturn(aResponse()
                        .withStatus(201)
                ));

        stubFor(post(urlMatching("/requestLoan")).atPriority(2)
                .withHeader("Authorization", matching("Bearer (.*)").or(absent()))
                .willReturn(aResponse()
                        .withStatus(401)
                ));
    }

    @Test
    public void testExercise601() {

        setupStubExercise601();

        given().
                spec(requestSpec).
                auth().
                oauth2("validToken").
        when().
                post("/requestLoan").
        then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
                auth().
                oauth2("invalidToken").
        when().
                post("/requestLoan").
        then().
                assertThat().
                statusCode(401);

        given().
                spec(requestSpec).
        when().
                post("/requestLoan").
        then().
                assertThat().
                statusCode(401);
    }
}
