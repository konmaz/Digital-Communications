import java.io.Serializable;

/**
 * This class implements a message, every message is private and can be seen only by the recipient.
 */
public class Message implements Serializable { // Serializable to be able to sent it over RMI
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
