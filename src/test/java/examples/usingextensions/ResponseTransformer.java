package examples.usingextensions;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import examples.extensions.AddDateHeaderTransformer;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class ResponseTransformer {

    private RequestSpecification requestSpec;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(
            new WireMockConfiguration().port(9876).extensions(new AddDateHeaderTransformer())
    );

    @Before
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void stubForResponseTransformer() {

        stubFor(get(urlEqualTo("/response-transformer"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("existingHeader", "shouldBeVisible")
                        .withTransformerParameter("dateFormat", "dd-MM-yyyy")
                ));
    }

    @Test
    public void callWireMockWithTransformedResponse_checkCurrentDateHeaderIsCorrect() {

        stubForResponseTransformer();

        given().
                spec(requestSpec).
        when().
                get("/response-transformer").
        then().
                assertThat().
                statusCode(200).
        and().
                header("existingHeader", org.hamcrest.Matchers.equalTo("shouldBeVisible")).
                header("currentDate", org.hamcrest.Matchers.equalTo(new SimpleDateFormat("dd-MM-yyyy").format(new Date())));
    }
}
