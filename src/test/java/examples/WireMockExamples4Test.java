package examples;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WireMockExamples4Test {

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance()
            .options(wireMockConfig().port(9876).globalTemplating(true))
            .build();

    public void setupStubResponseTemplatingHttpMethod() {

        wiremock.stubFor(any(urlEqualTo("/template-http-method"))
            .willReturn(aResponse()
                .withBody("You used an HTTP {{request.method}}")
                .withTransformers("response-template")
            ));
    }

    public void setupStubResponseTemplatingJsonBody() {

        wiremock.stubFor(post(urlEqualTo("/template-json-body"))
            .willReturn(aResponse().
                withBody("{{jsonPath request.body '$.book.title'}}").
                withTransformers("response-template")
            ));
    }
}
