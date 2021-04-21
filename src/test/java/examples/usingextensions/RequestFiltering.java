package examples.usingextensions;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import examples.extensions.BasicAuthRequestFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static io.restassured.RestAssured.given;

public class RequestFiltering {

    private RequestSpecification requestSpec;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(
            new WireMockConfiguration().port(9876).extensions(new BasicAuthRequestFilter())
    );

    @Before
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void stubForRequestFiltering() {

        stubFor(get(urlEqualTo("/request-filtering"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Authorized")
                ));
    }

    @Test
    public void callWireMockWithCorrectCredentials_checkStatusCodeEquals200() {

        stubForRequestFiltering();

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
