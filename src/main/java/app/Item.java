package app;

/**
 * Items are the resources of the API
 */
public class Item {

    private final long ID;
    private String value;

    public Item(long id, String value) {
        this.ID = id;
        this.value = value;
    }

    public long getID() {
        return ID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) { this.value = value; }

}
