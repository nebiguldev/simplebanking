package com.eteration.simplebanking.controller;


import java.util.UUID;

public class TransactionStatus {
    private String status;
    private String approvalCode;
    private String message;

    public TransactionStatus(String status, String approvalCode, String message) {
        this.status = status;
        this.approvalCode = UUID.randomUUID().toString();
        this.message = message;
    }

    public TransactionStatus() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
