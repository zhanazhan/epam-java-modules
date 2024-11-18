package com.epam.jmp.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ServiceImpl implements Service {
    private final List<Subscription> subscriptions = new ArrayList<>();

    @Override
    public void subscribe(BankCard bankCard) {
        // Validate the bank card
        if (bankCard == null || bankCard.getNumber() == null || bankCard.getUser() == null) {
            throw new IllegalArgumentException("BankCard and associated User must not be null.");
        }

        User user = bankCard.getUser();

        // Check if the user is already subscribed
        if (isUserSubscribed(user)) {
            throw new IllegalStateException("User is already subscribed.");
        }

        subscriptions.add(new Subscription(bankCard.getNumber(), user));
    }

    /**
     * Checks if a user is already subscribed.
     *
     * @param user the user to check
     * @return true if the user is already subscribed, false otherwise
     */
    public boolean isUserSubscribed(User user) {
        return subscriptions.stream()
                .anyMatch(subscription -> subscription.getUser().equals(user));
    }

    public List<Subscription> getAllSubscriptions() {
        return List.copyOf(subscriptions);
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return subscriptions.stream()
                .filter(s -> cardNumber.equals(s.getBankcard()))
                .findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return getAllSubscriptions().stream().map(Subscription::getUser).toList();
    }
}
