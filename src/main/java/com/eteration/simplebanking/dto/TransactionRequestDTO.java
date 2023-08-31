package com.eteration.simplebanking.dto;

public class TransactionRequestDTO {
    private double amount;

    public TransactionRequestDTO(double amount) {
        this.amount = amount;
    }

    public TransactionRequestDTO() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
