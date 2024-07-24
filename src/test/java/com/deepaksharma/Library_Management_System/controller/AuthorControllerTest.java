package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.model.Author;
import com.deepaksharma.Library_Management_System.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {
    @Mock
    AuthorService authorService;

    @InjectMocks
    AuthorController authorController;

    @Test
    public void getAuthor_ValidAuthor_ReturnsData() {
        Author author = Author.builder()
                        .id(1).build();
        Mockito.when(authorService.getAuthor(any()))
                        .thenReturn(author);

        ResponseEntity response = authorController.getAuthor("abc@gmail.com");
        Assertions.assertEquals(author, response.getBody());
    }
}
