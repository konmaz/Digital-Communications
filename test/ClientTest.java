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
        //Server.main(new String[]{port});
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testCreateAccount() {
        Server obj = new Server();
        obj.forTesting(new String[]{port});
        Client.main(new String[]{"localhost", port, "1", "test"});
        Client.main(new String[]{"localhost", port, "1", "test2"});
        Client.main(new String[]{"localhost", port, "1", "test"});
        Client.main(new String[]{"localhost", port, "1", "test2"});
        Client.main(new String[]{"localhost", port, "1", "test "});
        Client.main(new String[]{"localhost", port, "1", ""});
        Client.main(new String[]{"localhost", port, "1", " "});
        Client.main(new String[]{"localhost", port, "1", "inv@-lid"});
        Client.main(new String[]{"localhost", port, "1", "tester"});

        String expected = ("100\n" +
                "101\n" +
                "Sorry, the user already exists\n" +
                "Sorry, the user already exists\n" +
                "Invalid Username\n" +
                "Invalid Username\n" +
                "Invalid Username\n" +
                "Invalid Username\n" +
                "102")

                .replaceAll("\\n|\\r\\n", System.getProperty("line.separator"));
        assertEquals(expected, outContent.toString().trim());
    }

    @Test
    public void testShowAccounts(){
        Server obj = new Server();
        obj.forTesting(new String[]{port});

        Client.main(new String[]{"localhost", port, "1", "test"}); // 100
        Client.main(new String[]{"localhost", port, "1", "test2"}); // 101
        Client.main(new String[]{"localhost", port, "1", "test2"}); // Sorry ...
        Client.main(new String[]{"localhost", port, "1", "test "}); // Sorry ...
        Client.main(new String[]{"localhost", port, "1", ""}); //Invalid username
        Client.main(new String[]{"localhost", port, "1", " "}); //Invalid username
        Client.main(new String[]{"localhost", port, "1", "inv@-lid"}); // Invalid username
        Client.main(new String[]{"localhost", port, "1", "tester"}); // 102
        Client.main(new String[]{"localhost", port, "2", "100"});
        Client.main(new String[]{"localhost", port, "2", "-1"}); // Invalid auth token
        Client.main(new String[]{"localhost", port, "2", "101"});
        Client.main(new String[]{"localhost", port, "1", "brand_New_Account"}); // 103
        Client.main(new String[]{"localhost", port, "2", "102"});
        String expected = ("100\n" +
                "101\n" +
                "Sorry, the user already exists\n" +
                "Invalid Username\n" +
                "Invalid Username\n" +
                "Invalid Username\n" +
                "Invalid Username\n" +
                "102\n" +
                "1. test\n" +
                "2. test2\n" +
                "3. tester\n" +
                "Invalid Auth Token\n" +
                "1. test\n" +
                "2. test2\n" +
                "3. tester\n" +
                "103\n"+
                "1. test\n" +
                "2. test2\n" +
                "3. tester\n" +
                "4. brand_New_Account")

                .replaceAll("\\n|\\r\\n", System.getProperty("line.separator"));
        assertEquals(expected, outContent.toString().trim());
    }
}