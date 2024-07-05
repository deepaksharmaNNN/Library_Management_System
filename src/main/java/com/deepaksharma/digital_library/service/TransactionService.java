package com.deepaksharma.digital_library.service;

import com.deepaksharma.digital_library.dto.TransactionRequest;
import com.deepaksharma.digital_library.enums.TransactionStatus;
import com.deepaksharma.digital_library.enums.UserStatus;
import com.deepaksharma.digital_library.enums.UserType;
import com.deepaksharma.digital_library.exceptions.TransactionException;
import com.deepaksharma.digital_library.model.Book;
import com.deepaksharma.digital_library.model.Transaction;
import com.deepaksharma.digital_library.model.User;
import com.deepaksharma.digital_library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    public Transaction issueBook(TransactionRequest transactionRequest) {

        User user = fetchUser(transactionRequest.getUserEmail());
        Book book = fetchBook(transactionRequest.getBookNo());
        Transaction transaction = Transaction.builder()
                .user(user)
                .book(book)
                .transactionId(UUID.randomUUID().toString().substring(0, 30))
                .transactionStatus(TransactionStatus.ISSUED)
                .settlementAmount(-book.getSecurityDeposit())
                .build();
        transactionRepository.save(transaction);
        book.setUser(user);
        bookService.updateBookMetadata(book);
        return transaction;
    }
    private User fetchUser(String email) {
        User user = userService.fetchStudentByEmailAddress(email);
        if (user == null) {
            throw new TransactionException("User not found");
        }
        if (user.getUserType() != UserType.STUDENT) {
            throw new TransactionException("Only students can issue books");
        }
        if (user.getUserStatus() == UserStatus.BLOCKED) {
            throw new TransactionException("User is blocked");
        }
        return user;
    }
    private Book fetchBook(String bookNo) {
        Book book = bookService.getBookByBookNo(bookNo);
        if (book == null) {
            throw new TransactionException("Book not found");
        }
        if (book.getUser() != null) {
            throw new TransactionException("Book is already issued");
        }
        return book;
    }
}
