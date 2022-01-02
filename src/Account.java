import java.util.ArrayList;

public class Account {
    private final String username;
    private int authToken;
    ArrayList<Message> messageBox;
    private static int counter = 0;
    public Account(String username){
        this.username = username;
        authToken = Account.counter;
        Account.counter+=1;
    }

    public int getAuthToken() {
        return this.authToken;
    }
}
