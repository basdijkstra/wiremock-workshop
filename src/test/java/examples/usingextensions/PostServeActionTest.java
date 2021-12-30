package examples.usingextensions;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import examples.extensions.WriteToDBAction;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class PostServeActionTest {

    private RequestSpecification requestSpec;

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance().
            options(wireMockConfig().
                    port(9876).
                    extensions(new WriteToDBAction())
            ).build();

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void stubForPostServeAction() {

        wiremock.stubFor(get(urlEqualTo("/post-serve-action"))
                .withPostServeAction("write-to-database",
                        Parameters.one("dbName", "name-of-my-database")
                )
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Authorized")
                ));
    }

    @Test
    public void callWireMockWithPostServeAction_checkStatusCodeEquals200() {

        stubForPostServeAction();

        given().
                spec(requestSpec).
        when().
                get("/post-serve-action").
        then().
                assertThat().
                statusCode(200);
    }
}
