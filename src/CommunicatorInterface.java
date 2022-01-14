
import javax.naming.AuthenticationException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * This interface class dictates which requests are available for the Client.
 */
public interface CommunicatorInterface extends Remote {

    /**
     * A client requests an auth key, the client registers himself to the server.
     *
     * @param username The client's username.
     * @return the AUTH KEY
     * @throws RemoteException If there is a problem with the connection.
     * @throws IllegalArgumentException If the username is already exist or username contains illegal characters.
     */
    int createAccount(String username) throws RemoteException, IllegalArgumentException;

    /**
     * A client requests to return all the accounts usernames.
     *
     * @param authKey The AUTH KEY.
     * @return A list of username strings.
     * @throws RemoteException If there is a problem with the connection.
     * @throws AuthenticationException If there is a problem with the authKey.
     */
    String[] getAllUsernames(int authKey) throws RemoteException, AuthenticationException;

    /**
     * A client request to send a message.
     * @param authKey The AUTH KEY.
     * @param recipient The string username of the recipient.
     * @param messageBody The message text body to be sent.
     * @throws RemoteException If there is a problem with the connection.
     * @throws AuthenticationException If there is a problem with the authKey.
     * @throws IllegalArgumentException If the username of the recipient don't exist
     */
    void sendMessage(int authKey, String recipient, String messageBody) throws RemoteException, AuthenticationException, IllegalArgumentException;

    /**
     * A client requests all the messages (inbox) for account with the authKey.
     * @param authKey The AUTH KEY.
     * @return An ArrayList with all of his messages, @code null.
     * @throws RemoteException If there is a problem with the connection.
     * @throws AuthenticationException If there is a problem with the authKey.
     */
    ArrayList<Message> getAllUserMessages(int authKey) throws RemoteException, AuthenticationException;

    /**
     * A client requests to mark a message from the authKey user inbox as read.
     * @param authKey The AUTH KEY.
     * @param posOfMessage The position of the message in the field of the Account Messages.
     * @return the message with ID.
     * @throws RemoteException If there is a problem with the connection.
     * @throws AuthenticationException If there is a problem with the authKey.
     * @throws IllegalArgumentException If the posOfMessage points to a message that don't exist
     */
    Message readMessage(int authKey, int posOfMessage) throws RemoteException, AuthenticationException, IllegalArgumentException;

    /**
     * A client request to delete a message from the authKey user inbox .
     * @param authKey The AUTH KEY.
     * @param posOfMessage The position of the message in the field of the Account Messages.
     * @throws RemoteException If there is a problem with the connection.
     * @throws AuthenticationException If there is a problem with the authKey.
     * @throws IllegalArgumentException If the posOfMessage points to a message that don't exist
     */
    void deleteMessage(int authKey, int posOfMessage) throws RemoteException, AuthenticationException, IllegalArgumentException;


}
