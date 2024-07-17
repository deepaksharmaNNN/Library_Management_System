package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.dto.TransactionRequest;
import com.deepaksharma.Library_Management_System.model.Transaction;
import com.deepaksharma.Library_Management_System.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction") //localhost:8080/transactions/transaction
    public ResponseEntity<?> issueBook(@RequestBody @Valid TransactionRequest transactionRequest) {
        Transaction transaction = transactionService.issueBook(transactionRequest);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);

    }

    @PutMapping("/transaction") //localhost:8080/transactions/transaction
    public ResponseEntity<String> returnBook(@RequestBody @Valid TransactionRequest transactionRequest) {
        String response = transactionService.returnBook(transactionRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
