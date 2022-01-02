import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void isUsernameValid1() {
        assertEquals(true,Account.isUsernameValid("username"));
    }

    @Test
    public void isUsernameValid2() {
        assertEquals(false,Account.isUsernameValid("username ")); // not valid username because contains space
    }

    @Test
    public void isUsernameValid3() {
        assertEquals(false,Account.isUsernameValid("ελληνικά")); // not valid username because contains greek characters
    }

    @Test
    public void isUsernameValid4() {
        assertEquals(true,Account.isUsernameValid("userName_2")); // not valid username because contains greek characters
    }

    @Test
    public void isUsernameValid5() {
        assertEquals(true,Account.isUsernameValid("USERNAME")); // not valid username because contains greek characters
    }

    @Test
    public void isUsernameValid6() {
        assertEquals(false,Account.isUsernameValid("")); // not valid username because contains greek characters
    }
}