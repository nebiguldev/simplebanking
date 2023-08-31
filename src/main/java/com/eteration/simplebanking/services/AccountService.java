package com.eteration.simplebanking.services;


import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.dto.PhoneBillPaymentTransactionDTO;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public TransactionStatus credit(String accountNumber, double amount) throws InsufficientBalanceException {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account not found for number: " + accountNumber));
        account.post(new DepositTransaction(amount));
        accountRepository.save(account);
        return new TransactionStatus("OK", account.getAccountNumber(), "Credit operation successful");
    }

    public TransactionStatus debit(String accountNumber, double amount) throws InsufficientBalanceException {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account not found for number: " + accountNumber));
       if(account.getBalance()<amount){
           throw new InsufficientBalanceException(account.getBalance(),amount);
       }
        account.post(new WithdrawalTransaction(amount));
        accountRepository.save(account);
        return new TransactionStatus("OK", account.getAccountNumber(), "Debit operation successful");
    }

    public TransactionStatus phoneBillPayment(String accountNumber, PhoneBillPaymentTransactionDTO requestDTO) throws InsufficientBalanceException {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account not found for number: " + accountNumber));
        if(account.getBalance() < requestDTO.getAmount()){
            throw new InsufficientBalanceException(account.getBalance(), requestDTO.getAmount());
        }
        account.post(new PhoneBillPaymentTransaction(requestDTO.getOperator(), requestDTO.getPhoneNumber(), requestDTO.getAmount()));
        accountRepository.save(account);
        return new TransactionStatus("OK", account.getAccountNumber(), "Phone bill payment operation successful");
    }



    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new EntityNotFoundException("Account not found for number: " + accountNumber));
    }
}
