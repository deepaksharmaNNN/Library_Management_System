package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.dto.GetAuthorResponse;
import com.deepaksharma.Library_Management_System.model.Author;
import com.deepaksharma.Library_Management_System.service.AuthorService;
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

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {
    @Mock
    AuthorService authorService;

    @InjectMocks
    AuthorController authorController;

    // Test for getAuthor_ValidAuthor_ReturnsData
    @Test
    public void getAuthor_ValidAuthor_ReturnsData() {
        Author author = Author.builder()
                        .id(1).build();
        Mockito.when(authorService.getAuthor(any()))
                        .thenReturn(author);

        ResponseEntity<?> response = authorController.getAuthor("abc@gmail.com");
        Assertions.assertEquals(author, response.getBody());
    }

    // Test for addAuthor_ValidAuthor_ReturnsCreated
    @Test
    public void addAuthor_ValidAuthor_ReturnsCreated() {
        // Arrange
        Author author = Author.builder()
                        .id(1).build();
        Mockito.when(authorService.addAuthor(any()))
                        .thenReturn(author);
        // Act
        ResponseEntity<Author> response = authorController.addAuthor(author);
        // Assert
        Assertions.assertEquals(author, response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // Test for deleteAuthor_ValidAuthor_ReturnsOk
    @Test
    public void deleteAuthor_ValidAuthor_ReturnsOk() {
        // Arrange
        String email = "author@gmail.com";
        String responseMessage = "Author deleted successfully";
        Mockito.when(authorService.deleteAuthor(any()))
                .thenReturn("Author deleted successfully");
        // Act
        ResponseEntity<?> response = authorController.deleteAuthor(responseMessage);
        // Assert
        Assertions.assertEquals(responseMessage, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Test for getAllAuthors_ValidAuthors_ReturnsListOfAuthors
    @Test
    public void getAllAuthors_ValidAuthors_ReturnsListOfAuthors() {
        // Arrange
        List<GetAuthorResponse> authors = Collections.singletonList(new GetAuthorResponse());
        Mockito.when(authorService.getAllAuthors())
                        .thenReturn(authors);
        // Act
        ResponseEntity<List<GetAuthorResponse>> response = authorController.getAllAuthors();
        // Assert
        Assertions.assertEquals(authors, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
