package exercises.extensions;

import com.github.tomakehurst.wiremock.extension.requestfilter.RequestFilterAction;
import com.github.tomakehurst.wiremock.extension.requestfilter.StubRequestFilter;
import com.github.tomakehurst.wiremock.http.Request;

public class HttpDeleteFilter extends StubRequestFilter {

    @Override
    public RequestFilterAction filter(Request request) {

        /***
         * Implement this request filter so that it inspects the
         * HTTP method used in the incoming request.
         * - If the HTTP method is a DELETE, we should filter out the request and
         *   return an HTTP 403 Forbidden. You can use the notPermitted() method in
         *   the ResponseDefinition class for this. Use 'HTTP DELETE is not allowed!'
         *   as the error message
         * - If the HTTP method is something other than DELETE,
         *   the request should be processed normally
         */

        return null;
    }

    @Override
    public String getName() {
        return "http-delete-filter";
    }
}
