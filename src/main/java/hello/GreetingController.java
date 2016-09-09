package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by song on 9/8/16.
 */

@RestController
public class GreetingController {

    private static final String template = "Hello, %s! ";

    private GreetingDaoImpl greetingDaoImpl;

    public GreetingController() {
        this.greetingDaoImpl = new GreetingDaoImpl();
    }

    @Cacheable("greetingRequest")
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return this.greetingDaoImpl.findByName(name);
    }
}
