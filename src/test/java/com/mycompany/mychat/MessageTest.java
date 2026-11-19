/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */





package com.mycompany.mychat;

import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageID() {
        Message message = new Message();

        assertTrue(message.checkMessageID());

        message.setMessageID("12345");
        assertFalse(message.checkMessageID());
    }

    @Test
    public void testRecipientCell() {
        Message message = new Message(
                "+27718693002", "Hi Mike", 1);

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell());

        message.setRecipient("08575975889");

        assertTrue(message.checkRecipientCell().contains(
                "incorrectly formatted"));
    }

    @Test
    public void testMessageLength() {

        Message message = new Message(
                "+27718693002", "Hi Mike", 1);

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength());

        message.setMessage("A".repeat(250));

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength());

        message.setMessage("A".repeat(251));

        assertEquals(
                "Message exceeds 250 characters by 1; please reduce the size.",
                message.checkMessageLength());
    }

    @Test
    public void testMessageHash() {
        Message message = new Message();

        message.setMessageID("0012345678");
        message.setMessageNumber(0);
        message.setMessage(
                "Hi Mike, can you join us for dinner tonight?");

        assertEquals(
                "00:0:HITONIGHT",
                message.createMessageHash());
    }

    @Test
    public void testSendMessage() {
        Message message = new Message(
                "+27718693002", "Testing send", 1);

        int previousTotal = message.returnTotalMessagess();

        String result = message.SentMessage(
                new Scanner("1\n"));

        assertEquals(
                "Message successfully sent.", result);

        assertEquals(
                previousTotal + 1,
                message.returnTotalMessagess());
    }

    @Test
    public void testDisregardMessage() {
        Message message = new Message(
                "+27718693002", "Testing discard", 2);

        int previousTotal = message.returnTotalMessagess();

        String result = message.SentMessage(
                new Scanner("2\n"));

        assertEquals(
                "Press 0 to delete the message.", result);

        assertEquals(
                previousTotal,
                message.returnTotalMessagess());
    }

    @Test
    public void testStoreMessage() {
        Message message = new Message(
                "+27718693002", "Testing storage", 3);

        assertEquals(
                "Message successfully stored.",
                message.SentMessage(new Scanner("3\n")));

        message.deleteMessageByHash(message.getMessageHash());
    }

    // PART 3 ARRAY TESTS

    @Test
    public void testSentMessagesArray() {

        Message message1 = new Message(
                "+27834557896",
                "Did you get the cake?",
                1);

        Message message4 = new Message(
                "0838884567",
                "It is dinner time !",
                4);

        message1.SentMessage(new Scanner("1\n"));
        message4.SentMessage(new Scanner("1\n"));

        Message[] sentMessages = Message.getSentMessagesArray();

        assertTrue(Arrays.stream(sentMessages)
                .anyMatch(m -> m.getMessage().equals(
                        "Did you get the cake?")));

        assertTrue(Arrays.stream(sentMessages)
                .anyMatch(m -> m.getMessage().equals(
                        "It is dinner time !")));
    }

    @Test
    public void testDisregardedMessagesArray() {

        Message message3 = new Message(
                "+27834484567",
                "Yohoooo, I am at your gate.",
                3);

        message3.SentMessage(new Scanner("2\n"));

        assertTrue(Arrays.asList(
                Message.getDisregardedMessagesArray()).stream()
                .anyMatch(m -> m.getMessage().equals(
                        "Yohoooo, I am at your gate.")));
    }

    @Test
    public void testStoredMessagesArray() {

        Message message2 = new Message(
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                2);

        Message message5 = new Message(
                "+27838884567",
                "Ok, I am leaving without you.",
                5);

        assertTrue(message2.storeMessage());
        assertTrue(message5.storeMessage());

        Message[] storedMessages = Message.getStoredMessagesArray();

        assertTrue(Arrays.stream(storedMessages)
                .anyMatch(m -> m.getMessage().equals(
                        "Where are you? You are late! I have asked you to be on time.")));

        assertTrue(Arrays.stream(storedMessages)
                .anyMatch(m -> m.getMessage().equals(
                        "Ok, I am leaving without you.")));

        message2.deleteMessageByHash(message2.getMessageHash());
        message5.deleteMessageByHash(message5.getMessageHash());
    }

    @Test
    public void testMessageHashesArray() {

        Message message1 = new Message(
                "+27834557896",
                "Did you get the cake?",
                1);

        message1.SentMessage(new Scanner("1\n"));

        assertTrue(Arrays.asList(
                Message.getMessageHashesArray()).contains(
                        message1.getMessageHash()));
    }

    @Test
    public void testMessageIDsArray() {

        Message message4 = new Message(
                "0838884567",
                "It is dinner time !",
                4);

        message4.SentMessage(new Scanner("1\n"));

        assertTrue(Arrays.asList(
                Message.getMessageIDsArray()).contains(
                        message4.getMessageID()));
    }

    // PART 3 STORED MESSAGE FEATURE TESTS

    @Test
    public void testDisplayLongestStoredMessage() {

        Message message2 = new Message(
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                2);

        Message message5 = new Message(
                "+27838884567",
                "Ok, I am leaving without you.",
                5);

        assertTrue(message2.storeMessage());
        assertTrue(message5.storeMessage());

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                message2.displayLongestStoredMessage());

        message2.deleteMessageByHash(message2.getMessageHash());
        message5.deleteMessageByHash(message5.getMessageHash());
    }

    @Test
    public void testSearchMessageByID() {

        Message message4 = new Message(
                "0838884567",
                "It is dinner time !",
                4);

        message4.SentMessage(new Scanner("1\n"));

        String result = message4.searchMessageByID(
                message4.getMessageID());

        assertTrue(result.contains("0838884567"));
        assertTrue(result.contains("It is dinner time !"));
    }

    @Test
    public void testSearchMessagesByRecipient() {

        Message message2 = new Message(
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                2);

        Message message5 = new Message(
                "+27838884567",
                "Ok, I am leaving without you.",
                5);

        Message sentMessage = new Message(
                "+27838884567",
                "This is a sent message for testing.",
                6);

        assertTrue(message2.storeMessage());
        assertTrue(message5.storeMessage());

        sentMessage.SentMessage(new Scanner("1\n"));

        String result = message2.searchMessagesByRecipient(
                "+27838884567");

        assertTrue(result.contains(
                "Where are you? You are late! I have asked you to be on time."));

        assertTrue(result.contains(
                "Ok, I am leaving without you."));

        assertTrue(result.contains(
                "This is a sent message for testing."));

        message2.deleteMessageByHash(message2.getMessageHash());
        message5.deleteMessageByHash(message5.getMessageHash());
    }

    @Test
    public void testDeleteMessageByHash() {

        Message message2 = new Message(
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                2);

        assertTrue(message2.storeMessage());

        String result = message2.deleteMessageByHash(
                message2.getMessageHash());

        assertEquals(
                "Where are you? You are late! I have asked you to be on time. successfully deleted.",
                result);

        assertFalse(Arrays.asList(
                Message.getStoredMessagesArray()).contains(message2));
    }

    @Test
    public void testDisplayStoredMessagesReport() {

        Message message2 = new Message(
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                2);

        Message message5 = new Message(
                "+27838884567",
                "Ok, I am leaving without you.",
                5);

        Message sentMessage = new Message(
                "+27834557896",
                "This sent message must appear in the report.",
                7);

        assertTrue(message2.storeMessage());
        assertTrue(message5.storeMessage());

        sentMessage.SentMessage(new Scanner("1\n"));

        String report = message2.displayStoredMessagesReport();

        // Check stored messages.
        assertTrue(report.contains(message2.getMessageID()));
        assertTrue(report.contains(message2.getMessageHash()));
        assertTrue(report.contains("+27838884567"));

        assertTrue(report.contains(
                "Where are you? You are late! I have asked you to be on time."));

        assertTrue(report.contains(message5.getMessageID()));
        assertTrue(report.contains(message5.getMessageHash()));

        assertTrue(report.contains(
                "Ok, I am leaving without you."));

        // Check that sent messages are also included.
        assertTrue(report.contains(sentMessage.getMessageID()));
        assertTrue(report.contains(sentMessage.getMessageHash()));

        assertTrue(report.contains("+27834557896"));

        assertTrue(report.contains(
                "This sent message must appear in the report."));

        message2.deleteMessageByHash(message2.getMessageHash());
        message5.deleteMessageByHash(message5.getMessageHash());
    }
}


