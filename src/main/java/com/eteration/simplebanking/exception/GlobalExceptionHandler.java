package com.eteration.simplebanking.exception;



import com.eteration.simplebanking.model.InsufficientBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
//TODO: Bu hata sınıfı genel olarak projede kullanılabilir.Genişletilebilir. Nebi GUL 31/08/2023
    //Şu an için sadece InsufficientBalanceException sınıfını tutuyor.
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
