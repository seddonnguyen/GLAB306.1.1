package junit.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class PasswordComplyTest {
    @Test
    void testDoesPasswordComply() {
        boolean expectedResult = true;
        PasswordComply password = new PasswordComply("abcd1234");
        boolean actualResult = password.doesPasswordComply();
        assertEquals(expectedResult, actualResult, "Password conditions failed!");
        System.out.println("Password conditions success!");
    }

    @Test
    void testDoesPasswordExist() {
        PasswordComply password = new PasswordComply("abcd1234");
        assertThrows(java.sql.SQLException.class,
                     () -> { if (password.doesNotAlreadyExist()) { throw new NullPointerException(); } else { throw new SQLException(); } },
                     "SQL Exception was thrown.");
    }

    @Test
    void testEmptyPassword() {
        PasswordComply password = new PasswordComply("10023");

        assertThrows(NullPointerException.class,
                     () -> { if (password.doesNotAlreadyExist()) { throw new NullPointerException(); } else { throw new SQLException(); } },
                     "Null Exception was thrown but received SQL Exception.");
    }
}