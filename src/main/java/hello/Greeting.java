package hello;

/**
 * Created by song on 9/8/16.
 */

public class Greeting {

    private final long id;
    private final String name;
    private int balance;

    public Greeting(long id, String content) {
        this.id = id;
        this.name = content;
        this.balance = 0;
    }

    public Greeting(long id, String content, int balance) {
        this.id = id;
        this.name = content;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Greeting)) return false;

        Greeting greeting = (Greeting) o;

        if (getId() != greeting.getId()) return false;
        if (balance != greeting.balance) return false;
        return getName().equals(greeting.getName());

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + balance;
        return result;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
