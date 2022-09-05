package examples.extensions;

import com.github.tomakehurst.wiremock.extension.requestfilter.RequestFilterAction;
import com.github.tomakehurst.wiremock.extension.requestfilter.StubRequestFilter;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

public class HttpDeleteFilter extends StubRequestFilter {

    @Override
    public RequestFilterAction filter(Request request) {

        if (request.getMethod().equals(RequestMethod.DELETE)) {

            return RequestFilterAction.stopWith(ResponseDefinition.notPermitted("HTTP DELETE is not allowed!"));
        }

        return RequestFilterAction.continueWith(request);
    }

    @Override
    public String getName() {
        return "http-delete-filter";
    }
}
