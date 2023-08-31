package com.eteration.simplebanking.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BillPayment")
public class PhoneBillPaymentTransaction extends Transaction {

    @Column(name = "operator")
    private String operator;

    @Column(name = "phone_number")
    private String phoneNumber;

    public PhoneBillPaymentTransaction() {
    }

    public PhoneBillPaymentTransaction(String operator, String phoneNumber, double amount) {
        super(amount);
        this.operator = operator;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public double execute() {
        return -getAmount();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

