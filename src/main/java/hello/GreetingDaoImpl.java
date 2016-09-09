package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
/**
 * Created by song on 9/9/16.
 */

@Component
public class GreetingDaoImpl implements GreetingDao {


    private static final Logger logger = (Logger) LoggerFactory.getLogger(GreetingDaoImpl.class);
    private Map<String, Greeting> greetings;
    private final AtomicLong counter = new AtomicLong();

    public GreetingDaoImpl() {
        this.greetings = new HashMap<String, Greeting>();
        this.greetings.put("World", new Greeting(counter.incrementAndGet(), "World", 100));
        this.greetings.put("a", new Greeting(counter.incrementAndGet(), "a", 450));
    }

    public Greeting findByName(String name) {
        queryDelay(3000L);
        logger.info(String.format("loading %s ... ", name));
        return searchOrCreate(name);
    }

    public void dumpCache() {
        logger.info(new String(new char[10]).replace("\0", "="));
        for (Greeting greeting : this.greetings.values()) {
            System.out.println(greeting);
        }
    }

    private Greeting searchOrCreate(String name) {
        if (this.greetings.containsKey(name)) {
            return this.greetings.get(name);
        }
        Greeting greeting = new Greeting(counter.incrementAndGet(), name);
        this.greetings.put(name, greeting);
        dumpCache();
        return greeting;
    }

    private void queryDelay(long seconds){
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
