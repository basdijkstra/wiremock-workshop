package examples;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest(httpPort = 9876)
public class WireMockExamples3Test {

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
}
