package examples.usingextensions;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import examples.extensions.BodyLengthMatcher;
import examples.extensions.DatabaseWriter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

public class DatabaseWriterTest {

    private RequestSpecification requestSpec;

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance().
            options(wireMockConfig().
                    port(9876).
                    extensions(new DatabaseWriter())
            ).build();

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    public void stubForServeEventListener() {

        Map<String, Object> params = new HashMap<>();
        params.put("database", "requestsDB");

        wiremock.stubFor(get(urlEqualTo("/serve-event")).
                withServeEventListener("database-writer", Parameters.from(params)).
                willReturn(aResponse().withStatus(200))
        );
    }

    @Test
    public void sendRequest_shouldWriteRequestToDatabaseAfterServeIsComplete() {

        stubForServeEventListener();

        given().
                spec(requestSpec).
        when().
                get("/serve-event").
        then().
                assertThat().
                statusCode(200);
    }
}
