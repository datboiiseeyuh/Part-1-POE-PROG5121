/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychat;

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

        message.setMessage("A".repeat(251));

        assertEquals(
                "Message exceeds 250 characters by 2; please reduce the size.",
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
    }
}