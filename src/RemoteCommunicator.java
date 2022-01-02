import javax.naming.AuthenticationException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RemoteCommunicator implements CommunicatorInterFace{
    private static LinkedHashMap<String,Account> accountsLinkedHashMap;
    private static LinkedHashMap<Integer, Account> authIDMap;
    public RemoteCommunicator() {
        accountsLinkedHashMap = new LinkedHashMap<>(); // init
    }

    /**
     * A client requests an auth key, the client registers himself to the server.
     *
     * @param username The client's username.
     * @return the AUTH KEY
     * @throws RemoteException          If there is a problem with the connection.
     * @throws IllegalArgumentException If the username is already exist or username contains illegal characters.
     */
    @Override
    public int createAccount(String username) throws RemoteException, IllegalArgumentException {
        if (accountsLinkedHashMap.containsKey(username))
            throw new IllegalArgumentException("Username already exists");

        // TODO check if username does not contain illegal characters.

        Account newAccount = new Account(username);
        accountsLinkedHashMap.put(newAccount.getUsername(), newAccount);
        authIDMap.put(newAccount.getAuthToken(), newAccount);
        return newAccount.getAuthToken();
    }

    /**
     * A client requests to return all the accounts usernames.
     *
     * @param authKey The AUTH KEY.
     * @return A list of username strings.
     * @throws RemoteException         If there is a problem with the connection.
     * @throws AuthenticationException If there is a problem with the authKey.
     */
    @Override
    public String[] getAllUsernames(int authKey) throws RemoteException, AuthenticationException {
        checkAuthKey(authKey);
        String[] usernamesStrings = new String[accountsLinkedHashMap.size()];
        int i = 0;
        for (Account item : accountsLinkedHashMap.values()) {
            usernamesStrings[i] = item.getUsername();
            i+=1;
        }
        return usernamesStrings;
    }

    /**
     * A client request to send a message.
     *
     * @param authKey     The AUTH KEY.
     * @param recipient   The string username of the recipient.
     * @param messageBody The message text body to be sent.
     * @throws RemoteException          If there is a problem with the connection.
     * @throws AuthenticationException  If there is a problem with the authKey.
     * @throws IllegalArgumentException If the username of the recipient don't exist
     */
    @Override
    public void sendMessage(int authKey, String recipient, String messageBody) throws RemoteException, AuthenticationException, IllegalArgumentException {
        checkAuthKey(authKey);
        Account recipientAccountObj = accountsLinkedHashMap.get(recipient);
        Account senderAccountObj = authIDMap.get(authKey);
        if (recipient == null)
            throw new AuthenticationException("Recipient username don't exist");

        recipientAccountObj.addMessage(new Message(senderAccountObj.getUsername(), recipientAccountObj.getUsername(), messageBody));
    }

    /**
     * A client requests all the messages (inbox) for account with the authKey.
     *
     * @param authKey The AUTH KEY.
     * @return An ArrayList with all of his messages, @code null.
     * @throws RemoteException         If there is a problem with the connection.
     * @throws AuthenticationException If there is a problem with the authKey.
     */
    @Override
    public ArrayList<Message> getAllUserMessages(int authKey) throws RemoteException, AuthenticationException {
        checkAuthKey(authKey);

        return null;
    }

    /**
     * A client requests to mark a message from the authKey user inbox as read.
     *
     * @param authKey      The AUTH KEY.
     * @param posOfMessage The position of the message in the field of the Account Messages.
     * @return the message with ID.
     * @throws RemoteException         If there is a problem with the connection.
     * @throws AuthenticationException If there is a problem with the authKey.
     */
    @Override
    public Message readMessage(int authKey, int posOfMessage) throws RemoteException, AuthenticationException {
        checkAuthKey(authKey);

        return null;
    }

    /**
     * A client request to delete a message from the authKey user inbox .
     *
     * @param authKey      The AUTH KEY.
     * @param posOfMessage The position of the message in the field of the Account Messages.
     * @return A list of username strings.
     * @throws RemoteException         If there is a problem with the connection.
     * @throws AuthenticationException If there is a problem with the authKey.
     */
    @Override
    public void deleteMessage(int authKey, int posOfMessage) throws RemoteException, AuthenticationException {
        checkAuthKey(authKey);

    }

    /**
     * Check if the authKey is valid.
     * @param authKey The AUTH KEY.
     * @throws AuthenticationException Throws exception when the auth key don't belong to an account.
     */
    private void checkAuthKey(int authKey) throws AuthenticationException{
        if (!authIDMap.containsKey(authKey))
            throw new AuthenticationException("Invalid Auth Key");
    }
}
