/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a message sent through the MyChat application.
 * Stores message information and supports message processing.
 */

public class Message {

    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private int messageNumber;

    private static int totalMessagesSent = 0;

    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();

    private static final Scanner input = new Scanner(System.in);

    // Creates a new message with a randomly generated ten-digit ID.
    public Message() {
        Random random = new Random();
        long id = 1000000000L
                + (long) (random.nextDouble() * 9000000000L);

        messageID = String.valueOf(id);
    }

    // Allows message details to be supplied when creating a message.
    public Message(String recipient, String message, int messageNumber) {
        this();

        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;

        this.messageHash = createMessageHash();
    }

    // Checks that the message ID contains exactly ten digits.
    public boolean checkMessageID() {
        return messageID != null
                && messageID.matches("[0-9]{10}");
    }

    // Validates a South African international-format phone number.
    public String checkRecipientCell() {

        if (recipient != null
                && recipient.matches("\\+27[0-9]{9}")) {

            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does "
                + "not contain an international code. Please correct "
                + "the number and try again.";
    }

    // Checks whether the message is shorter than 250 characters.
    public String checkMessageLength() {

        if (message != null && message.length() < 250) {
            return "Message ready to send.";
        }

        int excess = message == null ? 0 : message.length() - 249;

        return "Message exceeds 250 characters by " + excess
                + "; please reduce the size.";
    }

    // Creates the message hash using the ID, message number,
    // first word and last word.
    public String createMessageHash() {

        if (messageID == null || message == null) {
            return "";
        }

        String[] words = message.trim().split("\\s+");

        if (words.length == 0 || message.trim().isEmpty()) {
            return messageID.substring(0, 2) + ":"
                    + messageNumber + ":";
        }

        String firstWord = words[0].replaceAll(
                "^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");

        String lastWord = words[words.length - 1].replaceAll(
                "^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");

        messageHash = (messageID.substring(0, 2)
                + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();

        return messageHash;
    }

    // Allows a user to send, disregard or store a message.
   public String SentMessage() {
    return SentMessage(input);
}



public String SentMessage(Scanner scanner) {
    System.out.println("\nChoose what to do with this message:");
    System.out.println("1. Send Message");
    System.out.println("2. Disregard Message");
    System.out.println("3. Store Message to send later");
    System.out.print("Enter your choice: ");

    String choice = scanner.nextLine();

    switch (choice) {
        case "1":
            sentMessages.add(this);
            totalMessagesSent++;
            return "Message successfully sent.";

        case "2":
            return "Press 0 to delete the message.";

        case "3":
            if (storeMessage()) {
                return "Message successfully stored.";
            }
            return "Message could not be stored.";

        default:
            return "Invalid choice. Please select 1, 2, or 3.";
    }
}

    // Displays details of messages successfully sent in this run.
    public String printMessages() {

        StringBuilder result = new StringBuilder();

        for (Message sent : sentMessages) {

            result.append("Message ID: ")
                    .append(sent.messageID)
                    .append("\nMessage Hash: ")
                    .append(sent.messageHash)
                    .append("\nRecipient: ")
                    .append(sent.recipient)
                    .append("\nMessage: ")
                    .append(sent.message)
                    .append("\n\n");
        }

        return result.toString();
    }

    // Returns the number of messages successfully sent.
    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // Stores messages in a valid JSON array in messages.json.
    public boolean storeMessage() {

        storedMessages.add(this);

        try (FileWriter writer = new FileWriter("messages.json")) {

            writer.write("[\n");

            for (int i = 0; i < storedMessages.size(); i++) {

                Message current = storedMessages.get(i);

                writer.write("  {\n");
                writer.write("    \"messageID\": \""
                        + escapeJSON(current.messageID) + "\",\n");
                writer.write("    \"messageHash\": \""
                        + escapeJSON(current.messageHash) + "\",\n");
                writer.write("    \"recipient\": \""
                        + escapeJSON(current.recipient) + "\",\n");
                writer.write("    \"message\": \""
                        + escapeJSON(current.message) + "\",\n");
                writer.write("    \"messageNumber\": "
                        + current.messageNumber + "\n");
                writer.write("  }");

                if (i < storedMessages.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]\n");
            return true;

        } catch (IOException exception) {

            System.out.println("Error storing messages: "
                    + exception.getMessage());

            storedMessages.remove(this);
            return false;
        }
    }

    // Escapes special characters so that the JSON remains valid.
    private String escapeJSON(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Getters and setters for testing and accessing message details.
    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }
}

