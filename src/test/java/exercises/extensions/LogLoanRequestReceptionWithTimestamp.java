package exercises.extensions;

import com.github.tomakehurst.wiremock.core.Admin;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.PostServeAction;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;

public class LogLoanRequestReceptionWithTimestamp extends PostServeAction {

    @Override
    public String getName() {
        return "log-timestamp";
    }

    @Override
    public void doAction(ServeEvent serveEvent, Admin admin, Parameters parameters) {

        /**
         * Read the desired date format from the parameters (parameter name is 'format')
         * Use this to format the current date and print
         * '<timestamp>: Loan application request received'
         */

    }
}
