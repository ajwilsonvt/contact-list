package app;

/**
 * Display text
 */
public class Text {

    private final long ID;
    private final String CONTENT;

    public Text(long id, String content) {
        this.ID = id;
        this.CONTENT = content;
    }

    public long getID() {
        return ID;
    }

    public String getContent() {
        return CONTENT;
    }

}
