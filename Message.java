/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poeparttwo1;

/**
 *
 * @author Student
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Student
 */
public class Message {

    public static final Scanner scanner = new Scanner(System.in);

    static ArrayList<Message> sentMessages = new ArrayList<>();
    static ArrayList<Message> storedMessages = new ArrayList<>();

    String messageID;
    int numMessagesSent;
    String recipient;
    String message;
    String messageHash;

    static int totalSent = 0;

    public Message() {
    }

    public Message(int numMessagesSent, String recipient, String message) {

        this.messageID = generateMessageID();
        this.numMessagesSent = numMessagesSent;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    public Message(String messageID, int numMessagesSent,
            String recipient, String message) {

        this.messageID = messageID;
        this.numMessagesSent = numMessagesSent;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    public String generateMessageID() {

        long number = ThreadLocalRandom.current().nextLong(10000000000L);

        return String.format("%010d", number);
    }

    public boolean checkMessageID() {

        return messageID != null
                && messageID.matches("[0-9]{1,10}");
    }

    public String checkRecipientCell() {

        if (recipient != null
                && recipient.matches("^\\+\\d{1,3}\\d{1,10}$")) {

            return "Cell phone number successfully captured.";

        } else {

            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String checkMessageLength() {

        if (message != null && !message.trim().isEmpty()
                && message.length() <= 250) {

            return "Message ready to send.";

        } else if (message != null && message.length() > 250) {

            int over = message.length() - 250;

            return "Message exceeds 250 characters by " + over
                    + ", please reduce the size.";

        } else {

            return "Message cannot be empty.";
        }
    }

    public String createMessageHash() {

        String firstTwoNumbers = "";

        if (messageID != null && messageID.length() >= 2) {
            firstTwoNumbers = messageID.substring(0, 2);
        }

        String trimmedMessage = "";

        if (message != null) {
            trimmedMessage = message.trim();
        }

        String[] words = trimmedMessage.split("\\s+");

        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");

        String hash = firstTwoNumbers + ":"
                + numMessagesSent + ":"
                + firstWord + lastWord;

        return hash.toUpperCase();
    }

    public String SentMessage() {

        System.out.println("\nChoose what to do with this message");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        while (true) {

            System.out.println("Enter your choice:");
            String option = scanner.nextLine().trim();

            if (option.equals("1") || option.equals("2")
                    || option.equals("3")) {

                return SentMessage(Integer.parseInt(option));

            } else {

                System.out.println("Please enter 1, 2 or 3.");
            }
        }
    }

    public String SentMessage(int choice) {

        switch (choice) {

            case 1 -> {
                sentMessages.add(this);
                totalSent++;

                return "Message successfully sent.";
            }

            case 2 -> {
                return "Disregarded";
            }

            case 3 -> {
                storeMessage();

                return "Message successfully stored.";
            }

            default -> {
                return "Invalid option.";
            }
        }
    }

    public String printMessages() {

        StringBuilder allMessages = new StringBuilder();

        System.out.println("\n====== Sent Messages ======");

        for (Message msg : sentMessages) {

            allMessages.append(msg.getDetails()).append("\n");

            System.out.println(msg.getDetails());
        }

        return allMessages.toString();
    }

    public static int returnTotalMessages() {

        return totalSent;
    }

    public String getDetails() {

        return "Message ID: " + messageID + System.lineSeparator()
                + "Message Hash: " + messageHash + System.lineSeparator()
                + "Recipient: " + recipient + System.lineSeparator()
                + "Message: " + message + System.lineSeparator();
    }

    public void storeMessage() {

        if (!storedMessages.contains(this)) {
            storedMessages.add(this);
        }

        try (FileWriter writer = new FileWriter("storedMessages.json")) {

            writer.write("[\n");

            for (int i = 0; i < storedMessages.size(); i++) {

                Message msg = storedMessages.get(i);

                writer.write("  {\n");
                writer.write("    \"messageID\": \"" + escapeJSON(msg.messageID) + "\",\n");
                writer.write("    \"messageHash\": \"" + escapeJSON(msg.messageHash) + "\",\n");
                writer.write("    \"recipient\": \"" + escapeJSON(msg.recipient) + "\",\n");
                writer.write("    \"message\": \"" + escapeJSON(msg.message) + "\",\n");
                writer.write("    \"messageNumber\": " + msg.numMessagesSent + "\n");
                writer.write("  }");

                if (i < storedMessages.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]");

        } catch (IOException e) {

            System.out.println("Could not store message: " + e.getMessage());
        }
    }

    public String escapeJSON(String text) {

        if (text == null) {
            return "";
        }

        String escaped = text.replace("\\", "\\\\");
        escaped = escaped.replace("\"", "\\\"");
        escaped = escaped.replace("\b", "\\b");
        escaped = escaped.replace("\f", "\\f");
        escaped = escaped.replace("\n", "\\n");
        escaped = escaped.replace("\r", "\\r");
        escaped = escaped.replace("\t", "\\t");

        return escaped;
    }

    public static void clearAll() {

        sentMessages.clear();
        storedMessages.clear();
        totalSent = 0;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public int getNumMessagesSent() {
        return numMessagesSent;
    }
}