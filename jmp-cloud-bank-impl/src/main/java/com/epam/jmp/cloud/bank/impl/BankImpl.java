package com.epam.jmp.cloud.bank.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.User;
import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.DebitBankCard;
import com.epam.jmp.bank.service.Bank;
import com.epam.jmp.cloud.bank.utils.CardNumberGenerator;

public class BankImpl implements Bank {
    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        var cardNumber = CardNumberGenerator.generateCardNumber(CardNumberGenerator.VISA_CARD_LENGTH);
        return switch (cardType) {
            case DEBIT -> createDebitBankCard(cardNumber, user, 10_000);
            case CREDIT -> createCreditBankCard(cardNumber, user, 10_000);
        };
    }

    private BankCard createDebitBankCard(String cardNumber, User user, double balance) {
        return new DebitBankCard(cardNumber, user, balance);
    }

    private BankCard createCreditBankCard(String cardNumber, User user, double balance) {
        return new CreditBankCard(cardNumber, user, balance);
    }
}
