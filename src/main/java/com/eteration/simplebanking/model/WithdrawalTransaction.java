package com.eteration.simplebanking.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Withdrawal")
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction() {
    }

    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    @Override
    public double execute() {
        return -getAmount();
    }
}

