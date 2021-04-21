package exercises.extensions;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.matching.MatchResult;
import com.github.tomakehurst.wiremock.matching.RequestMatcherExtension;

import java.util.List;

public class MultipleHttpVerbsMatcher extends RequestMatcherExtension {

    @Override
    public String getName() {
        return "multiple-verbs-matcher";
    }

    @Override
    public MatchResult match(Request request, Parameters parameters) {

        /**
         * Implement this custom matcher so that it:
         * - Reads the list of accepted verbs from the 'acceptedVerbs' parameter
         *   (use parameters.getList() to read the 'acceptedVerbs' parameter value
         *   into an object of type List<?>)
         * - Only return a positive match result when this list contains the
         *   HTTP method used in the incoming request
         *   You can retrieve this using request.getMethod().value()
         */

        return null;
    }
}
