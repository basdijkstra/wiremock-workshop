package exercises;

import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.*;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class WireMockExercises4 {

    private RequestSpecification requestSpec;

    @Rule
    public WireMockRule wireMockRule =
        new WireMockRule(wireMockConfig().
            port(9876).
            extensions(new ResponseTemplateTransformer(true))
        );

    @Before
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://localhost").
            setPort(9876).
            build();
    }

    public void setupStubExercise401() {

        /************************************************
         * Create a stub that listens at path
         * /echo-port
         * and responds to all GET requests with HTTP
         * status code 200 and a response body containing
         * the text "Listening on port <portnumber>"
         * where <portnumber> is replaced with the actual port
         * number (9876, in this case)
         * Don't forget to enable response templating!
         ************************************************/


    }

    public void setupStubExercise402() {

        /************************************************
         * Create a stub that listens at path
         * /echo-car-model
         * and responds to all POST requests with HTTP
         * status code 200 and a response body containing
         * the value of the JSON element car.model extracted
         * from the request body
         ************************************************/


    }

    @Test
    public void testExercise401() {

        /***
         * Use this test to test your implementation of exercise 401
         */

        setupStubExercise401();

        given().
            spec(requestSpec).
        when().
            get("/echo-port").
        then().
            assertThat().
            statusCode(200).
        and().
            body(org.hamcrest.Matchers.equalTo("Listening on port 9876"));
    }

    @Test
    public void testExercise402() {

        /***
         * Use this test to test your implementation of exercise 402
         */

        setupStubExercise402();

        given().
            spec(requestSpec).
            contentType(ContentType.JSON).
            body("{\"car\": {\"make\": \"Alfa Romeo\", \"model\": \"Giulia 2.9 V6 Quadrifoglio\",\"top_speed\": 307}}").
        when().
            post("/echo-car-model").
        then().
            assertThat().
            statusCode(200).
        and().
            body(org.hamcrest.Matchers.equalTo("Giulia 2.9 V6 Quadrifoglio"));
    }
}
