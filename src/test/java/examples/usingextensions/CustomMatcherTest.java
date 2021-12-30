package examples.usingextensions;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import examples.extensions.BodyLengthMatcher;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class CustomMatcherTest {

    private RequestSpecification requestSpec;

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance().
            options(wireMockConfig().
                    port(9876).
                    extensions(new BodyLengthMatcher())
            ).build();

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void stubForCustomMatching() {

        wiremock.stubFor(get(urlEqualTo("/custom-matching")).
                willReturn(aResponse().withStatus(200))
        );

        wiremock.stubFor(get(urlEqualTo("/custom-matching")).
                andMatching("body-too-long", Parameters.one("maxLength", 20)).
                willReturn(aResponse().withStatus(400))
        );
    }

    @Test
    public void callWireMockWithTooLongBody_checkStatusCode_shouldBeHttp400() {

        stubForCustomMatching();

        given().
                spec(requestSpec).
        and().
                body("Thisbodyistoolongtobeaccepted").
        when().
                get("/custom-matching").
        then().
                assertThat().
                statusCode(400);
    }

    @Test
    public void callWireMockWithShortEnoughBody_checkStatusCode_shouldBeHttp200() {

        stubForCustomMatching();

        given().
                spec(requestSpec).
        and().
                body("Shortenoughbody").
        when().
                get("/custom-matching").
        then().
                assertThat().
                statusCode(200);
    }
}
