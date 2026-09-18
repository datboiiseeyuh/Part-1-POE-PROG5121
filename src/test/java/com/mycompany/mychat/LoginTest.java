package com.mycompany.mychat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {

    Login login = new Login();

    @Test
    public void testCheckUserNameValid() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUserNameInvalid() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexityValid() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexityInvalid() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumberValid() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumberInvalid() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testLoginSuccessful() {

        login.registerUser(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(
                login.loginUser(
                        "kyl_1",
                        "Ch&&sec@ke99!"
                )
        );
    }

    @Test
    public void testLoginFailed() {

        login.registerUser(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(
                login.loginUser(
                        "wrong",
                        "wrong"
                )
        );
    }

    @Test
    public void testRegisterUserSuccessful() {

        assertEquals(
                "User registered successfully.",
                login.registerUser(
                        "kyl_1",
                        "Ch&&sec@ke99!",
                        "+27838968976"
                )
        );
    }

    @Test
    public void testRegisterUserInvalidUsername() {

        assertEquals(
                "Username is not correctly formatted; please ensure that "
                + "your username contains an underscore and is no more "
                + "than five characters in length.",
                login.registerUser(
                        "kyle!!!!!!!",
                        "Ch&&sec@ke99!",
                        "+27838968976"
                )
        );
    }

    @Test
    public void testRegisterUserInvalidPassword() {

        assertEquals(
                "Password is not correctly formatted; please ensure that "
                + "the password contains at least eight characters, "
                + "a capital letter, a number, and a special character.",
                login.registerUser(
                        "kyl_1",
                        "password",
                        "+27838968976"
                )
        );
    }

    @Test
    public void testRegisterUserInvalidCellPhone() {

        assertEquals(
                "Cell phone number incorrectly formatted or does not "
                + "contain international code.",
                login.registerUser(
                        "kyl_1",
                        "Ch&&sec@ke99!",
                        "08966553"
                )
        );
    }
}


