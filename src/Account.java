import com.sun.xml.internal.ws.util.StringUtils;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * A class that represents an account
 */
public class Account {
    private final String username;

    private final int authToken;
    ArrayList<Message> messageBox;

    private static int counter = 100;

    public Account(String username) throws IllegalArgumentException{
        if (!isUsernameValid(username))
            throw new IllegalArgumentException("Invalid Username");
        this.username = username;
        authToken = Account.counter;
        Account.counter+=1;
        this.messageBox = new ArrayList<>();
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

    /**
     * Checks if a username contains illegal characters
     * @param username The username to be checked
     * @return True if the username is valid false if the username contains illegal characters; or its length is zero.
     */
    public static boolean isUsernameValid(String username){
        Pattern p = Pattern.compile("^[A-Za-z0-9_]*$");
        return p.matcher(username).find() && !username.isEmpty();
    }
}
