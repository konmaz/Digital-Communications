import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Server {


    public static void main(String[] args) {
        try {

            //int port = Integer.parseInt(args[0]);
            int port = 5000;

            RemoteCommunicator stub = new RemoteCommunicator();
            // create the RMI registry on port
            Registry rmiRegistry = LocateRegistry.createRegistry(port);

            System.out.println("Server is running at port "+port);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
