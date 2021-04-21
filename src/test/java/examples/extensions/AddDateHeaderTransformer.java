package examples.extensions;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.tomakehurst.wiremock.http.HttpHeader.httpHeader;

public class AddDateHeaderTransformer extends ResponseTransformer {

    @Override
    public Response transform(
            Request request, Response response, FileSource files, Parameters parameters
    ) {
        return Response.Builder.like(response).but()
                .headers(response.getHeaders().plus(
                        httpHeader(
                            "currentDate",
                            new SimpleDateFormat(
                                    parameters.getString("dateFormat")).format(new Date()))
                        )
                )
                .build();
    }

    @Override
    public String getName() {
        return "example";
    }

    @Override
    public boolean applyGlobally() {
        return true;
    }
}
