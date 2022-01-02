import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CommunicatorInterFace extends Remote {
    /**
     * A request from the client to the server
     * @param username
     * @return
     * @throws RemoteException
     */
    public int createAccount(String username) throws RemoteException;


}
