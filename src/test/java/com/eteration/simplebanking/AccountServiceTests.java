package com.eteration.simplebanking;

import com.eteration.simplebanking.dto.PhoneBillPaymentTransactionDTO;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AccountServiceTests {

    @InjectMocks
    private AccountService service;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCredit() throws Exception {
        Account mockAccount = new Account("Jim", "12345");

        when(accountRepository.findByAccountNumber("12345")).thenReturn(Optional.of(mockAccount));

        service.credit("12345", 1000);

        assertEquals(1000, mockAccount.getBalance(), 0.0001);
    }

    @Test
    public void testPhoneBillPayment() throws Exception {
        Account account = new Account("Kerem Karaca", "17892");
        account.post(new DepositTransaction(1000));  // hesaba 1000 birim para eklemem gerekiyor. NG 31/08/2023
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        PhoneBillPaymentTransactionDTO requestDTO = new PhoneBillPaymentTransactionDTO(50.0, "Vodafone", "5423345566");
        service.phoneBillPayment("17892", requestDTO);

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testDebit() throws Exception {
        Account account = new Account("Kerem Karaca", "17892");
        account.post(new DepositTransaction(1000));
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        service.debit("17892", 500.0);

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testGetAccount() {
        Account account = new Account("Kerem Karaca", "17892");
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        service.getAccount("17892");

        verify(accountRepository, times(1)).findByAccountNumber("17892");
    }

    @Test
    public void testGetAccountThrowsEntityNotFoundException() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getAccount("12345"));
    }
}
