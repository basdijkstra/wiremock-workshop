package exercises;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import exercises.extensions.LogLoanRequestReceptionWithTimestamp;
import com.github.tomakehurst.wiremock.extension.Parameters;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class WireMockExercises5dot3Test {

    private RequestSpecification requestSpec;

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance().
            options(wireMockConfig().
                    port(9876).
                    extensions(new LogLoanRequestReceptionWithTimestamp())
            ).build();

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://localhost").
            setPort(9876).
            build();
    }

    public void setupStubExercise5dot3() {

        wiremock.stubFor(post(urlEqualTo("/requestLoan"))
            .withPostServeAction("log-loan-request-with-timestamp", Parameters.one("format", "dd-MM-yyyy HH:mm:ss"))
            .willReturn(aResponse()
                .withStatus(201)
            ));
    }

    @Test
    public void anIncomingLoanRequestShouldTriggerAConsoleLogMessage() {

        /***
         * Use this test to test your implementation of the post-serve action
         * This should result in a message with the timestamp in the desired format
         * printed to the console
         */

        setupStubExercise5dot3();

        given().
            spec(requestSpec).
        when().
            post("/requestLoan").
        then().
            assertThat().
            statusCode(201);
    }
}
