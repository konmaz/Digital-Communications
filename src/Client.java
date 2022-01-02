import javax.naming.AuthenticationException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        if (args.length < 4){
            throw new IllegalArgumentException("Number of supplied arguments is fewer than minimum 4.");
        }

        String IP = args[0];
        int port = Integer.parseInt(args[1]);
        String FN_ID = args[2];
        try {
            Registry rmiRegistry = LocateRegistry.getRegistry(IP,port);
            CommunicatorInterFace stub = (CommunicatorInterFace) rmiRegistry.lookup("remCom");
            try{
                if ("1".equals(FN_ID)) {
                    String username = args[3];
                    System.out.println(stub.createAccount(username));
                }else if ("2".equals(FN_ID)) {
                    int authToken = Integer.parseInt(args[3]);
                    String[] listOfUserNames = stub.getAllUsernames(authToken);
                    for (int i = 0; i < listOfUserNames.length; i++) {
                        System.out.println((i+1)+". "+listOfUserNames[i]);
                    }
                }
                else {
                    System.out.println("FN_ID invalid");
                }
            }catch (RemoteException e){
                e.printStackTrace();
            }catch (IllegalArgumentException | AuthenticationException e){
                System.out.println(e.getMessage());
            }


        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
