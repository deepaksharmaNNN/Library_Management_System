package com.deepaksharma.digital_library.controller;

import com.deepaksharma.digital_library.dto.TransactionRequest;
import com.deepaksharma.digital_library.model.Transaction;
import com.deepaksharma.digital_library.service.TransactionService;
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
