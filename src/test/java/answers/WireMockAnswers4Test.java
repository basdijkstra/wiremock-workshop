package answers;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import models.LoanDetails;
import models.LoanRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class WireMockAnswers4Test {

    private RequestSpecification requestSpec;

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance()
            .options(wireMockConfig().port(9876).globalTemplating(true))
            .build();

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void setupStubExercise401() {

        /************************************************
         * Create a stub that responds to all GET requests
         * to /echo-port with HTTP status code 200 and a
         * response body containing the text
         * "Listening on port <portnumber>", where <portnumber>
         * is replaced with the actual port number
         * (9876, in this case) using response templating.
         *
         * The response templating transformer extension is
         * already active, so you don't need to do that yourself.
         ************************************************/

        wiremock.stubFor(get(urlEqualTo("/echo-port"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Listening on port {{request.port}}")
                ));
    }

    public void setupStubExercise402() {

        /************************************************
         * Create a stub that listens at path /echo-loan-amount
         * and responds to all POST requests with HTTP
         * status code 200 and a response body containing
         * the text 'Received loan application request for $<amount>',         *
         * where <amount> is the value of the JSON element
         * loanDetails.amount extracted from the request body
         ************************************************/

        wiremock.stubFor(post(urlEqualTo("/echo-loan-amount"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Received loan application request for ${{jsonPath request.body '$.loanDetails.amount'}}")
                ));
    }

    @Test
    public void testExercise401() {

        /***
         * Use this test to test your implementation of exercise 401
         */

        setupStubExercise401();

        given().
                spec(requestSpec).
        when().
                get("/echo-port").
        then().
                assertThat().
                statusCode(200).
        and().
                body(org.hamcrest.Matchers.equalTo("Listening on port 9876"));
    }

    @ParameterizedTest(name = "Loan amount {0} is echoed in the response correctly")
    @CsvSource({
            "1000",
            "1500",
            "3000"
    })
    public void testExercise402(int loanAmount) {

        /***
         * Use this test to test your implementation of exercise 402
         */

        LoanDetails loanDetails = new LoanDetails(loanAmount, 100, "pending");
        LoanRequest loanRequest = new LoanRequest(12212, loanDetails);

        setupStubExercise402();

        given().
                spec(requestSpec).
        and().
                body(loanRequest).
        when().
                post("/echo-loan-amount").
        then().
                assertThat().
                statusCode(200).
        and().
                body(org.hamcrest.Matchers.equalTo(
                        String.format("Received loan application request for $%d", loanAmount)
                ));
    }
}
