import java.util.ArrayList;

/**
 * A class that represents an account
 */
public class Account {
    private final String username;

    private final int authToken;
    ArrayList<Message> messageBox;

    private static int counter = 100;

    public Account(String username){
        this.username = username;
        authToken = Account.counter;
        Account.counter+=1;
    }

    public int getAuthToken() {
        return this.authToken;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Message> getMessageBox() {
        return messageBox;
    }

    public void addMessage(Message message){
        this.messageBox.add(message);
    }
}
