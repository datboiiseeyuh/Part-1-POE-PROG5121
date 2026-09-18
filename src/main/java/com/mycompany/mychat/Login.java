package com.mycompany.mychat;

/**
 *
 * @author Student
 */
public class Login {

    private String username;
    private String password;
    private String cellPhoneNumber;

    private String firstName;
    private String lastName;

    private boolean loginStatus;

    /*
     * Cell phone regular expression reference:
     * Oracle Java Documentation - Pattern Class
     * https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/util/regex/Pattern.html
     *
     * The regular expression checks for:
     * +27 followed by exactly 9 digits.
     */
    private final String cellPhoneRegex = "^\\+27[0-9]{9}$";

    public boolean checkUserName(String username) {

        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {

        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    public boolean checkCellPhoneNumber(String cellPhoneNumber) {

        return cellPhoneNumber.matches(cellPhoneRegex);
    }

    public String registerUser(String username, String password,
            String cellPhoneNumber) {

        if (!checkUserName(username)) {

            return "Username is not correctly formatted; please ensure that "
                    + "your username contains an underscore and is no more "
                    + "than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {

            return "Password is not correctly formatted; please ensure that "
                    + "the password contains at least eight characters, "
                    + "a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellPhoneNumber)) {

            return "Cell phone number incorrectly formatted or does not "
                    + "contain international code.";
        }

        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;

        return "User registered successfully.";
    }

    public boolean loginUser(String username, String password) {

        if (username.equals(this.username)
                && password.equals(this.password)) {

            loginStatus = true;
            return true;

        } else {

            loginStatus = false;
            return false;
        }
    }

    public String returnLoginStatus() {

        if (loginStatus) {

            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";

        } else {

            return "Username or password incorrect, please try again.";
        }
    }

    public void setName(String firstName, String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;
    }
}