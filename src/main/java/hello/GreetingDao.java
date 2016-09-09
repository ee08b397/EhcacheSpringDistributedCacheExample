package hello;

/**
 * Created by song on 9/9/16.
 */
public interface GreetingDao {
    Greeting findByName(String name);
}
