package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;

@RegisterRestClient(configKey = "greetings-provider", baseUri = "http://greetings-service-provider:8084/v1/greeting")
public interface GreetingsProviderService {

    @GET
    Greeting getGreeting();
}
