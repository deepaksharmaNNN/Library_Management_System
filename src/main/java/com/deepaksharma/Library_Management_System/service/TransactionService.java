package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.TransactionRequest;
import com.deepaksharma.Library_Management_System.enums.BookStatus;
import com.deepaksharma.Library_Management_System.enums.TransactionStatus;
import com.deepaksharma.Library_Management_System.enums.UserStatus;
import com.deepaksharma.Library_Management_System.enums.UserType;
import com.deepaksharma.Library_Management_System.exceptions.TransactionException;
import com.deepaksharma.Library_Management_System.model.Book;
import com.deepaksharma.Library_Management_System.model.Transaction;
import com.deepaksharma.Library_Management_System.model.User;
import com.deepaksharma.Library_Management_System.repository.TransactionRepository;
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
    private Integer validDays;

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
        book.setBookStatus(BookStatus.ISSUED);
        bookService.updateBookMetadata(book);
        return transaction;
    }
    public User fetchUser(String email) {
        User user = userService.fetchUserByEmail(email);
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
        int amount = calculateFine(transaction, book);
        //if amount is negative, it means  we need to return the amount to the user
        if (amount < 0) {
            return "Amount of " + Math.abs(amount) + " has been returned to the user";
        }
        return "Fine of " + amount + " has been collected from the user";

    }
    @Transactional
    protected Integer calculateFine(Transaction transaction, Book book){

        int amount = getFineAmount(transaction);
        transactionRepository.save(transaction);

        book.setUser(null);
        bookService.updateBookMetadata(book);
        return amount;
    }
    public int getFineAmount(Transaction transaction){
        Long issueDateInTime = transaction.getCreatedOn().getTime();
        Long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - issueDateInTime;
        long days = TimeUnit.MILLISECONDS.toDays(timeDifference);

        int amount = 0;
        if (days > validDays) {
            int fine = (int) (days - validDays) * finePerDay;
            amount = fine - Math.abs(transaction.getSettlementAmount());
            transaction.setSettlementAmount(-fine);
            transaction.setTransactionStatus(TransactionStatus.FINED);
        }else {
            transaction.setTransactionStatus(TransactionStatus.RETURNED);
            amount = Math.abs(transaction.getSettlementAmount());
            transaction.setSettlementAmount(0);
        }
        transaction.setUpdatedOn(new Date());
        return amount;
    }
}
