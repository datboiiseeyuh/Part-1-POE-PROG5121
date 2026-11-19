/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */







package com.mycompany.mychat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Message {

    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private int messageNumber;

    private static int totalMessagesSent = 0;

    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> disregardedMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();

    private static Message[] sentMessagesArray = new Message[0];
    private static Message[] disregardedMessagesArray = new Message[0];
    private static Message[] storedMessagesArray = new Message[0];

    private static String[] messageHashesArray = new String[0];
    private static String[] messageIDsArray = new String[0];

    private static final Scanner input = new Scanner(System.in);

    static {
        loadStoredMessages();
    }

    public Message() {
        Random random = new Random();

        long id = 1000000000L
                + (long) (random.nextDouble() * 9000000000L);

        messageID = String.valueOf(id);
    }

    public Message(String recipient, String message, int messageNumber) {
        this();

        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;

        this.messageHash = createMessageHash();
    }

    public boolean checkMessageID() {
        return messageID != null
                && messageID.matches("[0-9]{10}");
    }

    // Accepts both international and South African local formats.
    public String checkRecipientCell() {

        if (recipient != null
                && (recipient.matches("\\+27[0-9]{9}")
                || recipient.matches("0[0-9]{9}"))) {

            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does "
                + "not contain an international code. Please correct "
                + "the number and try again.";
    }

    // Checks that a message is not empty and does not exceed 250 characters.
    public String checkMessageLength() {

        if (message == null || message.trim().isEmpty()) {
            return "Message cannot be empty. Please enter a message.";
        }

        if (message.length() <= 250) {
            return "Message ready to send.";
        }

        int excess = message.length() - 250;

        return "Message exceeds 250 characters by " + excess
                + "; please reduce the size.";
    }

    public String createMessageHash() {

        if (messageID == null || messageID.length() < 2
                || message == null) {
            return "";
        }

        String[] words = message.trim().split("\\s+");

        if (message.trim().isEmpty()) {
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
                updateMessageArrays();

                return "Message successfully sent.";

            case "2":
                disregardedMessages.add(this);
                updateMessageArrays();

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

    public String printMessages() {

        StringBuilder result = new StringBuilder();

        for (Message sent : sentMessages) {

            result.append("Message Number: ")
                    .append(sent.messageNumber)
                    .append("\nMessage ID: ")
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

    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // Loads previously stored messages from messages.json.
    public static void loadStoredMessages() {

        File file = new File("messages.json");

        if (!file.exists()) {
            updateMessageArrays();
            return;
        }

        List<Message> loadedMessages = new ArrayList<>();

        try (FileReader reader = new FileReader(file)) {

            JsonElement element = JsonParser.parseReader(reader);

            if (!element.isJsonArray()) {
                System.out.println(
                        "The messages.json file is not a valid JSON array.");
                return;
            }

            JsonArray jsonArray = element.getAsJsonArray();

            for (JsonElement item : jsonArray) {

                if (!item.isJsonObject()) {
                    continue;
                }

                JsonObject object = item.getAsJsonObject();

                Message loadedMessage = new Message();

                loadedMessage.messageID =
                        object.has("messageID")
                        ? object.get("messageID").getAsString() : "";

                loadedMessage.messageHash =
                        object.has("messageHash")
                        ? object.get("messageHash").getAsString() : "";

                loadedMessage.recipient =
                        object.has("recipient")
                        ? object.get("recipient").getAsString() : "";

                loadedMessage.message =
                        object.has("message")
                        ? object.get("message").getAsString() : "";

                loadedMessage.messageNumber =
                        object.has("messageNumber")
                        ? object.get("messageNumber").getAsInt() : 0;

                if (loadedMessage.messageHash.isEmpty()) {
                    loadedMessage.messageHash =
                            loadedMessage.createMessageHash();
                }

                loadedMessages.add(loadedMessage);
            }

            storedMessages.clear();
            storedMessages.addAll(loadedMessages);

            updateMessageArrays();

            System.out.println("Stored messages successfully loaded: "
                    + storedMessages.size());

        } catch (IOException | JsonParseException exception) {

            System.out.println("Error reading stored messages: "
                    + exception.getMessage());
        }
    }

    // Stores messages in messages.json.
    public boolean storeMessage() {

        if (messageID == null || messageHash == null
                || recipient == null || message == null) {

            System.out.println("Cannot store an incomplete message.");
            return false;
        }

        storedMessages.add(this);
        updateMessageArrays();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter("messages.json")) {

            gson.toJson(storedMessages, writer);

            return true;

        } catch (IOException exception) {

            System.out.println("Error storing messages: "
                    + exception.getMessage());

            storedMessages.remove(this);
            updateMessageArrays();

            return false;
        }
    }

    // 1. Displays sender and recipient of all stored messages.
    public String displayStoredSendersAndRecipients(String senderName) {

        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }

        StringBuilder result = new StringBuilder();

        for (Message stored : storedMessages) {

            result.append("Sender: ")
                    .append(senderName)
                    .append("\nRecipient: ")
                    .append(stored.recipient)
                    .append("\n\n");
        }

        return result.toString();
    }

    // 2. Displays the longest stored message.
    public String displayLongestStoredMessage() {

        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }

        Message longestMessage = storedMessages.get(0);

        for (Message stored : storedMessages) {

            if (stored.message.length()
                    > longestMessage.message.length()) {

                longestMessage = stored;
            }
        }

        return longestMessage.message;
    }

    // 3. Searches for a message using its message ID.
    // Searches both sent and stored messages.
    public String searchMessageByID(String searchID) {

        for (Message sent : sentMessages) {

            if (searchID != null
                    && sent.messageID.equals(searchID)) {

                return "Recipient: " + sent.recipient
                        + "\nMessage: " + sent.message;
            }
        }

        for (Message stored : storedMessages) {

            if (searchID != null
                    && stored.messageID.equals(searchID)) {

                return "Recipient: " + stored.recipient
                        + "\nMessage: " + stored.message;
            }
        }

        return "Message ID not found.";
    }

    // 4. Searches for all sent and stored messages belonging to a recipient.
    public String searchMessagesByRecipient(String searchRecipient) {

        StringBuilder result = new StringBuilder();

        // Search messages that have been sent.
        for (Message sent : sentMessages) {

            if (searchRecipient != null
                    && searchRecipient.equals(sent.recipient)) {

                result.append("Message ID: ")
                        .append(sent.messageID)
                        .append("\nMessage: ")
                        .append(sent.message)
                        .append("\n\n");
            }
        }

        // Search messages that have been stored.
        for (Message stored : storedMessages) {

            if (searchRecipient != null
                    && searchRecipient.equals(stored.recipient)) {

                result.append("Message ID: ")
                        .append(stored.messageID)
                        .append("\nMessage: ")
                        .append(stored.message)
                        .append("\n\n");
            }
        }

        if (result.length() == 0) {
            return "No messages found for this recipient.";
        }

        return result.toString();
    }

    // 5. Deletes a stored message using its message hash.
    public String deleteMessageByHash(String searchHash) {

        Message messageToDelete = null;

        for (Message stored : storedMessages) {

            if (stored.messageHash.equals(searchHash)) {
                messageToDelete = stored;
                break;
            }
        }

        if (messageToDelete == null) {
            return "Message hash not found.";
        }

        storedMessages.remove(messageToDelete);
        updateMessageArrays();

        if (saveStoredMessages()) {
            return messageToDelete.message + " successfully deleted.";
        }

        storedMessages.add(messageToDelete);
        updateMessageArrays();

        return "Message could not be deleted because the file could not be updated.";
    }

    // 6. Displays a report containing all sent and stored message details.
    public String displayStoredMessagesReport() {

        if (sentMessages.isEmpty() && storedMessages.isEmpty()) {
            return "No messages found.";
        }

        StringBuilder report = new StringBuilder();

        // Include all sent messages in the report.
        for (Message sent : sentMessages) {

            report.append("Message ID: ")
                    .append(sent.messageID)
                    .append("\nMessage Hash: ")
                    .append(sent.messageHash)
                    .append("\nRecipient: ")
                    .append(sent.recipient)
                    .append("\nMessage: ")
                    .append(sent.message)
                    .append("\nMessage Number: ")
                    .append(sent.messageNumber)
                    .append("\n-----------------------------\n");
        }

        // Include all stored messages in the report.
        for (Message stored : storedMessages) {

            report.append("Message ID: ")
                    .append(stored.messageID)
                    .append("\nMessage Hash: ")
                    .append(stored.messageHash)
                    .append("\nRecipient: ")
                    .append(stored.recipient)
                    .append("\nMessage: ")
                    .append(stored.message)
                    .append("\nMessage Number: ")
                    .append(stored.messageNumber)
                    .append("\n-----------------------------\n");
        }

        return report.toString();
    }

    // Saves the current stored message list to JSON.
    private static boolean saveStoredMessages() {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter("messages.json")) {

            gson.toJson(storedMessages, writer);
            return true;

        } catch (IOException exception) {

            System.out.println("Error updating messages.json: "
                    + exception.getMessage());

            return false;
        }
    }

    // Updates all arrays using the existing message lists.
    private static void updateMessageArrays() {

        sentMessagesArray = sentMessages.toArray(new Message[0]);

        disregardedMessagesArray =
                disregardedMessages.toArray(new Message[0]);

        storedMessagesArray =
                storedMessages.toArray(new Message[0]);

        List<Message> allMessages = new ArrayList<>();

        allMessages.addAll(sentMessages);
        allMessages.addAll(disregardedMessages);
        allMessages.addAll(storedMessages);

        messageHashesArray = new String[allMessages.size()];
        messageIDsArray = new String[allMessages.size()];

        for (int i = 0; i < allMessages.size(); i++) {

            Message current = allMessages.get(i);

            messageHashesArray[i] = current.getMessageHash();
            messageIDsArray[i] = current.getMessageID();
        }
    }

    // Getters and setters.
    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
        this.messageHash = createMessageHash();
        updateMessageArrays();
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
        updateMessageArrays();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.messageHash = createMessageHash();
        updateMessageArrays();
    }

    public String getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
        updateMessageArrays();
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash();
        updateMessageArrays();
    }

    public static Message[] getSentMessagesArray() {
        return sentMessagesArray.clone();
    }

    public static Message[] getDisregardedMessagesArray() {
        return disregardedMessagesArray.clone();
    }

    public static Message[] getStoredMessagesArray() {
        return storedMessagesArray.clone();
    }

    public static String[] getMessageHashesArray() {
        return messageHashesArray.clone();
    }

    public static String[] getMessageIDsArray() {
        return messageIDsArray.clone();
    }
}








