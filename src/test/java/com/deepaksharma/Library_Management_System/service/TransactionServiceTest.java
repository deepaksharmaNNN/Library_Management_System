package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.TransactionRequest;
import com.deepaksharma.Library_Management_System.enums.UserType;
import com.deepaksharma.Library_Management_System.exceptions.TransactionException;
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

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    UserService userService;

    @Mock
    BookService bookService;

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionService transactionService;


    @BeforeEach
    public void setup() {
        System.out.println("In before each");
        log.info("In before each by lombok");
        transactionService = new TransactionService();
        ReflectionTestUtils.setField(transactionService, "finePerDay", 1);
        ReflectionTestUtils.setField(transactionService, "validDays", 14);
    }
    @BeforeAll
    public static void setupAll() {
        System.out.println("In before all");
        log.info("In before all by lombok");
    }
    @Test
    public void getFineAmount_WithinValidDays_ReturnsCorrectAmount() throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("15/07/2024");
        Transaction transaction = Transaction.builder().createdOn(date).settlementAmount(200).build();
        int fineAmount = transactionService.getFineAmount(transaction);
        Assertions.assertEquals(200, fineAmount);
    }
    @Test
    public void getFineAmount_InvalidDays_ReturnsCorrectAmount() throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2024");
        Transaction transaction = Transaction.builder().createdOn(date).settlementAmount(200).build();
        int fineAmount = transactionService.getFineAmount(transaction);
        Assertions.assertEquals(-13, fineAmount);
    }
    @Test
    public void fetchUser_InvalidStudent_ThrowsException() throws TransactionException {
        User user = User.builder().id(123).userType(UserType.ADMIN).build();

        Mockito.when(userService.fetchUserByEmail("abc@gmail.com")).thenReturn(user);

        TransactionRequest request = TransactionRequest.builder().userEmail("abc@gmail.com").build();
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.fetchUser(request.getUserEmail()));
    }

    @Test
    public void fetchUser_ValidStudent_ReturnsCorrectStudent() throws TransactionException {
        User user = User.builder().id(123).userType(UserType.STUDENT).build();

        Mockito.when(userService.fetchUserByEmail("abc@gmail.com")).thenReturn(user);
        TransactionRequest request = TransactionRequest.builder().userEmail("abc@gmail.com").build();
        User returnedUser =  transactionService.fetchUser(request.getUserEmail());

        Assertions.assertEquals(user, returnedUser);
    }
}
