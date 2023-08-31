package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.AccountDTO;
import com.eteration.simplebanking.dto.PhoneBillPaymentTransactionDTO;
import com.eteration.simplebanking.dto.TransactionRequestDTO;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/account/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody TransactionRequestDTO requestDTO) throws InsufficientBalanceException {
        double amount = requestDTO.getAmount();
        TransactionStatus transactionStatus = accountService.credit(accountNumber, amount);
        return ResponseEntity.ok(transactionStatus);
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody TransactionRequestDTO requestDTO) throws InsufficientBalanceException {
        double amount = requestDTO.getAmount();
        TransactionStatus transactionStatus = accountService.debit(accountNumber, amount);
        return ResponseEntity.ok(transactionStatus);
    }

    @PostMapping("/phoneBillPayment/{accountNumber}")
    public ResponseEntity<TransactionStatus> phoneBillPayment(@PathVariable String accountNumber, @RequestBody PhoneBillPaymentTransactionDTO requestDTO) throws InsufficientBalanceException {
        TransactionStatus transactionStatus = accountService.phoneBillPayment(accountNumber, requestDTO);
        return ResponseEntity.ok(transactionStatus);
    }


    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setOwner(account.getOwner());
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setBalance(account.getBalance());
        return ResponseEntity.ok(accountDTO);
    }
}