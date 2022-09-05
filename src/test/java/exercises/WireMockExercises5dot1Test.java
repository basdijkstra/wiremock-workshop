package exercises;

import exercises.extensions.BasicAuthRequestFilter;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class WireMockExercises5dot1Test {

    private RequestSpecification requestSpec;

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance().
            options(wireMockConfig().
                    port(9876).
                    extensions(new BasicAuthRequestFilter())
            ).build();

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void stubForRequestFiltering() {

        wiremock.stubFor(get(urlEqualTo("/request-filtering"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Authorized")
                ));
    }

    @Test
    public void callWireMockWithCorrectCredentials_checkStatusCodeEquals200() {

        stubForRequestFiltering();

        /***
         * Use this test to test your implementation of the request filter
         * This request should be processed normally since it contains the
         * proper credentials
         */

        given().
                spec(requestSpec).
        and().
                auth().preemptive().basic("username","password").
        when().
                get("/request-filtering").
        then().
                assertThat().
                statusCode(200).
        and().
                body(org.hamcrest.Matchers.equalTo("Authorized"));
    }

    @Test
    public void callWireMockWithIncorrectCredentials_checkStatusCodeEquals401() {

        stubForRequestFiltering();

        /***
         * Use this test to test your implementation of the request filter
         * This request should be filtered out as it uses incorrect credentials
         */

        given().
                spec(requestSpec).
        and().
                auth().preemptive().basic("username","incorrectpassword").
        when().
                get("/request-filtering").
        then().
                assertThat().
                statusCode(401);
    }
}
