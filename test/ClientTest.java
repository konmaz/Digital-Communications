import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ClientTest {
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
    public void testCreateAccount() {
        Server.main(new String[]{"5050"});

        Client.main(new String[]{"localhost", "5050", "1", "test"});
        Client.main(new String[]{"localhost", "5050", "1", "test2"});
        Client.main(new String[]{"localhost", "5050", "1", "test"});
        Client.main(new String[]{"localhost", "5050", "1", "test2"});
        Client.main(new String[]{"localhost", "5050", "1", "test "});
        Client.main(new String[]{"localhost", "5050", "1", ""});
        Client.main(new String[]{"localhost", "5050", "1", " "});
        Client.main(new String[]{"localhost", "5050", "1", "inv@-lid"});
        Client.main(new String[]{"localhost", "5050", "1", "tester"});

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
        Server.main(new String[]{"5050"});

        Client.main(new String[]{"localhost", "5050", "1", "test"});
        Client.main(new String[]{"localhost", "5050", "1", "test2"});
        Client.main(new String[]{"localhost", "5050", "1", "test"});
        Client.main(new String[]{"localhost", "5050", "1", "test2"});
        Client.main(new String[]{"localhost", "5050", "1", "test "});
        Client.main(new String[]{"localhost", "5050", "1", ""});
        Client.main(new String[]{"localhost", "5050", "1", " "});
        Client.main(new String[]{"localhost", "5050", "1", "inv@-lid"});
        Client.main(new String[]{"localhost", "5050", "1", "tester"});
        Client.main(new String[]{"localhost", "5050", "1", "tester"});
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
}