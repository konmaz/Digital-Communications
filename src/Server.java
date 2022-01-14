import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class represents a server, the server replies to request made by clients, it uses Java's RMI Protocol. Also parses the command line arguments.
 */
public class Server {

    public static void main(String[] args) {
        try {
            if (args.length != 1){
                throw new IllegalArgumentException("Argument port number is missing!");
            }
            int port = Integer.parseInt(args[0]);
            if (port < 0)
                System.exit(0);
            RemoteCommunicator stub = new RemoteCommunicator();
            // create the RMI registry on port
            Registry rmiRegistry = LocateRegistry.createRegistry(port);
            rmiRegistry.rebind("remCom", stub);
        } catch (RemoteException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
