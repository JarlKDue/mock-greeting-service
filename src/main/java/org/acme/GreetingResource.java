package org.acme;

import io.micrometer.core.instrument.MeterRegistry;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapExtractAdapter;
import io.opentracing.tag.Tags;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Random;

@Path("/v1")
public class GreetingResource {

    private static final Logger LOG = Logger.getLogger(GreetingResource.class);

    @Inject
    MeterRegistry registry;

    @Inject
    MockDatabaseRepository mockDatabaseRepository;

    @Inject
    @RestClient
    GreetingsProviderService greetingsProviderService;

    @GET
    @Path("/greeting")
    @Produces(MediaType.TEXT_PLAIN)
    public String greeting() throws InterruptedException {
        LOG.info("Received a request for a greeting");
        Random random = new Random();
            Thread.sleep(random.nextInt(10)*1000);
            return greetingsProviderService.getGreeting().toString();
    }

    /**
     * This request mocks a request that includes an external component that might not be covered by jaeger.
     * @return
     */
    @GET
    @Path("/external")
    @Produces(MediaType.TEXT_PLAIN)
    public String external(){
        LOG.info("Received a request to test a mock external service");
        return mockDatabaseRepository.getGreeting();
    }
}