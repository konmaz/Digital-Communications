import java.io.Serializable;

public class Message implements Serializable {
    private boolean isRead;
    private final String sender;
    private final String receiver;
    private final String body;

    public Message(String sender, String receiver, String body) {
        this.isRead = false;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setAsRead(){
        this.isRead = true;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getBody() {
        return body;
    }
}
