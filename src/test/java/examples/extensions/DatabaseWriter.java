package examples.extensions;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ServeEventListener;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;

public class DatabaseWriter implements ServeEventListener {

    @Override
    public String getName() {
        return "database-writer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }

    @Override
    public void afterComplete(ServeEvent serveEvent, Parameters parameters) {

        String database = parameters.getString("database");

        System.out.println("Writing to database: " + database);
    }
}
