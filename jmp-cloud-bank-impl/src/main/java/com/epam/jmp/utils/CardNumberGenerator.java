package com.epam.jmp.utils;

import java.util.Random;

public class CardNumberGenerator {
    public static int VISA_CARD_LENGTH = 16;
    public static int AMERICAN_CARD_LENGTH = 15;

    /**
     * Generates a valid card number using the Luhn algorithm.
     *
     * @param length the length of the card number (typically 16 for most cards)
     * @return a valid card number
     */
    public static String generateCardNumber(int length) {
        if (length < 13 || length > 19) {
            throw new IllegalArgumentException("Card number length must be between 13 and 19 digits.");
        }

        Random random = new Random();
        int[] digits = new int[length];

        // Generate all but the last digit randomly
        for (int i = 0; i < length - 1; i++) {
            digits[i] = random.nextInt(10);
        }

        // Calculate the Luhn checksum digit
        digits[length - 1] = calculateLuhnChecksum(digits);

        // Convert the digits array to a card number string
        StringBuilder cardNumber = new StringBuilder();
        for (int digit : digits) {
            cardNumber.append(digit);
        }

        return cardNumber.toString();
    }

    /**
     * Calculates the Luhn checksum for the given digits.
     *
     * @param digits the array of card number digits (excluding the last checksum digit)
     * @return the Luhn checksum digit
     */
    private static int calculateLuhnChecksum(int[] digits) {
        int sum = 0;

        // Start from the rightmost digit and process alternately
        for (int i = digits.length - 2; i >= 0; i--) {
            int digit = digits[i];

            // Double every second digit
            if ((digits.length - 2 - i) % 2 == 0) {
                digit *= 2;
                // If doubling results in a number > 9, subtract 9
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
        }

        // Calculate the checksum digit
        return (10 - (sum % 10)) % 10;
    }
}

