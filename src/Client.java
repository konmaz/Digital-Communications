import javax.naming.AuthenticationException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a client, a client request from a server. Also parses the command line arguments.
 */
public class Client {
    public static void main(String[] args) {

        try {
            String IP = args[0];
            int port = Integer.parseInt(args[1]);
            Registry rmiRegistry = LocateRegistry.getRegistry(IP,port);
            CommunicatorInterface stub = (CommunicatorInterface) rmiRegistry.lookup("remCom");

            String FN_ID = args[2];
            try{
                switch (FN_ID) {
                    //Create Account (FN_ID: 1)
                    //               0       1        2    3
                    //java client <ip> <port number> 1 <username>
                    case "1":{
                        if (args.length != 4)
                            throw new IllegalArgumentException("The number of arguments is wrong.\n+"+ Arrays.toString(args));
                        String username = args[3];
                        System.out.println(stub.createAccount(username));
                        break;
                    }
                    // Show Accounts (FN_ID: 2)
                    //                0       1        2    3
                    //java client <ip> <port number> 2 <authToken>
                    case "2": {
                        if (args.length != 4)
                            throw new IllegalArgumentException("The number of arguments is wrong.\n+"+ Arrays.toString(args));
                        int authToken = Integer.parseInt(args[3]);
                        String[] listOfUserNames = stub.getAllUsernames(authToken);
                        for (int i = 0; i < listOfUserNames.length; i++) {
                            System.out.println((i + 1) + ". " + listOfUserNames[i]);
                        }
                        break;
                    }
                    //Send Message (FN_ID: 3)
                    //              0       1        2      3           4            5
                    //java client <ip> <port number> 3 <authToken> <recipient> <message_body>
                    case "3": {
                        if (args.length != 6)
                            throw new IllegalArgumentException("The number of arguments is wrong.\n+"+ Arrays.toString(args));
                        int authToken = Integer.parseInt(args[3]);
                        String recipient = args[4];
                        String messageBody = args[5];
                        stub.sendMessage(authToken, recipient, messageBody);
                        System.out.println("OK");
                        break;
                    }
                    //Show Inbox (FN_ID: 4)
                    //              0       1        2      3
                    //java client <ip> <port number> 4 <authToken>
                    case "4": {
                        if (args.length != 4)
                            throw new IllegalArgumentException("The number of arguments is wrong.\n+"+ Arrays.toString(args));
                        int authToken = Integer.parseInt(args[3]);
                        int i = 1;
                        ArrayList<Message> messages= stub.getAllUserMessages(authToken);
                        for (Message item : messages) {
                            if (!item.isRead()){
                                System.out.println(i+". from: "+item.getSender()+"*");
                            }
                            else{
                                System.out.println(i+". from: "+item.getSender());
                            }
                            i+=1;
                        }
                        break;
                    }
                    //ReadMessage (FN_ID: 5)
                    //              0        1       2      3           4
                    //java client <ip> <port number> 5 <authToken> <message_id>
                    case "5": {
                        if (args.length != 5)
                            throw new IllegalArgumentException("The number of arguments is wrong.\n+"+ Arrays.toString(args));
                        int authToken = Integer.parseInt(args[3]);
                        int messageID = Integer.parseInt(args[4]) - 1; // notice -1
                        Message message= stub.readMessage(authToken, messageID);
                        System.out.println(message.getBody());
                        break;
                    }
                    //DeleteMessage (FN_ID: 6)
                    //              0        1       2      3           4
                    //java client <ip> <port number> 6 <authToken> <message_id>
                    case "6":{
                        if (args.length != 5)
                            throw new IllegalArgumentException("The number of arguments is wrong.\n+"+ Arrays.toString(args));
                        int authToken = Integer.parseInt(args[3]);
                        int messageID = Integer.parseInt(args[4]) - 1; // notice -1
                        stub.deleteMessage(authToken, messageID);
                        System.out.println("OK");
                        break;
                    }
                    default:
                        System.out.println("FN_ID invalid");
                        break;
                }
            }catch (RemoteException e ){
                e.printStackTrace();

            }catch (NumberFormatException e){
                System.out.println("Wrong order of arguments!");
                System.out.println(Arrays.toString(args));
            }
            catch (IllegalArgumentException | AuthenticationException e){
                System.out.println(e.getMessage());
            }


        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
