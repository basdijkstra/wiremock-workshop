package examples.usingextensions;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import examples.extensions.AddDateHeaderTransformer;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class ResponseTransformerTest {

    private RequestSpecification requestSpec;

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance().
            options(wireMockConfig().
                    port(9876).
                    extensions(new AddDateHeaderTransformer())
            ).build();

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void stubForResponseTransformer() {

        wiremock.stubFor(get(urlEqualTo("/response-transformer"))
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
