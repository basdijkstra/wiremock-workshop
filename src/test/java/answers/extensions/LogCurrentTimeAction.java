package answers.extensions;

import com.github.tomakehurst.wiremock.core.Admin;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.PostServeAction;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogCurrentTimeAction extends PostServeAction {

    @Override
    public String getName() {
        return "log-timestamp";
    }

    @Override
    public void doAction(ServeEvent serveEvent, Admin admin, Parameters parameters) {

        /**
         * Read the desired date format from the parameters (parameter name is 'format')
         * Use this to format the current date and print
         * 'Response served on: <current_date_and_time> to the console
         */

        String format = parameters.getString("format");

        System.out.println("Response served on: " + new SimpleDateFormat(format).format(new Date()));
    }
}
