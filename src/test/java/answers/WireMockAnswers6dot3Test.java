package answers;

import answers.extensions.LogRequestWithTimestamp;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class WireMockAnswers6dot3Test {

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

        wiremock.stubFor(post(urlEqualTo("/requestLoan"))
                .withServeEventListener("log-loan-request-with-timestamp", Parameters.one("format", "dd-MM-yyyy HH:mm:ss"))
                .willReturn(aResponse()
                        .withStatus(201)
                ));
    }

    @Test
    public void anIncomingLoanRequestShouldTriggerAConsoleLogMessage() {

        /***
         * Use this test to test your implementation of the serve event listener
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
