package com.epam.jmp.dto;

import java.time.LocalDate;
import java.util.Objects;

public class Subscription {
    private String bankcard;
    private LocalDate startDate = LocalDate.now();
    private User user;

    public Subscription(String bankcard, User user) {
        this.bankcard = bankcard;
        this.user = user;
    }

    public String getBankcard() {
        return bankcard;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(bankcard, that.bankcard) && Objects.equals(user, that.user) && Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankcard, startDate, user);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "bankcard='" + bankcard + '\'' +
                "user='" + user + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
