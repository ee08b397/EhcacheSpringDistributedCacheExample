package hello;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by song on 9/9/16.
 */

@Component
public class GreetingDaoImpl implements GreetingDao {

    private Map<String, Greeting> greetings;
    private final AtomicLong counter = new AtomicLong();

    public GreetingDaoImpl() {
        this.greetings = new HashMap<String, Greeting>();
        this.greetings.put("World", new Greeting(counter.incrementAndGet(), "World", 100));
        this.greetings.put("a", new Greeting(counter.incrementAndGet(), "a", 450));
    }

    @Override
    public Greeting findByName(String name) {
        queryDelay(3000L);
        System.out.println(String.format("loading %s ... ", name));
        return searchOrCreate(name);
    }

    public void dumpCache() {
        System.out.println(new String(new char[10]).replace("\0", "="));
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