package com.eteration.simplebanking.model;

public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException() {
        super("Yetersiz bakiye.");
    }

    public InsufficientBalanceException(double balance, double amount) {
        super(String.format("Yetersiz bakiye. Mevcut bakiye: %.2f, Çekilmek istenen miktar: %.2f", balance, amount));
    }
}
