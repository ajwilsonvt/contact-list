package app;

/**
 * Resource of the API
 */
public class Contact {

    private String name;
    private long number;

    public Contact() {}

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) { this.number = number; }

}
