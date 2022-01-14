import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ServerTest {

    @Test
    public void testServerStart() {
        Server.main(new String[]{"5000"});
    }


    @Test(expected = IllegalArgumentException.class)
    public void testServerStartWithoutArguments() {
        Server.main(new String[]{});
    }
}