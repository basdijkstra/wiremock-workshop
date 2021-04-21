package exercises;

import exercises.extensions.AddUuidAndHttpMethodHeaderTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class WireMockExercises5dot4 {

    private RequestSpecification requestSpec;

    @Rule
    public WireMockRule wireMockRule =
        new WireMockRule(wireMockConfig().
            port(9876).
            extensions(new AddUuidAndHttpMethodHeaderTransformer())
        );

    @Before
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://localhost").
            setPort(9876).
            build();
    }

    public void setupStubExercise5dot4() {

        stubFor(get(urlEqualTo("/response-definition-transformer"))
            .willReturn(aResponse()
                .withTransformerParameter("headerName", "uuid")
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
            get("/response-definition-transformer").
        then().
            assertThat().
            statusCode(200).
        and().
            header("uuid", org.hamcrest.Matchers.matchesPattern("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}")).
            header("methodName", org.hamcrest.Matchers.equalTo("GET"));
    }
}
