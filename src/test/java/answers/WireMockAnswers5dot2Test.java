package answers;

import answers.extensions.MultipleHttpVerbsMatcher;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class WireMockAnswers5dot2Test {

    private RequestSpecification requestSpec;

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance().
            options(wireMockConfig().
                    port(9876).
                    extensions(new MultipleHttpVerbsMatcher())
            ).build();

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://localhost").
            setPort(9876).
            build();
    }

    public void setupStubExercise5dot2() {

        List<String> verbs = new ArrayList<>();
        verbs.add("GET");
        verbs.add("POST");

        wiremock.stubFor(requestMatching("multiple-verbs-matcher",
                        Parameters.one("acceptedVerbs", verbs))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody("Your HTTP verb has been accepted!")
            ));
    }

    @Test
    public void anIncomingHttpGETShouldBeAccepted() {

        /***
         * Use this test to test your implementation of the custom matcher
         * This request should be accepted as it uses an HTTP GET
         */

        setupStubExercise5dot2();

        given().
            spec(requestSpec).
        when().
            get("/match-multiple-verbs").
        then().
            assertThat().
            statusCode(200).
        and().
            body(org.hamcrest.Matchers.equalTo("Your HTTP verb has been accepted!"));
    }

    @Test
    public void anIncomingHttpPOSTShouldAlsoBeMatched() {

        /***
         * Use this test to test your implementation of the custom matcher
         * This request should be also accepted as it uses an HTTP POST
         */

        setupStubExercise5dot2();

        given().
            spec(requestSpec).
        when().
            post("/match-multiple-verbs").
        then().
            assertThat().
            statusCode(200).
        and().
            body(org.hamcrest.Matchers.equalTo("Your HTTP verb has been accepted!"));
    }

    @Test
    public void anIncomingHttpPATCHShouldNotBeMatched() {

        /***
         * Use this test to test your implementation of the custom matcher
         * This request should not be accepted as it uses an HTTP PATCH
         */

        setupStubExercise5dot2();

        given().
            spec(requestSpec).
        when().
            patch("/match-multiple-verbs").
        then().
            assertThat().
            statusCode(404);
    }
}
