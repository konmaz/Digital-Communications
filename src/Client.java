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
                int authToken = Integer.parseInt(args[3]);
                switch (FN_ID) {
                    case "1":
                        String username = args[3];
                        System.out.println(stub.createAccount(username));
                        break;
                    case "2": {
                        String[] listOfUserNames = stub.getAllUsernames(authToken);
                        for (int i = 0; i < listOfUserNames.length; i++) {
                            System.out.println((i + 1) + ". " + listOfUserNames[i]);
                        }
                        break;
                    }
                    case "3": {
                        String recipient = args[4];
                        String messageBody = args[5];
                        stub.sendMessage(authToken, recipient, messageBody);
                        System.out.println("OK");
                        break;
                    }
                    case "4": {
                        int i = 1;
                        for (Message item : stub.getAllUserMessages(authToken)) {
                            if (!item.isRead())
                                System.out.println(i+". from: "+item.sender+"*");
                            else
                                System.out.println(i+". from: "+item.sender);
                            i+=1;
                        }
                    }
                    case "5": {
                        int messageID = Integer.parseInt(args[4]) - 1; // notice -1
                        Message message= stub.readMessage(authToken, messageID);
                        System.out.println(message.body);
                        message.setAsRead();
                    }
                    case "6":{
                        int messageID = Integer.parseInt(args[4]) - 1; // notice -1
                        stub.deleteMessage(authToken, messageID);
                        System.out.println("OK");
                    }
                    default:
                        System.out.println("FN_ID invalid");
                        break;
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
