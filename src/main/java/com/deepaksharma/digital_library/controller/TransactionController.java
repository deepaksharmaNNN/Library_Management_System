package com.deepaksharma.digital_library.controller;

import com.deepaksharma.digital_library.dto.TransactionRequest;
import com.deepaksharma.digital_library.exceptions.TransactionException;
import com.deepaksharma.digital_library.model.Transaction;
import com.deepaksharma.digital_library.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction") //localhost:8080/transactions/transaction
    public ResponseEntity<?> issueBook(@RequestBody @Valid TransactionRequest transactionRequest) {
        try {
            Transaction transaction = transactionService.issueBook(transactionRequest);
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } catch (TransactionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
