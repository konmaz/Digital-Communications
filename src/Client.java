

public class Client {
    public static void main(String[] args) {
        if (args.length < 4){
            throw new IllegalArgumentException("Number of supplied arguments is fewer than minimum 4.");
        }

        String IP = args[0];
        String port = args[1];
        String FN_ID = args[2];

        switch (FN_ID){
            case "1":
                System.out.println("Add new user");
                String username = args[3];
                //Send the username to the server STOP wait for reply
                break;
            default:
                System.out.println("FN_ID invalid");
        }
    }
}
