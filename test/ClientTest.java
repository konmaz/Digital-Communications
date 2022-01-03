import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class ClientTest {

    static String port = "5000";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void test() {
        Server.main(new String[]{port});

        Client.main(new String[]{"localhost", port, "1", "test"}); // 100
        Client.main(new String[]{"localhost", port, "1", "test2"}); // 101
        Client.main(new String[]{"localhost", port, "1", "test"}); // Sorry, the user already exists
        Client.main(new String[]{"localhost", port, "1", "test2"}); // Sorry, the user already exists
        Client.main(new String[]{"localhost", port, "1", ""}); // Invalid Username
        Client.main(new String[]{"localhost", port, "1", " "}); // Invalid Username
        Client.main(new String[]{"localhost", port, "1", "inv@-lid"}); // Invalid Username
        Client.main(new String[]{"localhost", port, "1", "tester"}); // 102
        Client.main(new String[]{"localhost", port, "2", "-1"}); // Invalid auth token
        Client.main(new String[]{"localhost", port, "2", "101"}); // "1. test\n2. test2\n3. tester\n"
        Client.main(new String[]{"localhost", port, "1", "brand_New_Account"}); // 103
        Client.main(new String[]{"localhost", port, "2", "102"}); // "1. test\n2. test2\n3. tester\n4. brand_New_Account"

        Client.main(new String[]{"localhost", port, "3", "100", "test2", "Hello, test2 I'm test"}); // Message from test to test2 "OK"
        Client.main(new String[]{"localhost", port, "4", "101"}); // Show Inbox "1. test2*"
        Client.main(new String[]{"localhost", port, "5", "101", "1"}); // Show Message 1 "Hello, test2 I'm test"
        Client.main(new String[]{"localhost", port, "4", "101"}); // Show Inbox "1. test2"
        Client.main(new String[]{"localhost", port, "5", "101", "0"}); // Show Message that does not exist
        Client.main(new String[]{"localhost", port, "5", "100", "0"}); // Show Message from account without messages
        Client.main(new String[]{"localhost", port, "6", "100", "0"}); // Delete a Message from account without messages
        Client.main(new String[]{"localhost", port, "6", "101", "1"}); // Delete Message 1 from account test2
        Client.main(new String[]{"localhost", port, "4", "101"}); // Show Inbox "" (Empty Inbox)

        Client.main(new String[]{"localhost", port, "6", "-1", "1"}); // Delete Message with wrong AUTH ID
        String expected = ("100\n" +
                "101\n" +
                "Sorry, the user already exists\n" +
                "Sorry, the user already exists\n" +
                "Invalid Username\n" +
                "Invalid Username\n" +
                "Invalid Username\n" +
                "102\n"+

                "Invalid Auth Token\n" +
                "1. test\n" +
                "2. test2\n" +
                "3. tester\n" +
                "103\n"+
                "1. test\n" +
                "2. test2\n" +
                "3. tester\n" +
                "4. brand_New_Account\n" +

                "OK\n" +
                "1. from: test*\n"+
                "Hello, test2 I'm test\n"+
                "1. from: test\n"+
                "Message does not exist\n" +
                "Message does not exist\n" +
                "Message does not exist\n" +
                "OK"+
                "\n" +// Empty Inbox Nothing get printed
                "Invalid Auth Token\n"

        )

                .replaceAll("\\n|\\r\\n", System.getProperty("line.separator"));
        assertEquals(expected, outContent.toString());
    }


}