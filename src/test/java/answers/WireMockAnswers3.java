package answers;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class WireMockAnswers3 {

    private RequestSpecification requestSpec;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9876);

    @Before
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://localhost").
            setPort(9876).
            build();
    }

    public void setupStubExercise301() {

        /************************************************
         * Create a stub that exerts the following behavior:
         * - The scenario is called 'Stateful mock exercise'
         * - 1. A first GET to /nl/3825 returns HTTP 404
         * - 2. A POST to /nl/3825 with body 'DATA FOR /nl/3825' returns HTTP 201
         * 		and causes a transition to state 'DATA_CREATED'
         * - 3. A second GET (when in state 'DATA_CREATED ') to /nl/3825
         *      returns HTTP 200 and body "DATA FOR /nl/3825"
         ************************************************/

        stubFor(get(urlEqualTo("/nl/3825"))
            .inScenario("Stateful mock exercise")
            .whenScenarioStateIs(Scenario.STARTED)
            .willReturn(aResponse()
                .withStatus(404)
            )
        );

        stubFor(post(urlEqualTo("/nl/3825"))
            .inScenario("Stateful mock exercise")
            .whenScenarioStateIs(Scenario.STARTED)
            .withRequestBody(equalTo("DATA FOR /nl/3825"))
            .willReturn(aResponse()
                .withStatus(201)
            )
            .willSetStateTo("DATA_CREATED")
        );

        stubFor(get(urlEqualTo("/nl/3825"))
            .inScenario("Stateful mock exercise")
            .whenScenarioStateIs("DATA_CREATED")
            .willReturn(aResponse()
                .withStatus(200)
                .withBody("DATA FOR /nl/3825")
            )
        );
    }

    @Test
    public void testExercise301() {

        /***
         * Use this test to test the Java implementation of exercise 301
         */

        setupStubExercise301();

        given().
            spec(requestSpec).
        when().
            get("/nl/3825").
        then().
            assertThat().
            statusCode(404);

        given().
            spec(requestSpec).
        and().
            body("DATA FOR /nl/3825").
        when().
            post("/nl/3825").
        then().
            assertThat().
            statusCode(201);

        given().
            spec(requestSpec).
        when().
            get("/nl/3825").
        then().
            assertThat().
            statusCode(200).
        and().
            body(org.hamcrest.Matchers.equalTo("DATA FOR /nl/3825"));
    }
}
