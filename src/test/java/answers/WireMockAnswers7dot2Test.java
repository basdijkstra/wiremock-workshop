package answers;

import answers.extensions.RejectedHttpVerbsMatcher;
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

public class WireMockAnswers7dot2Test {

    private RequestSpecification requestSpec;

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance().
            options(wireMockConfig().
                    port(9876).
                    extensions(new RejectedHttpVerbsMatcher())
            ).build();

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://localhost").
            setPort(9876).
            build();
    }

    public void stubForCustomRequestFilter() {

        List<String> verbs = new ArrayList<>();
        verbs.add("PUT");
        verbs.add("PATCH");
        verbs.add("DELETE");

        wiremock.stubFor(requestMatching("rejected-verbs-matcher",
                        Parameters.one("rejectedVerbs", verbs))
            .willReturn(aResponse()
                .withStatus(405)
            ));
    }

    @Test
    public void anIncomingHttpPUTShouldBeRejected() {

        /***
         * Use this test to test your implementation of the custom matcher
         * This request should be rejected as it uses an HTTP PUT
         */

        stubForCustomRequestFilter();

        given().
            spec(requestSpec).
        when().
            put("/requestLoan").
        then().
            assertThat().
            statusCode(405);
    }

    @Test
    public void anIncomingHttpPATCHShouldAlsoBeRejected() {

        /***
         * Use this test to test your implementation of the custom matcher
         * This request should also be rejected as it uses an HTTP PATCH
         */

        stubForCustomRequestFilter();

        given().
            spec(requestSpec).
        when().
            patch("/requestLoan").
        then().
            assertThat().
            statusCode(405);
    }

    @Test
    public void anIncomingHttpDELETEShouldAlsoBeRejected() {

        /***
         * Use this test to test your implementation of the custom matcher
         * This request should also be rejected as it uses an HTTP DELETE
         */

        stubForCustomRequestFilter();

        given().
            spec(requestSpec).
        when().
            delete("/requestLoan").
        then().
            assertThat().
            statusCode(405);
    }

    @Test
    public void anIncomingHttpGETShouldNotBeMatched() {

        /***
         * Use this test to test your implementation of the custom matcher
         * This request should not be matched by our custom matcher
         * as it uses an HTTP GET, and thus should yield a default HTTP 404
         */

        stubForCustomRequestFilter();

        given().
            spec(requestSpec).
        when().
            get("/requestLoan").
        then().
            assertThat().
            statusCode(404);
    }
}
