package org.acme;

import org.jboss.logging.Logger;
import javax.enterprise.context.RequestScoped;
import java.util.Random;

@RequestScoped
public class MockDB {

    private static final Logger LOG = Logger.getLogger(MockDB.class);


    public String getGreeting() throws InterruptedException {
        Random random = new Random();
        Thread.sleep(random.nextInt(10) * 1000);
        return "Hello World";
    }
}
