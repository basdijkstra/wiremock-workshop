package examples;

import com.github.tomakehurst.wiremock.common.DateTimeUnit;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

@WireMockTest(httpPort = 9876)
public class WireMockExamplesTest {

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance()
            .options(wireMockConfig().port(9876).globalTemplating(true))
            .build();

    public void helloWorld() {

        stubFor(
            get(
                urlEqualTo("/helloworld")
            )
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("Hello world!")));
    }

    public void setupStubURLMatching() {

        stubFor(get(urlEqualTo("/urlmatching"))
            .willReturn(aResponse()
                .withBody("URL matching")
            ));
    }

    public void setupStubHeaderMatching() {

        stubFor(get(urlEqualTo("/headermatching"))
            .withHeader("Content-Type", containing("application/json"))
            .withHeader("DoesntExist", absent())
            .willReturn(aResponse()
                .withBody("Header matching")
            ));
    }

    public void setupStubLogicalAndHeaderMatching() {

        stubFor(get(urlEqualTo("logical-or-matching"))
            .withHeader("my-header",
                matching("[a-z]+").and(containing("somevalue"))
            )
            .willReturn(aResponse()
                .withBody("Logical AND matching"))
        );
    }

    public void setupStubLogicalAndHeaderMatchingMoreVerbose() {

        stubFor(get(urlEqualTo("logical-or-matching"))
            .withHeader("my-header", and(
                matching("[a-z]+"),
                containing("somevalue"))
            )
            .willReturn(aResponse()
                .withBody("Logical AND matching, a little more verbose"))
        );
    }

    public void setupStubLogicalOrHeaderMatching() {

        stubFor(get(urlEqualTo("logical-or-matching"))
            .withHeader("Content-Type",
                equalTo("application/json").or(absent())
            )
            .willReturn(aResponse()
                .withBody("Logical OR matching"))
        );
    }

    public void setupStubAfterSpecificDateMatching() {

        stubFor(get(urlEqualTo("date-is-after"))
            .withHeader("my-date",
                after("2021-07-01T00:00:00Z")
            )
            .willReturn(aResponse()
                .withBody("Date is after midnight, July 1, 2021"))
        );
    }

    public void setupStubRelativeToCurrentDateMatching() {

        stubFor(get(urlEqualTo("date-is-relative-to-now"))
            .withHeader("my-date",
                beforeNow().expectedOffset(1, DateTimeUnit.MONTHS)
            )
            .willReturn(aResponse()
                .withBody("Date is at least 1 month before current date"))
        );
    }

    public void setupStubRequestBodyValueMatching() {

        stubFor(post(urlEqualTo("/request-body-matching"))
                .withRequestBody(
                        matchingJsonPath("$.fruits[?(@.banana=='2')]")
                )
                .willReturn(aResponse()
                        .withStatus(200)
                )
        );
    }

    public void setupStubReturningErrorCode() {

        stubFor(get(urlEqualTo("/errorcode"))
            .willReturn(aResponse()
                .withStatus(500)
                .withStatusMessage("Status message goes here")
            ));
    }

    public void setupStubFixedDelay() {

        stubFor(get(urlEqualTo("/fixeddelay"))
            .willReturn(aResponse()
                .withFixedDelay(2000)
            ));
    }

    public void setupStubBadResponse() {

        stubFor(get(urlEqualTo("/badresponse"))
            .willReturn(aResponse()
                .withFault(Fault.MALFORMED_RESPONSE_CHUNK)
            ));
    }

    public void setupStubStateful() {

        stubFor(get(urlEqualTo("/order")).inScenario("Order processing")
            .whenScenarioStateIs(Scenario.STARTED)
            .willReturn(aResponse()
                .withBody("Your shopping cart is empty")
            )
        );

        stubFor(post(urlEqualTo("/order")).inScenario("Order processing")
            .whenScenarioStateIs(Scenario.STARTED)
            .withRequestBody(equalTo("Ordering 1 item"))
            .willReturn(aResponse()
                .withBody("Item placed in shopping cart")
            )
            .willSetStateTo("ORDER_PLACED")
        );

        stubFor(get(urlEqualTo("/order")).inScenario("Order processing")
            .whenScenarioStateIs("ORDER_PLACED")
            .willReturn(aResponse()
                .withBody("There is 1 item in your shopping cart")
            )
        );
    }

    public void setupStubResponseTemplatingHttpMethod() {

        wiremock.stubFor(any(urlEqualTo("/template-http-method"))
            .willReturn(aResponse()
                .withBody("You used an HTTP {{request.method}}")
                .withTransformers("response-template")
            ));
    }

    public void setupStubResponseTemplatingJsonBody() {

        stubFor(post(urlEqualTo("/template-json-body"))
            .willReturn(aResponse().
                withBody("{{jsonPath request.body '$.book.title'}}").
                withTransformers("response-template")
            ));
    }

    private RequestSpecification requestSpec;

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void setupHelloWorldStub() {

        stubFor(
                get(
                        urlEqualTo("/hello-world")
                )
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", "text/plain")
                                        .withStatus(200)
                                        .withBody("Hello world!"))
        );
    }

    @Test
    public void helloWorldVerificationTest() {

        setupHelloWorldStub();

        given().
                spec(requestSpec).
        when().
                get("/hello-world").
        then().
                assertThat().
                statusCode(200).
        and().
                body(org.hamcrest.Matchers.equalTo("Hello world!"));

        verify(exactly(1), getRequestedFor(urlEqualTo("/hello-world")));
    }
}
