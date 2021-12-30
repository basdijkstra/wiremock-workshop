package answers;

import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.ClientProtocolException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

@WireMockTest(httpPort = 9876)
public class WireMockAnswers2Test {

    private RequestSpecification requestSpec;

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://localhost").
            setPort(9876).
            build();
    }

    public void setupStubExercise201() {

        /************************************************
         * Create a stub that will respond to all GET
         * requests to /servicedown
         * with HTTP status code 503 and a status message
         * equal to 'Service unavailable'
         ************************************************/

        stubFor(get(urlEqualTo("/servicedown"))
            .willReturn(aResponse()
                .withStatus(503)
                .withStatusMessage("Service unavailable")
            ));
    }

    public void setupStubExercise202() {

        /************************************************
         * Create a stub that will respond to a GET request
         * to /slow with request header 'speed' with value 'slow'.
         * Respond with status code 200, but only after a
         * fixed delay of 3000 milliseconds.
         ************************************************/

        stubFor(get(urlEqualTo("/slow"))
            .withHeader("speed", equalTo("slow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withFixedDelay(3000)
            ));
    }

    public void setupStubExercise203() {

        /************************************************
         * Create a stub that will respond to a GET request
         * to /fault with a Fault of type RANDOM_DATA_THEN_CLOSE
         ************************************************/

        stubFor(get(urlEqualTo("/fault"))
                .willReturn(aResponse()
                        .withFault(Fault.RANDOM_DATA_THEN_CLOSE)
                ));
    }

    @Test
    public void testExercise201() {

        /***
         * Use this test to test your implementation of exercise 201
         */

        setupStubExercise201();

        given().
            spec(requestSpec).
        when().
            get("/servicedown").
        then().
            assertThat().
            statusCode(503).
        and().
            statusLine(org.hamcrest.Matchers.containsString("Service unavailable"));
    }

    @Test
    public void testExercise202() {

        /***
         * Use this test to test your implementation of exercise 202
         */

        setupStubExercise202();

        given().
            spec(requestSpec).
        and().
            header("speed","slow").
        when().
            get("/slow").
        then().
            assertThat().
            statusCode(200).
        and().
            time(org.hamcrest.Matchers.greaterThan(3000L));
    }

    @Test
    public void testExercise203() {

        /***
         * Use this test to test your implementation of exercise 203
         */

        setupStubExercise203();

        Assertions.assertThrows(ClientProtocolException.class, () -> {

            given().
                spec(requestSpec).
            when().
                get("/fault");
        });
    }
}
