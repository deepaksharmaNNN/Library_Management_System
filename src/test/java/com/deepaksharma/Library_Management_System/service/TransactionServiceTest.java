package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.TransactionRequest;
import com.deepaksharma.Library_Management_System.enums.TransactionStatus;
import com.deepaksharma.Library_Management_System.enums.UserStatus;
import com.deepaksharma.Library_Management_System.enums.UserType;
import com.deepaksharma.Library_Management_System.exceptions.TransactionException;
import com.deepaksharma.Library_Management_System.model.Book;
import com.deepaksharma.Library_Management_System.model.Transaction;
import com.deepaksharma.Library_Management_System.model.User;
import com.deepaksharma.Library_Management_System.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
        System.out.println("In before each");
        log.info("In before each by lombok");
        // Set the finePerDay and validDays fields using ReflectionTestUtils
        ReflectionTestUtils.setField(transactionService, "finePerDay", 1);
        ReflectionTestUtils.setField(transactionService, "validDays", 14);
    }
    @BeforeAll
    public static void setupAll() {
        System.out.println("In before all");
        log.info("In before all by lombok");
    }
    // Test cases for getFineAmount With valid days = 14 and fine per day = 1
    @Test
    public void getFineAmount_WithinValidDays_ReturnsCorrectAmount() throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("15/07/2024");
        Transaction transaction = Transaction.builder().createdOn(date).settlementAmount(200).build();
        int fineAmount = transactionService.getFineAmount(transaction);
        Assertions.assertEquals(-173, fineAmount);
    }
    // Test cases for getFineAmount When current date is after valid days
    @Test
    public void getFineAmount_InvalidDays_ReturnsCorrectAmount() throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2024");
        Transaction transaction = Transaction.builder().createdOn(date).settlementAmount(200).build();
        int fineAmount = transactionService.getFineAmount(transaction);
        Assertions.assertEquals(23, fineAmount);
    }


    // Test cases for fetchUser With invalid student
    @Test
    public void fetchUser_InvalidStudent_ThrowsException() throws TransactionException {
        User user = User.builder().id(123).userType(UserType.ADMIN).build();

        Mockito.when(userService.fetchUserByEmail("abc@gmail.com")).thenReturn(user);

        TransactionRequest request = TransactionRequest.builder().userEmail("abc@gmail.com").build();
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.fetchUser(request.getUserEmail()));
    }

    // Test cases for fetchUser With valid student
    @Test
    public void fetchUser_ValidStudent_ReturnsCorrectStudent() throws TransactionException {
        User user = User.builder().id(123).userType(UserType.STUDENT).build();
        Mockito.when(userService.fetchUserByEmail("abc@gmail.com")).thenReturn(user);
        User returnedUser = transactionService.fetchUser("abc@gmail.com");
        Assertions.assertEquals(user, returnedUser);
    }

    //Test case for fetchBook With invalid book
   @Test
    public void fetchBook_InvalidBook_ThrowsException() throws TransactionException {
        Book book = Book.builder().bookNo("123").build();
        Mockito.when(bookService.getBookByBookNo("123")).thenReturn(null);

        Assertions.assertThrows(TransactionException.class,
            () -> transactionService.fetchBook("123"));
    }

    //Test case for fetchBook With valid book
    @Test
    public void fetchBook_ValidBook_ReturnsCorrectBook() throws TransactionException {
        Book book = Book.builder().bookNo("123").build();
        Mockito.when(bookService.getBookByBookNo("123")).thenReturn(book);
        Book returnedBook = transactionService.fetchBook("123");
        Assertions.assertEquals(book, returnedBook);
    }


    //Test case for issueBook With blocked user throws exception
    @Test
    public void issueBook_BlockedUser_ThrowsException() throws TransactionException {
        //Arrange
        User user = User.builder()
                        .id(123)
                        .userType(UserType.STUDENT)
                        .userStatus(UserStatus.BLOCKED)
                        .build();
        String email = "blocked@gmail.com";
        TransactionRequest request = TransactionRequest.builder()
                                    .userEmail(email)
                                    .bookNo("123")
                                    .build();
        Mockito.when(userService.fetchUserByEmail(email)).thenReturn(user);
        //Act and Assert
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.issueBook(request));
    }

    //Test case for issueBook With already issued book throws exception
    @Test
    public void issueBook_AlreadyIssuedBook_ThrowsException() throws TransactionException {
        //Arrange
        User user = User.builder()
                .id(123)
                .userType(UserType.STUDENT)
                .userStatus(UserStatus.ACTIVE)
                .build();
        Book issuedBook = Book.builder()
                .bookNo("123")
                .user(user)
                .build();
        String email = "example@gmail.com";
        TransactionRequest request = TransactionRequest.builder()
                .userEmail(email)
                .bookNo("123")
                .build();
        Mockito.when(userService.fetchUserByEmail(email)).thenReturn(user);
        Mockito.when(bookService.getBookByBookNo("123")).thenReturn(issuedBook);
        //Act and Assert
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.issueBook(request));
    }

    //Test case for issueBook With valid user and book
    @Test
    public void issueBook_ValidUserAndBook_ReturnsTransaction() throws TransactionException {
        //Arrange
        User user = User.builder()
                .id(123)
                .userType(UserType.STUDENT)
                .userStatus(UserStatus.ACTIVE)
                .build();
        Book book = Book.builder()
                .bookNo("123")
                .build();
        String email = "example@gmail.com";
        TransactionRequest request = TransactionRequest.builder()
                .userEmail(email)
                .bookNo("123")
                .build();
        Mockito.when(userService.fetchUserByEmail(email)).thenReturn(user);
        Mockito.when(bookService.getBookByBookNo("123")).thenReturn(book);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        //Act
        Transaction transaction = transactionService.issueBook(request);

        //Assert
        Assertions.assertNotNull(transaction);
        Assertions.assertEquals(user, transaction.getUser());
        Assertions.assertEquals(book, transaction.getBook());
    }

    //Test case for returnBook when user is null
    @Test
    public void returnBook_UserIsNull_ThrowsException() throws TransactionException {
        //Arrange
        TransactionRequest request = TransactionRequest.builder()
                .userEmail("nonexist@gmail.com")
                .bookNo("123")
                .build();
        Mockito.when(userService.fetchUserByEmail("nonexist@gmail.com")).thenReturn(null);
        //Act and Assert
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.returnBook(request));
    }

    //Test case for returnBook when book is null
    @Test
    public void returnBook_BookIsNull_ThrowsException() throws TransactionException {
        //Arrange
        TransactionRequest request = TransactionRequest.builder()
                .userEmail("user@gmail.com")
                .bookNo("123")
                .build();
        User user = User.builder()
                .id(123)
                .userType(UserType.STUDENT)
                .userStatus(UserStatus.ACTIVE)
                .build();
        Mockito.when(userService.fetchUserByEmail("user@gmail.com")).thenReturn(user);
        Mockito.when(bookService.getBookByBookNo("123")).thenReturn(null);
        //Act and Assert
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.returnBook(request));
    }

    //Test case for returnBook With book not issued to user throws exception
    @Test
    public void returnBook_BookNotIssuedToUser_ThrowsException() throws TransactionException {
        //Arrange
        User user = User.builder()
                .id(123)
                .userType(UserType.STUDENT)
                .userStatus(UserStatus.ACTIVE)
                .build();
        Book book = Book.builder()
                .bookNo("123")
                .user(User.builder().id(456).build()) // Book is issued to another user
                .build();

        TransactionRequest request = TransactionRequest.builder()
                .userEmail("user@gmail.com")
                .bookNo("123")
                .build();
        Mockito.when(userService.fetchUserByEmail("user@gmail.com")).thenReturn(user);
        Mockito.when(bookService.getBookByBookNo("123")).thenReturn(book);

        //Act and Assert
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.returnBook(request));
    }

    //Test case for returnBook With valid user and book
    @Test
    public void returnBook_ValidUserAndBook_ReturnsExpectedResult() throws TransactionException {
        //Arrange
        User user = User.builder()
                .id(123)
                .userType(UserType.STUDENT)
                .userStatus(UserStatus.ACTIVE)
                .email("user@gmail.com")
                .build();
        Book book = Book.builder()
                .bookNo("123")
                .user(user)
                .build();
        Transaction transaction = Transaction.builder()
                .user(user)
                .book(book)
                .transactionStatus(TransactionStatus.ISSUED)
                .createdOn(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(10))) // Issued 10 days ago
                .settlementAmount(-100)
                .build();

        TransactionRequest request = TransactionRequest.builder()
                .userEmail("user@gmail.com")
                .bookNo("123")
                .build();
        Mockito.when(userService.fetchUserByEmail("user@gmail.com")).thenReturn(user);
        Mockito.when(bookService.getBookByBookNo("123")).thenReturn(book);
        Mockito.when(transactionRepository.findByUserEmailAndBookBookNo("user@gmail.com", "123")).thenReturn(transaction);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        //Act
        String result = transactionService.returnBook(request);

        //Assert
        Assertions.assertEquals("Amount of 100 has been returned to the user", result);
    }

    //Test case for returnBook when Transaction not found
    @Test
    public void returnBook_TransactionNotFound_ThrowsException() throws TransactionException {
        // Arrange
        User user = User.builder()
                        .id(123)
                        .userType(UserType.STUDENT)
                        .userStatus(UserStatus.ACTIVE)
                        .email("user@gmail.com")
                        .build();
        Book book = Book.builder()
                        .bookNo("123")
                        .user(user)
                        .build();
        TransactionRequest request = TransactionRequest.builder()
                                                       .userEmail("user@gmail.com")
                                                       .bookNo("123")
                                                       .build();
        Mockito.when(userService.fetchUserByEmail("user@gmail.com")).thenReturn(user);
        Mockito.when(bookService.getBookByBookNo("123")).thenReturn(book);
        Mockito.when(transactionRepository.findByUserEmailAndBookBookNo("user@gmail.com", "123")).thenReturn(null);

        // Act and Assert
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.returnBook(request));
    }

    //Test case for returnBook when the fine amount is positive
    @Test
    public void returnBook_FineAmountPositive_ReturnsExpectedResult() throws TransactionException {
        // Arrange
        User user = User.builder()
                        .id(123)
                        .userType(UserType.STUDENT)
                        .userStatus(UserStatus.ACTIVE)
                        .email("user@gmail.com")
                        .build();
        Book book = Book.builder()
                        .bookNo("123")
                        .user(user)
                        .build();
        Transaction transaction = Transaction.builder()
                                             .user(user)
                                             .book(book)
                                             .transactionStatus(TransactionStatus.ISSUED)
                                             .createdOn(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(20))) // Issued 20 days ago
                                             .settlementAmount(-100)
                                             .build();
        TransactionRequest request = TransactionRequest.builder()
                                                       .userEmail("user@gmail.com")
                                                       .bookNo("123")
                                                       .build();
        Mockito.when(userService.fetchUserByEmail("user@gmail.com")).thenReturn(user);
        Mockito.when(bookService.getBookByBookNo("123")).thenReturn(book);
        Mockito.when(transactionRepository.findByUserEmailAndBookBookNo("user@gmail.com", "123")).thenReturn(transaction);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        String result = transactionService.returnBook(request);

        // Assert
        Assertions.assertEquals("Amount of 94 has been returned to the user", result);
    }
}
