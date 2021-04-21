package examples.usingextensions;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import examples.extensions.CreateDateHeaderDefinitionTransformer;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static io.restassured.RestAssured.given;

public class ResponseDefinitionTransformer {

    private RequestSpecification requestSpec;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(
            new WireMockConfiguration().port(9876).extensions(new CreateDateHeaderDefinitionTransformer())
    );

    @Before
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void stubForResponseDefinitionTransformer() {

        stubFor(get(urlEqualTo("/response-definition-transformer"))
                .willReturn(aResponse()
                        .withTransformerParameter("dateFormat", "dd-MM-yyyy")
                ));
    }

    @Test
    public void callWireMockWithTransformedResponseDefinition_checkCurrentDateHeaderIsCorrect() {

        stubForResponseDefinitionTransformer();

        given().
                spec(requestSpec).
        when().
                get("/response-definition-transformer").
        then().
                assertThat().
                statusCode(200).
        and().
                header("currentDate", org.hamcrest.Matchers.equalTo(new SimpleDateFormat("dd-MM-yyyy").format(new Date())));
    }
}