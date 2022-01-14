import javax.naming.AuthenticationException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * This class implements the interface CommunicatorInterface, this class is responsible for the logic side of the server, it handles requests and has also a list of all Accounts.
 */
public class RemoteCommunicator extends UnicastRemoteObject implements CommunicatorInterface {
    private final LinkedHashMap<String, Account> accountsLinkedHashMap;
    private final LinkedHashMap<Integer, Account> authIDMap;

    public RemoteCommunicator() throws RemoteException {
        super();
        accountsLinkedHashMap = new LinkedHashMap<>(); // init
        authIDMap = new LinkedHashMap<>(); // init
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
            throw new IllegalArgumentException("Sorry, the user already exists");

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
        getAccountFromAUTHID(authKey);
        String[] usernamesStrings = new String[accountsLinkedHashMap.size()];
        int i = 0;
        for (Account item : accountsLinkedHashMap.values()) {
            usernamesStrings[i] = item.getUsername();
            i += 1;
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
        Account senderAccountObj = getAccountFromAUTHID(authKey);
        Account recipientAccountObj = accountsLinkedHashMap.get(recipient);
        if (recipient == null)
            throw new AuthenticationException("User does not exist");

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
        return getAccountFromAUTHID(authKey).getMessageBox();
    }

    /**
     * A client requests to mark a message from the authKey user inbox as read.
     *
     * @param authKey      The AUTH KEY.
     * @param posOfMessage The position of the message in the field of the Account Messages.
     * @return the message with ID.
     * @throws RemoteException         If there is a problem with the connection.
     * @throws AuthenticationException If there is a problem with the authKey.
     * @throws IllegalArgumentException If the posOfMessage points to a message that don't exist
     */
    @Override
    public Message readMessage(int authKey, int posOfMessage) throws RemoteException, AuthenticationException, IllegalArgumentException {
        try{
            Message message = getAccountFromAUTHID(authKey).getMessageBox().get(posOfMessage);
            message.setAsRead();
            return message;
        }catch (IndexOutOfBoundsException e){
            throw new IllegalArgumentException("Message does not exist");
        }
    }

    /**
     * A client request to delete a message from the authKey user inbox .
     *
     * @param authKey      The AUTH KEY.
     * @param posOfMessage The position of the message in the field of the Account Messages.
     * @throws RemoteException         If there is a problem with the connection.
     * @throws AuthenticationException If there is a problem with the authKey.
     */
    @Override
    public void deleteMessage(int authKey, int posOfMessage) throws RemoteException, AuthenticationException {
        try {
            getAccountFromAUTHID(authKey).getMessageBox().remove(posOfMessage);
        }catch (IndexOutOfBoundsException e){
            throw new IllegalArgumentException("Message does not exist");
        }
    }

    /**
     * Check if the authKey is valid.
     *
     * @param authKey The AUTH KEY.
     * @return the correspondence Account-AUth ID.
     * @throws AuthenticationException Throws exception when the auth key don't belong to an account.
     */
    private Account getAccountFromAUTHID(int authKey) throws AuthenticationException {
        if (!authIDMap.containsKey(authKey))
            throw new AuthenticationException("Invalid Auth Token");
        return authIDMap.get(authKey);
    }
}