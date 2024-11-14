package examples;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

@WireMockTest(httpPort = 9876)
public class WireMockExamples5Test {

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
