package examples;

import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockExamples {

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

        stubFor(any(urlEqualTo("/template-http-method"))
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
}
