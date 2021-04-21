package examples.extensions;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateDateHeaderDefinitionTransformer extends ResponseDefinitionTransformer {

    @Override
    public ResponseDefinition transform(
            Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters
    ) {
        return new ResponseDefinitionBuilder()
                .withHeader(
                        "currentDate",
                        new SimpleDateFormat(parameters.getString("dateFormat")).format(new Date()))
                .withStatus(200)
                .build();
    }

    @Override
    public String getName() {
        return "example";
    }
}
