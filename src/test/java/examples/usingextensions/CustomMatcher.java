package examples.usingextensions;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import examples.extensions.BasicAuthRequestFilter;
import examples.extensions.BodyLengthMatcher;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class CustomMatcher {

    private RequestSpecification requestSpec;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(
            new WireMockConfiguration().port(9876).extensions(new BodyLengthMatcher())
    );

    @Before
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void stubForCustomMatching() {

        stubFor(get(urlEqualTo("/custom-matching")).
                willReturn(aResponse().withStatus(200))
        );

        stubFor(get(urlEqualTo("/custom-matching")).
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
