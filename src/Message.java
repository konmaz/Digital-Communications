public class Message {
    private boolean isRead;
    String sender;
    String receiver;
    String body;

    public Message(String sender, String receiver, String body) {
        this.isRead = false;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
    }
}
