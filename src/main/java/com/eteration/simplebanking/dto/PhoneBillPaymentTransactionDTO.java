package com.eteration.simplebanking.dto;

public class PhoneBillPaymentTransactionDTO {
    private double amount;
    private String operator;
    private String phoneNumber;

    public PhoneBillPaymentTransactionDTO(double amount, String operator, String phoneNumber) {
        this.amount = amount;
        this.operator = operator;
        this.phoneNumber = phoneNumber;
    }

    public PhoneBillPaymentTransactionDTO() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
