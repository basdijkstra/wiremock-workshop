package answers.extensions;

import com.github.tomakehurst.wiremock.core.Admin;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ServeEventListener;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogRequestWithTimestamp implements ServeEventListener {

    @Override
    public String getName() {
        return "log-loan-request-with-timestamp";
    }

    @Override
    public void afterComplete(ServeEvent serveEvent, Parameters parameters) {

        /**
         * Read the desired date format from the parameters (parameter name is 'format')
         * Use this to format the current date and print
         * '<timestamp>: Loan application request received'
         */

        String format = parameters.getString("format");

        System.out.printf(
                "%s: Loan application request received",
                new SimpleDateFormat(format).format(new Date())
        );
    }
}