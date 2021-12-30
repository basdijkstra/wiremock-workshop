package examples.usingextensions;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import examples.extensions.CreateDateHeaderDefinitionTransformer;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class ResponseDefinitionTransformerTest {

    private RequestSpecification requestSpec;

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance().
            options(wireMockConfig().
                    port(9876).
                    extensions(new CreateDateHeaderDefinitionTransformer())
            ).build();

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void stubForResponseDefinitionTransformer() {

        wiremock.stubFor(get(urlEqualTo("/response-definition-transformer"))
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