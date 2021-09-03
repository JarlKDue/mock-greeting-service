package org.acme;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import org.eclipse.microprofile.opentracing.Traced;
import org.jboss.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Random;

@RequestScoped
public class MockDatabaseRepository {

    private static final Logger LOG = Logger.getLogger(MockDatabaseRepository.class);

    @Inject
    Tracer tracer;

    @Inject
    MockDB mockDB;

    /**
     * Here we wrap the request to the mockDB in a try statement with a specific scope and create a span just for it.
     * @return
     */
    public String getGreeting() {
        Random random = new Random();
        //The span should be created as a child of the current span, but could also be a totally new span, if this makes more sense.
        Scope scope = tracer.buildSpan("mock-external-service").asChildOf(tracer.activeSpan()).startActive(true);
        try (scope) {
            Thread.sleep(random.nextInt(10)*1000);
            scope.span().setTag("fetching data from external service", "Greeting fetched from DB");
            return mockDB.getGreeting();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
