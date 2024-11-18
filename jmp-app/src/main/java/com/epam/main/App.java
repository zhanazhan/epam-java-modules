package com.epam.main;


import com.epam.jmp.cloud.bank.utils.CardNumberGenerator;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.exception.SubscriptionNotFoundException;
import com.epam.jmp.cloud.bank.impl.BankImpl;
import com.epam.jmp.cloud.service.impl.ServiceImpl;
import com.epam.jmp.service.Service;

import java.time.LocalDate;

public class App {

    private static final String VALID_CARD_NUMBER = "4123456789123456";
    private static final String INVALID_CARD_NUMBER = "9112345678901234";
    private static final String SEPARATOR = "***************************************************";

    public static void main(String[] args) {
        var service = new ServiceImpl();

        // Create members and subscriptions
        initDatabase(service);

        // Display all registered members
        System.out.println(SEPARATOR);
        System.out.println("Registered Members:");
        service.getAllUsers().forEach(System.out::println);

        // Search for an existing subscription
        System.out.println(SEPARATOR);
        System.out.println("Looking up subscription for card number: " + VALID_CARD_NUMBER);
        displaySubscriptionDetails(service, VALID_CARD_NUMBER);

        // Search for a non-existing subscription
        System.out.println();
        System.out.println("Looking up subscription for card number: " + INVALID_CARD_NUMBER);
        displaySubscriptionDetails(service, INVALID_CARD_NUMBER);

        // Create new cards for a member and display them
        System.out.println();
        var bankManager = new BankImpl();
        var newMember = new User("Emily", "Johnson", LocalDate.of(2010, 5, 22));
        var newCreditCard = bankManager.createBankCard(newMember, BankCardType.CREDIT);
        System.out.println("Issued new Credit Card:");
        System.out.println(newCreditCard);
        var newDebitCard = bankManager.createBankCard(newMember, BankCardType.DEBIT);
        System.out.println("Issued new Debit Card:");
        System.out.println(newDebitCard);

        // Display average age of all members
        System.out.println(SEPARATOR);
        System.out.println("Average Member Age: " + service.getAverageUsersAge());

        // Check if a new member is eligible
        System.out.println(SEPARATOR);
        System.out.println("Is the new member eligible?");
        System.out.println("Member: " + newMember);
        System.out.println("Subscribed: " + service.isUserSubscribed(newMember));

        // Display subscriptions created today
        System.out.println(SEPARATOR);
        System.out.println("Subscriptions created today:");
        service.getAllSubscriptionsByCondition(s -> s.getStartDate().equals(LocalDate.now()))
                .forEach(System.out::println);
    }

    private static void displaySubscriptionDetails(Service service, String cardNumber) {
        try {
            var subscription = service.getSubscriptionByBankCardNumber(cardNumber)
                    .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found for card: " + cardNumber));
            System.out.println(subscription);
        } catch (SubscriptionNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void initDatabase(Service service) {
        var member1 = new User("John", "Doe", LocalDate.of(1985, 8, 25));
        var member2 = new User("Jane", "Smith", LocalDate.of(1970, 4, 10));
        var member3 = new User("Robert", "Brown", LocalDate.of(1992, 12, 2));

        var card1 = new BankCard(VALID_CARD_NUMBER, member1);
        var card2 = new BankCard(CardNumberGenerator.generateCardNumber(CardNumberGenerator.VISA_CARD_LENGTH), member2);
        var card3 = new BankCard(CardNumberGenerator.generateCardNumber(CardNumberGenerator.VISA_CARD_LENGTH), member3);

        service.subscribe(card1);
        service.subscribe(card2);
        service.subscribe(card3);
    }
}
