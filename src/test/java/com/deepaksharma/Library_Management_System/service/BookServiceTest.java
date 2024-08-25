package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.AddBookRequest;
import com.deepaksharma.Library_Management_System.enums.BookStatus;
import com.deepaksharma.Library_Management_System.enums.BookType;
import com.deepaksharma.Library_Management_System.exceptions.TransactionException;
import com.deepaksharma.Library_Management_System.model.Author;
import com.deepaksharma.Library_Management_System.model.Book;
import com.deepaksharma.Library_Management_System.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private AddBookRequest addBookRequest;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    // Add a book with existing author -> addBook_WithExistingAuthor_AddsBook
    @Test
    public void addBook_WithExistingAuthor_AddsBook() {
        //Arrange
        AddBookRequest addBookRequest = AddBookRequest.builder()
                .bookTitle("New Book")
                .bookNo("NB001")
                .authorEmail("author@gmail.com")
                .build();
        Author author = Author.builder()
                .id(1)
                .email("author@gmail.com")
                .build();
        Book book = Book.builder()
                .id(1)
                .bookNo("NB001")
                .bookTitle("New Book")
                .bookStatus(BookStatus.AVAILABLE)
                .author(author)
                .build();

        Mockito.when(authorService.getAuthor("author@gmail.com")).thenReturn(author);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(i -> {
            Book savedBook = i.getArgument(0);
            savedBook.setId(1);
            return savedBook;
        });

        //Act
        Book result = bookService.addBook(addBookRequest);

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("New Book", result.getBookTitle());
        Assertions.assertEquals(book, result);

        //Verify
        Mockito.verify(authorService, Mockito.times(1)).getAuthor("author@gmail.com");
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }

    // Add a book with new author -> addBook_WithNewAuthor_AddsBook
    @Test
    public void addBook_WithNewAuthor_AddsBook() {
        //Arrange
        AddBookRequest addBookRequest = AddBookRequest.builder()
                .bookTitle("New Book")
                .bookNo("NB001")
                .authorName("Author Name")
                .authorEmail("author@gmail.com")
                .build();
        Author author = Author.builder()
                .id(1)
                .name("Author Name")
                .email("author@gmail.com")
                .build();
        Book book = Book.builder()
                .id(1)
                .bookNo("NB001")
                .bookTitle("New Book")
                .bookStatus(BookStatus.AVAILABLE)
                .author(author)
                .build();
        Mockito.when(authorService.getAuthor("author@gmail.com")).thenReturn(null);
        Mockito.when(authorService.addAuthor(Mockito.any(Author.class))).thenAnswer(i -> {
            Author savedAuthor = i.getArgument(0);
            savedAuthor.setId(1);
            return savedAuthor;
        });
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenAnswer((i -> {
            Book savedBook = i.getArgument(0);
            savedBook.setId(1);
            return savedBook;
        }));


        //Act
        Book result = bookService.addBook(addBookRequest);

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(book.getId(), result.getId());
        Assertions.assertEquals(book.getBookTitle(), result.getBookTitle());
        Assertions.assertEquals(book.getBookNo(), result.getBookNo());
        Assertions.assertEquals(book.getBookStatus(), result.getBookStatus());
        Assertions.assertEquals(book.getAuthor(), result.getAuthor());


        //Verify
        Mockito.verify(authorService, Mockito.times(1)).getAuthor("author@gmail.com");
        Mockito.verify(authorService, Mockito.times(1)).addAuthor(Mockito.any(Author.class));
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }

    // Get book by book number -> getBookByBookNo_WithExistingBook_ReturnsBook
    @Test
    public void getBookByBookNo_WithExistingBook_ReturnsBook() {
        //Arrange
        Book book = Book.builder()
                .id(1)
                .bookNo("NB001")
                .bookTitle("New Book")
                .bookStatus(BookStatus.AVAILABLE)
                .build();
        Mockito.when(bookRepository.findBookByBookNo("NB001")).thenReturn(book);

        //Act
        Book result = bookService.getBookByBookNo("NB001");

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(book, result);

        //Verify
        Mockito.verify(bookRepository, Mockito.times(1)).findBookByBookNo("NB001");
    }

    // Get book by book number -> getBookByBookNo_WithNonExistingBook_ReturnsNull
    @Test
    public void getBookByBookNo_WithNonExistingBook_ReturnsNull() {
        //Arrange
        Mockito.when(bookRepository.findBookByBookNo("NB001")).thenReturn(null);

        //Act
        Book result = bookService.getBookByBookNo("NB001");

        //Assert
        Assertions.assertNull(result);

        //Verify
        Mockito.verify(bookRepository, Mockito.times(1)).findBookByBookNo("NB001");
    }
}
