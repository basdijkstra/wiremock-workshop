package exercises;

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
public class WireMockExercises5Test {

    private RequestSpecification requestSpec;

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void setupStubServiceUnavailable() {

        stubFor(post(urlEqualTo("/requestLoan"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withStatusMessage("Loan processor service unavailable")
                ));
    }

    @Test
    public void testExercise501() {

        setupStubServiceUnavailable();

        given().
                spec(requestSpec).
        when().
                post("/requestLoan").
        then().
                assertThat().
                statusCode(503);

        /**
         * Add a verification to this test that verifies that exactly one HTTP POST
         * has been submitted to the /requestLoan endpoint
         */

    }

    @ParameterizedTest(name = "Request a loan for {0}")
    @CsvSource({
            "1000",
            "1500",
            "2000"
    })
    public void testExercise502(int loanAmount) {

        setupStubServiceUnavailable();

        LoanDetails loanDetails = new LoanDetails(loanAmount, 100, "pending");
        LoanRequest loanRequest = new LoanRequest(12212, loanDetails);

        given().
                spec(requestSpec).
        and().
                contentType(ContentType.JSON).
        and().
                body(loanRequest).
        when().
                post("/requestLoan").
        then().
                assertThat().
                statusCode(503);

        /**
         * Add a verification to this test that verifies after each test that
         * - exactly 1 HTTP POST has been submitted to the /requestLoan endpoint
         * - the request contained a Content-Type header with a value containing 'application/json'
         * - the request body contained a field loanDetails.amount with a value equal to the
         *   loanAmount variable supplied to the test
         *   (hint: see setupStubExercise205() in WireMockAnswers2Test.java)
         */

    }
}
