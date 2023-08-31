package com.eteration.simplebanking.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Deposit")
public class DepositTransaction extends Transaction {

    public DepositTransaction() {
    }

    public DepositTransaction(double amount) {
        super(amount);
    }

    @Override
    public double execute() {
        return getAmount();
    }

}
