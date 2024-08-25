package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.dto.TransactionRequest;
import com.deepaksharma.Library_Management_System.model.Transaction;
import com.deepaksharma.Library_Management_System.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    // issueBook_ValidRequest_ReturnsCreated
    @Test
    public void issueBook_ValidRequest_ReturnsCreated() {
        // Arrange
        TransactionRequest validRequest = new TransactionRequest();
        validRequest.setBookNo("1");
        validRequest.setUserEmail("user@gmail.com");

        Transaction transaction = new Transaction();
        Mockito.when(transactionService.issueBook(Mockito.any(TransactionRequest.class))).thenReturn(transaction);

        // Act
        ResponseEntity<?> response = transactionController.issueBook(validRequest);

        // Assert
        Assertions.assertEquals(transaction, response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // returnBook_ValidRequest_ReturnsOk
    @Test
    public void returnBook_ValidRequest_ReturnsOk() {
        // Arrange
        TransactionRequest validRequest = new TransactionRequest();
        validRequest.setBookNo("1");
        validRequest.setUserEmail("user@gmail.com");

        String responseMessage = "Book returned successfully";
        Mockito.when(transactionService.returnBook(Mockito.any(TransactionRequest.class))).thenReturn(responseMessage);

        // Act
        ResponseEntity<String> response = transactionController.returnBook(validRequest);

        // Assert
        Assertions.assertEquals(responseMessage, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}