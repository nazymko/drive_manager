package rt.models;

/**
 * User: Andrew.Nazymko
 */
public class Message {
    private String from;
    private String message;
    private long date;

    public Message(String message, String from) {
        this.message = message;
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", from='" + from + '\'' +
                '}';
    }

    public void setDate(long date) {
        this.date = date;
    }
}
