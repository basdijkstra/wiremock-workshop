package examples;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

@WireMockTest(httpPort = 9876)
public class WireMockExamples6Test {

    private RequestSpecification requestSpec;

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void setupPizzaIngredientsStub() {

        stubFor(get(urlEqualTo("/pizza/ingredients/pineapple"))
                .atPriority(1)
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", "text/plain")
                                .withStatus(400)
                                .withBody("No. Just no."))
        );

        stubFor(get(urlMatching("/pizza/ingredients/(.*)"))
                .atPriority(2)
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", "text/plain")
                                .withStatus(200)
                                .withBody("That's fine."))
        );
    }

    @Test
    public void pizzaIngredientsVerificationTest() {

        setupPizzaIngredientsStub();

        given().
                spec(requestSpec).
        when().
                get("/pizza/ingredients/pineapple").
        then().
                assertThat().
                statusCode(400).
        and().
                body(org.hamcrest.Matchers.equalTo("No. Just no."));

        given().
                spec(requestSpec).
        when().
                get("/pizza/ingredients/mozzarella").
        then().
                assertThat().
                statusCode(200).
        and().
                body(org.hamcrest.Matchers.equalTo("That's fine."));
    }
}
