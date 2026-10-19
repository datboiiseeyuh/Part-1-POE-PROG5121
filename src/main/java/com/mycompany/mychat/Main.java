package com.mycompany.mychat;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

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

        login.setName(firstName, lastName);

        String registrationMessage = login.registerUser(
                username, password, cellPhoneNumber
        );

        System.out.println(registrationMessage);

        if (!registrationMessage.equals("User registered successfully.")) {
            input.close();
            return;
        }

        System.out.println("\n===== Login =====");

        System.out.print("Enter your username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter your password: ");
        String loginPassword = input.nextLine();

        login.loginUser(loginUsername, loginPassword);

        String loginMessage = login.returnLoginStatus();
        System.out.println(loginMessage);

        if (!loginMessage.startsWith("Welcome ")) {
            input.close();
            return;
        }

        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages would you like to enter? ");

        int messageLimit;

        try {
            messageLimit = Integer.parseInt(input.nextLine());

            if (messageLimit < 0) {
                System.out.println("Please enter a valid message limit.");
                input.close();
                return;
            }

        } catch (NumberFormatException exception) {
            System.out.println("Please enter a valid number.");
            input.close();
            return;
        }

        int messagesCreated = 0;
        int messageNumber = 0;

        while (true) {

            System.out.println("\n===== QuickChat Menu =====");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");

            String choice = input.nextLine();

            switch (choice) {

                case "1":

                    if (messagesCreated >= messageLimit) {
                        System.out.println("You have reached your message limit.");
                        break;
                    }

                    System.out.print("Enter recipient number (+27 followed by 9 digits): ");
                    String recipient = input.nextLine();

                    Message message = new Message();
                    message.setRecipient(recipient);

                    String recipientStatus = message.checkRecipientCell();

                    if (!recipientStatus.equals(
                            "Cell phone number successfully captured.")) {
                        System.out.println(recipientStatus);
                        break;
                    }

                    System.out.print("Enter your message: ");
                    String messageText = input.nextLine();

                    message.setMessage(messageText);

                    String lengthStatus = message.checkMessageLength();

                    if (!lengthStatus.equals("Message ready to send.")) {
                        System.out.println(lengthStatus);
                        break;
                    }

                    messagesCreated++;
                    messageNumber++;

                    message.setMessageNumber(messageNumber);
                    message.createMessageHash();

                    System.out.println("\nMessage ID: "
                            + message.getMessageID());

                    System.out.println("Message Hash: "
                            + message.getMessageHash());

                    System.out.println("Recipient: "
                            + message.getRecipient());

                    System.out.println("Message: "
                            + message.getMessage());

                    System.out.println(message.SentMessage(input));

                    break;

                case "2":

                    System.out.println("Coming Soon.");
                    break;

                case "3":

                    System.out.println("Total messages sent: "
                            + new Message().returnTotalMessagess());

                    System.out.println("Thank you for using QuickChat!");
                    input.close();
                    return;

                default:

                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
                    break;
            }
        }
    }
}