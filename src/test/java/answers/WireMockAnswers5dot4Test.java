package answers;

import answers.extensions.AddUuidHeaderTransformer;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class WireMockAnswers5dot4Test {

    private RequestSpecification requestSpec;

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance().
            options(wireMockConfig().
                    port(9876).
                    extensions(new AddUuidHeaderTransformer())
            ).build();

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://localhost").
            setPort(9876).
            build();
    }

    public void setupStubExercise5dot4() {

        wiremock.stubFor(post(urlEqualTo("/requestLoan"))
            .willReturn(aResponse()
                .withTransformerParameter("uuidHeaderName", "uuid")
                .withStatus(200)
            ));
    }

    @Test
    public void getResponse_checkHeaders_shouldIncludeThoseAddedbyResponseDefinitionTransformer() {

        /***
         * Use this test to test your implementation of the response definition transformer
         */

        setupStubExercise5dot4();

        given().
            spec(requestSpec).
        when().
            post("/requestLoan").
        then().
            assertThat().
            statusCode(200).
        and().
            header("uuid", org.hamcrest.Matchers.matchesPattern("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
    }
}
