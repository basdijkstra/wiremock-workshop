package examples.usingextensions;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import examples.extensions.HttpDeleteFilter;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class UsingRequestFilterTest {

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance().
            options(wireMockConfig().
                    port(9876).
                    extensions(new HttpDeleteFilter())
            ).build();
}
