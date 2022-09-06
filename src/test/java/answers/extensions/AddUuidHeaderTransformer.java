package answers.extensions;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import java.util.UUID;

public class AddUuidHeaderTransformer extends ResponseDefinitionTransformer {

    @Override
    public ResponseDefinition transform(
            Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters
    ) {
        /**
         * Transform the response by creating a new ResponseDefinition
         * that adds a header that contains an auto-generated UUID
         * - Use UUID.randomUUID().toString() to generate it
         * - The header name to be 'injected' into the response
         *   is specified as a parameter called 'uuidHeaderName'
         */

        return new ResponseDefinitionBuilder()
                .withHeader(
                        parameters.getString("uuidHeaderName"),
                        UUID.randomUUID().toString())
                .build();
    }

    @Override
    public String getName() {
        return "add-uuid-header";
    }
}
