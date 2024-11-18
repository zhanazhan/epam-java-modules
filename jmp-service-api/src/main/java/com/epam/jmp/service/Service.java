package com.epam.jmp.service;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

public interface Service {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();

    /**
     * Calculates the average age of users in the list.
     *
     * @param users the list of users
     * @return the average age as a double
     */
    default double getAverageUsersAge(List<User> users) {
        if (users == null || users.isEmpty()) {
            throw new IllegalArgumentException("User list cannot be null or empty.");
        }

        double totalAge = users.stream()
                .mapToDouble(user -> calculateAge(user.getBirthday()))
                .sum();

        return totalAge / users.size();
    }

    /**
     * Determines if the user is payable (18 years old or older).
     *
     * @param user the user to check
     * @return true if the user is 18 or older, false otherwise
     */
    default boolean isPayableUser(User user) {
        if (user == null || user.getBirthday() == null) {
            throw new IllegalArgumentException("User or birthday cannot be null.");
        }

        return calculateAge(user.getBirthday()) >= 18;
    }

    /**
     * Calculates the age based on the birthday.
     *
     * @param birthday the birthday of the user
     * @return the calculated age
     */
    private int calculateAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }
}
