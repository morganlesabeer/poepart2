/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.poeparttwo1;

import java.util.Scanner;

public class Poeparttwo1 {

    public static void main(String[] args) {

        Scanner scan = Message.scanner;

        Registration registration = new Registration();
        Login login = new Login();

        System.out.println("\n===== Register =====");

        System.out.println("Please enter Username:");
        String username = scan.nextLine();

        System.out.println("Enter Password:");
        String password = scan.nextLine();

        System.out.println("Enter Your Cellphone:");
        String cellphone = scan.nextLine();

        String registrationMessage =
                registration.registerUser(username, password, cellphone);

        System.out.println(registrationMessage);

        if (!registration.checkUserName(username)
                || !registration.checkPasswordComplexity(password)
                || !registration.checkCellPhoneNumber(cellphone)) {

            return;
        }

        System.out.println("\n===== Login =====");

        System.out.println("Please enter Username:");
        String loginUsername = scan.nextLine();

        System.out.println("Please enter Password:");
        String loginPassword = scan.nextLine();

        boolean loginStatus =
                login.loginUser(loginUsername, loginPassword, registration);

        System.out.println(login.returnLoginStatus(loginStatus));

        if (loginStatus) {

            System.out.println("Welcome to QuickChat.");

            int numberOfMessages;

            while (true) {

                System.out.println("How many messages do you want to enter?");
                String numberInput = scan.nextLine();

                try {
                    numberOfMessages = Integer.parseInt(numberInput);

                    if (numberOfMessages >= 0) {
                        break;
                    }

                    System.out.println("Please enter 0 or a positive number.");

                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid whole number.");
                }
            }

            int handled = 0;
            int created = 0;
            int option = 0;

            do {
                System.out.println("\nSelect any option below");
                System.out.println("1. Send Message");
                System.out.println("2. Show recently sent messages");
                System.out.println("3. Exit");
                System.out.println("Enter your choice:");

                String optionInput = scan.nextLine();

                try {
                    option = Integer.parseInt(optionInput);
                } catch (NumberFormatException e) {
                    option = 0;
                }

                switch (option) {

                    case 1 -> {

                        if (handled >= numberOfMessages) {
                            System.out.println("You have handled all your messages.");
                            break;
                        }

                        System.out.println("\nMessage: " + (created + 1));

                        System.out.println("Enter recipient cell number:");
                        String recipient = scan.nextLine();

                        Message message = new Message(created, recipient, "x");

                        while (!message.checkRecipientCell().equals(
                                "Cell phone number successfully captured.")) {

                            System.out.println(message.checkRecipientCell());
                            System.out.println("Enter recipient cell number:");
                            recipient = scan.nextLine();

                            message = new Message(created, recipient, "x");
                        }

                        System.out.println(message.checkRecipientCell());

                        System.out.println("Enter your message:");
                        String messageText = scan.nextLine();

                        message = new Message(created, recipient, messageText);

                        while (!message.checkMessageLength().equals(
                                "Message ready to send.")) {

                            System.out.println(message.checkMessageLength());
                            System.out.println("Enter your message:");
                            messageText = scan.nextLine();

                            message = new Message(created, recipient, messageText);
                        }

                        System.out.println(message.checkMessageLength());

                        String result = message.SentMessage();

                        System.out.println(result);

                        if (result.equals("Message successfully sent.")) {

                            handled++;
                            System.out.println(message.getDetails());

                        } else if (result.equals("Message successfully stored.")) {

                            handled++;

                        } else if (result.equals("Disregarded")) {

                            handled++;

                        }

                        created++;

                        System.out.println(
                                "Total messages sent: "
                                + Message.returnTotalMessages());
                    }

                    case 2 -> {
                        Message message = new Message(0, "+27820000000", "View");
                        String output = message.printMessages();

                        if (output.isEmpty()) {
                            System.out.println("No sent messages to display.");
                        }
                    }

                    case 3 ->
                        System.out.println("Good Bye");

                    default ->
                        System.out.println("Invalid option.");
                }

            } while (option != 3);
        }
    }
}