package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
/**
 * Created by song on 9/9/16.
 */

@Component
public class GreetingDaoImpl implements GreetingDao {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(GreetingDaoImpl.class);
    private final AtomicLong counter = new AtomicLong();

    public Greeting findByName(String name) {
        queryDelay(3000L);
        logger.info(String.format("loading %s ... ", name));
        return new Greeting(counter.incrementAndGet(), name);
    }

    private void queryDelay(long seconds){
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
