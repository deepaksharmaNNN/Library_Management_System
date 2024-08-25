package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.dto.AddBookRequest;
import com.deepaksharma.Library_Management_System.dto.GetBookResponse;
import com.deepaksharma.Library_Management_System.enums.BookType;
import com.deepaksharma.Library_Management_System.model.Book;
import com.deepaksharma.Library_Management_System.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    // addBook_ValidRequest_ReturnsCreated
    @Test
    public void addBook_ValidRequest_ReturnsCreated() {
        // Arrange
        AddBookRequest addBookRequest = new AddBookRequest();
        Book book = new Book();
        Mockito.when(bookService.addBook(addBookRequest)).thenReturn(book);
        // Act
        ResponseEntity<?> response = bookController.addBook(addBookRequest);
        // Assert
        Assertions.assertEquals(book, response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // deleteBook_ValidRequest_ReturnsOk
    @Test
    public void deleteBook_ValidRequest_ReturnsOk() {
        // Arrange
        String bookNo = "123";
        String responseString = "Book deleted successfully";
        Mockito.when(bookService.deleteBook(bookNo)).thenReturn(responseString);
        // Act
        ResponseEntity<?> response = bookController.deleteBook(bookNo);
        // Assert
        Assertions.assertEquals(responseString, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // getAvailableBooks_ValidRequest_ReturnsBookTypesWithCount
    @Test
    public void getAvailableBooks_ValidRequest_ReturnsBookTypesWithCount() {
        // Arrange
        Map<BookType, Long> bookTypeIntegerMap = Collections.singletonMap(BookType.FICTION, 1L);
        Mockito.when(bookService.getAvailableBooks()).thenReturn(bookTypeIntegerMap);
        // Act
        ResponseEntity<Map<BookType, Long>> response = bookController.getAvailableBooks();
        // Assert
        Assertions.assertEquals(bookTypeIntegerMap, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // searchAllBooks_ValidRequest_ReturnsBooks
    @Test
    public void searchAllBooks_ValidRequest_ReturnsBooks() {
        // Arrange
        List<GetBookResponse> books = Collections.singletonList(new GetBookResponse());
        Mockito.when(bookService.getAllBooks(any(), any())).thenReturn(books);
        // Act
        ResponseEntity<List<GetBookResponse>> response = bookController.searchAllBooks(null, null);
        // Assert
        Assertions.assertEquals(books, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
