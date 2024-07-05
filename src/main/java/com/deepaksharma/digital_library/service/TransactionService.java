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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Value("${book.maximum.days}")
    private Integer maximumDays;

    @Value("${book.fine.per.day}")
    private Integer finePerDay;

    public Transaction issueBook(TransactionRequest transactionRequest) {

        User user = fetchUser(transactionRequest.getUserEmail());
        if (user.getUserStatus() == UserStatus.BLOCKED) {
            throw new TransactionException("User is blocked");
        }
        Book book = fetchBook(transactionRequest.getBookNo());
        if (book.getUser() != null) {
            throw new TransactionException("Book is already issued");
        }

        return issueBook(user, book);
    }

    @Transactional
    protected Transaction issueBook(User user, Book book){
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
        return user;
    }
    private Book fetchBook(String bookNo) {
        Book book = bookService.getBookByBookNo(bookNo);
        if (book == null) {
            throw new TransactionException("Book not found");
        }
        return book;
    }

    @SuppressWarnings("SpringTransactionalMethodCallsInspection")
    public String returnBook(TransactionRequest transactionRequest) {
        User user = fetchUser(transactionRequest.getUserEmail());
        Book book = fetchBook(transactionRequest.getBookNo());
        if (book.getUser() != user) {
            throw new TransactionException("Book is not issued to this user");
        }
        Transaction transaction = transactionRepository.findByUserEmailAndBookBookNo(user.getEmail(), book.getBookNo());
        if (transaction == null) {
            throw new TransactionException("Transaction not found");
        }
        int amount = calculateFineAmount(transaction, book);
        //if amount is negative, it means  we need to return the amount to the user
        if (amount < 0) {
            return "Amount of " + Math.abs(amount) + " has been returned to the user";
        }
        return "Fine of " + amount + " has been collected from the user";

    }
    @Transactional
    protected Integer calculateFineAmount(Transaction transaction, Book book){
        Long issueDateInTime = transaction.getCreatedOn().getTime();
        Long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - issueDateInTime;
        long daysElapsed = TimeUnit.MILLISECONDS.toDays(timeElapsed);

        int amount = 0;
        if (daysElapsed > maximumDays) {
            int fine = (int) (daysElapsed - maximumDays) * finePerDay;
            amount = fine - Math.abs(transaction.getSettlementAmount());
            transaction.setSettlementAmount(-fine);
            transaction.setTransactionStatus(TransactionStatus.FINED);
        }else {
            transaction.setTransactionStatus(TransactionStatus.RETURNED);
            amount = Math.abs(transaction.getSettlementAmount());
            transaction.setSettlementAmount(0);
        }
        transaction.setUpdatedOn(new Date());
        transactionRepository.save(transaction);

        book.setUser(null);
        bookService.updateBookMetadata(book);
        return amount;
    }
}
