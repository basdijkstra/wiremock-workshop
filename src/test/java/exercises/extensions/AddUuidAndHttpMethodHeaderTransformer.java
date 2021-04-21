package exercises.extensions;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import java.util.UUID;

public class AddUuidAndHttpMethodHeaderTransformer extends ResponseDefinitionTransformer {

    @Override
    public ResponseDefinition transform(
            Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters
    ) {
        /**
         * Transform the response by creating a new ResponseDefinition that add two headers:
         * - One header that contains an auto-generated UUID
         *   Use UUID.randomUUID().toString() to generate it
         *   The header name is specified as a parameter called 'headerName'
         * - One header that contains the HTTP method used in the request
         *   Use request.getMethod().value() to retrieve it
         *   The header name is 'methodName'
         */

        return null;
    }

    @Override
    public String getName() {
        return "add-uuid-and-method-name";
    }
}
