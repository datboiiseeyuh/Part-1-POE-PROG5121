package com.mycompany.mychat;

import java.util.Scanner;

/**
 *
 * @author Student
 */

/**
 * Main class responsible for running the MyChat application
 * and interacting with the user.
 */
public class Main {

    public static void main(String[] args) {

        // Collects the user's personal information during registration.
        Scanner input = new Scanner(System.in);

        Login login = new Login();

        System.out.println("===== Welcome to MyChat =====");

        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();

        System.out.print("Enter your username: ");
        String username = input.nextLine();

        System.out.print("Enter your password: ");
        String password = input.nextLine();

        System.out.print("Enter your South African cell phone number: ");
        String cellPhoneNumber = input.nextLine();
        
        // Allows the user to enter their credentials and attempts to log them in.

        login.setName(firstName, lastName);

        String registrationMessage = login.registerUser(
                username,
                password,
                cellPhoneNumber
        );

        System.out.println();
        System.out.println(registrationMessage);
        
        // Displays the available options to the user.

        if (registrationMessage.equals("User registered successfully.")) {

            System.out.println();
            System.out.println("===== Login =====");

            System.out.print("Enter your username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = input.nextLine();

            login.loginUser(loginUsername, loginPassword);

            System.out.println(login.returnLoginStatus());
        }

        input.close();
    }
}


/*
 * REFERENCES
 *
 * JUnit (2026). JUnit 5 User Guide. Available at:
 * https://junit.org/junit5/docs/current/user-guide/
 * (Accessed: 18 September 2026).
 *
 * Oracle (2024). Classes and Objects. Available at:
 * https://docs.oracle.com/javase/tutorial/java/javaOO/
 * (Accessed: 18 September 2026).
 *
 * Oracle (2024). Scanner Class. Available at:
 * https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Scanner.html
 * (Accessed: 18 September 2026).
 *
 * Oracle (2024). String Class. Available at:
 * https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html
 * (Accessed: 18 September 2026).
 *
 * Oracle (2024). System Class. Available at:
 * https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/System.html
 * (Accessed: 18 September 2026).
 *
 * Oracle (2024). Pattern Class. Available at:
 * https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/regex/Pattern.html
 * (Accessed: 18 September 2026).
 */