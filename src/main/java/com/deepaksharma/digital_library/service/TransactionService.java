package com.deepaksharma.digital_library.service;

import com.deepaksharma.digital_library.dto.TransactionRequest;
import com.deepaksharma.digital_library.model.Transaction;
import com.deepaksharma.digital_library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    public Transaction issueBook(TransactionRequest transactionRequest) {
        return null;
    }
}
