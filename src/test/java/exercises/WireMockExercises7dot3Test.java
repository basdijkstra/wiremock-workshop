package exercises;

import answers.extensions.LogRequestWithTimestamp;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class WireMockExercises7dot3Test {

    private RequestSpecification requestSpec;

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance().
            options(wireMockConfig().
                    port(9876).
                    extensions(new LogRequestWithTimestamp())
            ).build();

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void stubForServeEventListener() {

        /**
         * Create a new Map<String, Object> for the Parameters and initialize it
         * an empty HashMap
         *
         * Then, add a single item with key 'format' and value 'dd-MM-yyyy HH:mm:ss'
         */


        /**
         * Create a new stub that responds to an HTTP POST to /requestLoan and that
         * returns a response with HTTP status code 201
         *
         * Add the ServeEventListener with name 'log-loan-request-with-timestamp' and
         * pass in the parameters you defined above
         */

    }

    @Test
    public void anIncomingLoanRequestShouldTriggerAConsoleLogMessage() {

        /***
         * Use this test to test your implementation of the ServeEvent listener
         * This should result in a message with the timestamp in the desired format
         * printed to the console
         */

        stubForServeEventListener();

        given().
                spec(requestSpec).
        when().
                post("/requestLoan").
        then().
                assertThat().
                statusCode(201);
    }
}
