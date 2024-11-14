package examples;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest(httpPort = 9876)
public class WireMockExamples1Test {

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
}
