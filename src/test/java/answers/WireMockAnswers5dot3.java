package answers;

import answers.extensions.LogCurrentTimeAction;
import answers.extensions.MultipleHttpVerbsMatcher;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class WireMockAnswers5dot3 {

    private RequestSpecification requestSpec;

    @Rule
    public WireMockRule wireMockRule =
        new WireMockRule(wireMockConfig().
            port(9876).
            extensions(new LogCurrentTimeAction())
        );

    @Before
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://localhost").
            setPort(9876).
            build();
    }

    public void setupStubExercise5dot3() {

        stubFor(get(urlEqualTo("/post-serve"))
            .withPostServeAction("log-timestamp", Parameters.one("format", "dd-MM-yyyy HH:mm:ss"))
            .willReturn(aResponse()
                .withStatus(200)
            ));
    }

    @Test
    public void aRequestSuccessfullyServedShouldWriteCurrentTimeToConsole() {

        /***
         * Use this test to test your implementation of the post-serve action
         * This should result in the
         */

        setupStubExercise5dot3();

        given().
            spec(requestSpec).
        when().
            get("/post-serve").
        then().
            assertThat().
            statusCode(200);
    }
}
