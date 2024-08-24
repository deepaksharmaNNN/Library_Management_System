package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.exceptions.TransactionException;
import com.deepaksharma.Library_Management_System.model.Author;
import com.deepaksharma.Library_Management_System.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    // Test case for addBook method checking if author exists
    @Test
    public void addBook_AuthorExists_ReturnsCorrectAuthor() throws TransactionException {
        //Arrange
        Author author = Author.builder()
                .id(123)
                .email("author@gmail.com")
                .build();
        Mockito.when(authorService.getAuthor("author@gmail.com")).thenReturn(author);
        //Act
        Author actualAuthor = authorService.getAuthor("author@gmail.com");
        //Assert
        Assertions.assertEquals(author, actualAuthor);
    }

    // Test case for addBook method checking if author does not exist
    @Test
    public void addBook_AuthorDoesNotExist_ReturnsNull() throws TransactionException {
        //Arrange
        Author author = Author.builder()
                .id(123)
                .email("author@gmail.com")
                .build();
        Mockito.when(authorService.getAuthor("author@gmail.com")).thenReturn(null);
        //Act & Assert
        Assertions.assertNull(authorService.getAuthor("author@gmail.com"));
    }

    // Test case for addBook method checking if book exists
}
