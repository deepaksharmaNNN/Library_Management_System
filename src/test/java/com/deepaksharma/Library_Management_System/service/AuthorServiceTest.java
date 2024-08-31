package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.AuthorDTO;
import com.deepaksharma.Library_Management_System.exceptions.TransactionException;
import com.deepaksharma.Library_Management_System.mapper.AuthorMapper;
import com.deepaksharma.Library_Management_System.model.Author;
import com.deepaksharma.Library_Management_System.repository.AuthorRepository;
import com.deepaksharma.Library_Management_System.repository.RedisDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper AuthorMapper;

    @Mock
    private RedisDataRepository redisDataRepository;

    @InjectMocks
    private AuthorService authorService;

    // Test case for getAuthor method checking if author exists
    @Test
    public void getAuthorWithBooks_AuthorExists_ReturnsCorrectAuthor() throws TransactionException {
        //Arrange
        Author author = Author.builder()
                .id(123)
                .email("author@gmail.com")
                .build();
        Mockito.when(authorRepository.findByEmail("author@gmail.com")).thenReturn(author);
        Mockito.when(redisDataRepository.getAuthorFromRedis("author@gmail.com")).thenReturn(author);
        //Act
        Author actualAuthor = authorService.getAuthorWithBooks("author@gmail.com");
        //Assert
        Assertions.assertEquals(author, actualAuthor);
        //Verify
        Mockito.verify(redisDataRepository, Mockito.times(1)).getAuthorFromRedis("author@gmail.com");
        Mockito.verify(authorRepository, Mockito.times(1)).findByEmail("author@gmail.com");    }

    // Test case for getAuthor method checking if author does not exist
    @Test
    public void getAuthor_AuthorDoesNotExist_ReturnsNull() throws TransactionException {
        Author author = Author.builder()
                .id(123)
                .email("author@gmail.com")
                .build();
        Mockito.when(authorRepository.findByEmail("author@gmail.com")).thenReturn(null);
        //Act
        Author actualAuthor = authorService.getAuthorWithBooks("author@gmail.com");
        //Assert
        Assertions.assertEquals(null, actualAuthor);
        //Verify
        Mockito.verify(authorRepository, Mockito.times(1)).findByEmail("author@gmail.com");
    }

    // Test case for addAuthor method checking if author does not exist
    @Test
    public void addAuthor_AuthorDoesNotExist_ReturnsCorrectAuthor() throws TransactionException {
        //Arrange
        Author author = Author.builder()
                .id(123)
                .email("author@gmail.com")
                .build();
        Mockito.when(authorRepository.findByEmail("author@gmail.com")).thenReturn(null);
        Mockito.when(authorRepository.save(author)).thenReturn(author);
        //Act
        Author actualAuthor = authorService.addAuthor(author);
        //Assert
        Assertions.assertEquals(author, actualAuthor);
        //Verify
        Mockito.verify(authorRepository, Mockito.times(1)).findByEmail("author@gmail.com");
    }

    // Test case for addAuthor method checking if author exists
    @Test
    public void addAuthor_AuthorExists_ReturnsTransactionException() throws TransactionException {
        //Arrange
        Author author = Author.builder()
                .id(123)
                .email("author@gmail.com")
                .build();
        Mockito.when(authorRepository.findByEmail("author@gmail.com")).thenReturn(author);
        //Act & Assert
        Assertions.assertThrows(TransactionException.class, () -> authorService.addAuthor(author));
        //Verify
        Mockito.verify(authorRepository, Mockito.times(1)).findByEmail("author@gmail.com");
    }

    // Test case for deleteAuthor method checking if author exists
    @Test
    public void deleteAuthor_AuthorExists_ReturnsSuccessMessage() throws TransactionException {
        //Arrange
        Author author = Author.builder()
                .id(123)
                .email("author@gmail.com")
                .build();
        Mockito.when(authorRepository.findByEmail("author@gmail.com")).thenReturn(author);
        //Act
        String actualMessage = authorService.deleteAuthor("author@gmail.com");
        //Assert
        Assertions.assertEquals("Author deleted successfully", actualMessage);
        //Verify
        Mockito.verify(authorRepository, Mockito.times(1)).findByEmail("author@gmail.com");
    }

    // Test case for deleteAuthor method checking if author does not exist
    @Test
    public void deleteAuthor_AuthorDoesNotExist_ReturnsErrorMessage() throws TransactionException {
        //Arrange
        Author author = Author.builder()
                .id(123)
                .email("author@gmail.com")
                .build();
        Mockito.when(authorRepository.findByEmail("author@gmail.com")).thenReturn(null);
        //Act
        String actualMessage = authorService.deleteAuthor("author@gmail.com");
        //Assert
        Assertions.assertEquals("No author found with email: "+author.getEmail(), actualMessage);
        //Verify
        Mockito.verify(authorRepository, Mockito.times(1)).findByEmail("author@gmail.com");
    }

    // Test case for getAllAuthors method
    @Test
    public void getAllAuthors_ReturnsListOfAuthors() {
        // Arrange
        Author author1 = Author.builder()
                .id(123)
                .name("author1")
                .email("author@gmail.com")
                .createdOn(null)
                .updatedOn(null)
                .build();
        Author author2 = Author.builder()
                .id(124)
                .name("author2")
                .email("author@example.com")
                .createdOn(null)
                .updatedOn(null)
                .build();

        AuthorDTO response1 = new AuthorDTO("author1", "author@gmail.com", "createdOn", "updatedOn");
        AuthorDTO response2 = new AuthorDTO("author2", "author@example.com", "createdOn", "updatedOn");

        Mockito.when(authorRepository.findAll()).thenReturn(List.of(author1, author2));

        // Act
        List<AuthorDTO> actualAuthors = authorService.getAllAuthors();

        // Assert
        Assertions.assertEquals(List.of(response1, response2), actualAuthors);

        // Verify
        Mockito.verify(authorRepository, Mockito.times(1)).findAll();

        // No need to verify the static method invocation
    }

}
