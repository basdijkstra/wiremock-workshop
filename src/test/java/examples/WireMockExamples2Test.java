package examples;

import com.github.tomakehurst.wiremock.common.DateTimeUnit;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest(httpPort = 9876)
public class WireMockExamples2Test {

    public void setupStubURLMatching() {

        stubFor(get(urlEqualTo("/urlmatching"))
            .willReturn(aResponse()
                .withBody("URL matching")
            ));
    }

    public void setupStubHeaderMatching() {

        stubFor(get(urlEqualTo("/headermatching"))
            .withHeader("Content-Type", containing("application/json"))
            .withHeader("DoesntExist", absent())
            .willReturn(aResponse()
                .withBody("Header matching")
            ));
    }

    public void setupStubLogicalAndHeaderMatching() {

        stubFor(get(urlEqualTo("logical-or-matching"))
            .withHeader("my-header",
                matching("[a-z]+").and(containing("somevalue"))
            )
            .willReturn(aResponse()
                .withBody("Logical AND matching"))
        );
    }

    public void setupStubLogicalAndHeaderMatchingMoreVerbose() {

        stubFor(get(urlEqualTo("logical-or-matching"))
            .withHeader("my-header", and(
                matching("[a-z]+"),
                containing("somevalue"))
            )
            .willReturn(aResponse()
                .withBody("Logical AND matching, a little more verbose"))
        );
    }

    public void setupStubLogicalOrHeaderMatching() {

        stubFor(get(urlEqualTo("logical-or-matching"))
            .withHeader("Content-Type",
                equalTo("application/json").or(absent())
            )
            .willReturn(aResponse()
                .withBody("Logical OR matching"))
        );
    }

    public void setupStubAfterSpecificDateMatching() {

        stubFor(get(urlEqualTo("date-is-after"))
            .withHeader("my-date",
                after("2021-07-01T00:00:00Z")
            )
            .willReturn(aResponse()
                .withBody("Date is after midnight, July 1, 2021"))
        );
    }

    public void setupStubRelativeToCurrentDateMatching() {

        stubFor(get(urlEqualTo("date-is-relative-to-now"))
            .withHeader("my-date",
                beforeNow().expectedOffset(1, DateTimeUnit.MONTHS)
            )
            .willReturn(aResponse()
                .withBody("Date is at least 1 month before current date"))
        );
    }

    public void setupStubRequestBodyValueMatching() {

        stubFor(post(urlEqualTo("/request-body-matching"))
                .withRequestBody(
                        matchingJsonPath("$.fruits[?(@.banana=='2')]")
                )
                .willReturn(aResponse()
                        .withStatus(200)
                )
        );
    }

    public void setupStubReturningErrorCode() {

        stubFor(get(urlEqualTo("/errorcode"))
            .willReturn(aResponse()
                .withStatus(500)
                .withStatusMessage("Status message goes here")
            ));
    }

    public void setupStubFixedDelay() {

        stubFor(get(urlEqualTo("/fixeddelay"))
            .willReturn(aResponse()
                .withFixedDelay(2000)
            ));
    }

    public void setupStubBadResponse() {

        stubFor(get(urlEqualTo("/badresponse"))
            .willReturn(aResponse()
                .withFault(Fault.MALFORMED_RESPONSE_CHUNK)
            ));
    }
}
