package com.eteration.simplebanking;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.dto.AccountDTO;
import com.eteration.simplebanking.dto.PhoneBillPaymentTransactionDTO;
import com.eteration.simplebanking.dto.TransactionRequestDTO;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.services.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ControllerTests {

    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenId_Credit_thenReturnJson() throws Exception {
        Account account = new Account("Kerem Karaca", "17892");
        when(service.credit(anyString(), anyDouble())).thenReturn(new TransactionStatus("OK", "17892", "Credit operation successful"));

        ResponseEntity<TransactionStatus> result = controller.credit("17892", new TransactionRequestDTO(1000.0));
        verify(service, times(1)).credit("17892", 1000.0);
        assertEquals("OK", result.getBody().getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson() throws Exception {
        Account account = new Account("Kerem Karaca", "17892");
        when(service.credit(anyString(), anyDouble())).thenReturn(new TransactionStatus("OK", "17892", "Credit operation successful"));
        when(service.debit(anyString(), anyDouble())).thenReturn(new TransactionStatus("OK", "17892", "Debit operation successful"));

        ResponseEntity<TransactionStatus> result = controller.credit("17892", new TransactionRequestDTO(1000.0));
        ResponseEntity<TransactionStatus> result2 = controller.debit("17892", new TransactionRequestDTO(50.0));
        verify(service, times(1)).credit("17892", 1000.0);
        verify(service, times(1)).debit("17892", 50.0);
        assertEquals("OK", result.getBody().getStatus());
        assertEquals("OK", result2.getBody().getStatus());
    }


    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson() throws Exception {
        Account account = new Account("Nebi GUL", "669-7788");
        when(service.credit(anyString(), anyDouble())).thenReturn(new TransactionStatus("OK", "17892", "Credit operation successful"));
        when(service.debit(anyString(), anyDouble())).thenThrow(new InsufficientBalanceException(1000.0, 5000.0));

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            ResponseEntity<TransactionStatus> result = controller.credit("669-7788", new TransactionRequestDTO(1000.0));
            ResponseEntity<TransactionStatus> result2 = controller.debit("669-7788", new TransactionRequestDTO(5000.0));
        });
    }

    @Test
    public void givenId_PhoneBillPayment_thenReturnJson() throws Exception {
        Account account = new Account("Kerem Karaca", "17892");
        when(service.phoneBillPayment(anyString(), any(PhoneBillPaymentTransactionDTO.class)))
                .thenReturn(new TransactionStatus("OK", "17892", "Phone bill payment operation successful"));

        PhoneBillPaymentTransactionDTO requestDTO = new PhoneBillPaymentTransactionDTO(50.0, "Vodafone", "5423345566");
        ResponseEntity<TransactionStatus> result = controller.phoneBillPayment("17892", requestDTO);
        verify(service, times(1)).phoneBillPayment("17892", requestDTO);
        assertEquals("OK", result.getBody().getStatus());
    }


    @Test
    public void givenId_GetAccount_thenReturnJson() {
        Account account = new Account("Kerem Karaca", "17892");
        when(service.getAccount(anyString())).thenReturn(account);

        ResponseEntity<AccountDTO> result = controller.getAccount("17892");
        verify(service, times(1)).getAccount("17892");
        assertEquals(account.getOwner(), result.getBody().getOwner());
        assertEquals(account.getAccountNumber(), result.getBody().getAccountNumber());
        assertEquals(account.getBalance(), result.getBody().getBalance());
    }

}
