package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.AddBookRequest;
import com.deepaksharma.Library_Management_System.dto.GetBookResponse;
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

import java.util.List;
import java.util.Map;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private AddBookRequest addBookRequest;

    @Mock
    private GetBookResponse getBookResponse;

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

    // Update book metadata -> updateBookMetadata_WithExistingBook_UpdatesBook
    @Test
    public void updateBookMetadata_WithExistingBook_UpdatesBook() {
        //Arrange
        Book book = Book.builder()
                .id(1)
                .bookNo("NB001")
                .bookTitle("New Book")
                .bookStatus(BookStatus.AVAILABLE)
                .build();
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(i -> {
            Book savedBook = i.getArgument(0);
            savedBook.setId(1);
            return savedBook;
        });

        //Act
        bookService.updateBookMetadata(book);

        //Verify
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }

    // Update book metadata -> updateBookMetadata_WithNonExistingBook_ThrowsException
    @Test
    public void updateBookMetadata_WithNonExistingBook_ThrowsException() {
        //Arrange
        Book book = Book.builder()
                .id(1)
                .bookNo("NB001")
                .bookTitle("New Book")
                .bookStatus(BookStatus.AVAILABLE)
                .build();
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenThrow(new TransactionException("Book not found"));

        //Act
        Assertions.assertThrows(TransactionException.class, () -> {
            bookService.updateBookMetadata(book);
        });

        //Verify
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }

    // Get all books -> getAllBooks_WithExistingBooksByFilters_ReturnsBooks
    @Test
    public void getAllBooks_WithExistingBooksByFilters_returnsBook() {
        //Arrange
        Book book = Book.builder()
                .id(1)
                .bookNo("NB001")
                .bookTitle("New Book")
                .bookStatus(BookStatus.AVAILABLE)
                .build();
        Mockito.when(bookRepository.findBookByFilters("New Book", "NB001")).thenReturn(List.of(book));

        //Act
        List<GetBookResponse> result = bookService.getAllBooks("New Book", "NB001");

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(book.getBookTitle(), result.getFirst().getBookTitle());
        Assertions.assertEquals(book.getBookNo(), result.getFirst().getBookNo());
        Assertions.assertEquals(book.getBookStatus(), result.getFirst().getBookStatus());

        //Verify
        Mockito.verify(bookRepository, Mockito.times(1)).findBookByFilters("New Book", "NB001");
    }

    // Get all books -> getAllBooks_WithNonExistingBooksByFilters_ReturnsEmptyList
    @Test
    public void getAllBooks_WithNonExistingBooksByFilters_returnsEmptyList() {
        //Arrange
        Mockito.when(bookRepository.findBookByFilters("New Book", "NB001")).thenReturn(List.of());

        //Act
        List<GetBookResponse> result = bookService.getAllBooks("New Book", "NB001");

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        //Verify
        Mockito.verify(bookRepository, Mockito.times(1)).findBookByFilters("New Book", "NB001");
    }

    // Get available books -> getAvailableBooks_WithAvailableBooks_ReturnsBookTypeCountMap
    @Test
    public void getAvailableBooks_WithAvailableBooks_ReturnsBookTypeCountMap() {
        //Arrange
        List<Object[]> mockResults = List.of(
            new Object[]{BookType.FICTION, 5L},
            new Object[]{BookType.HISTORY, 3L}
        );
        Mockito.when(bookRepository.findDistinctBookTypesWithCountByStatus(BookStatus.AVAILABLE)).thenReturn(mockResults);

        //Act
        Map<BookType, Long> result = bookService.getAvailableBooks();

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(5L, result.get(BookType.FICTION));
        Assertions.assertEquals(3L, result.get(BookType.HISTORY));

        //Verify
        Mockito.verify(bookRepository, Mockito.times(1)).findDistinctBookTypesWithCountByStatus(BookStatus.AVAILABLE);
    }

    // Delete book -> deleteBook_WithExistingBook_DeletesBook
    @Test
    public void deleteBook_WithExistingBook_DeletesBook() {
        //Arrange
        Book book = Book.builder()
                .id(1)
                .bookNo("NB001")
                .bookTitle("New Book")
                .bookStatus(BookStatus.AVAILABLE)
                .build();
        Mockito.when(bookRepository.findBookByBookNo("NB001")).thenReturn(book);

        //Act
        String result = bookService.deleteBook("NB001");

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Book deleted successfully", result);

        //Verify
        Mockito.verify(bookRepository, Mockito.times(1)).findBookByBookNo("NB001");
        Mockito.verify(bookRepository, Mockito.times(1)).delete(book);
    }

    // Delete book -> deleteBook_WithNonExistingBook_ReturnsErrorMessage
    @Test
    public void deleteBook_WithNonExistingBook_ReturnsErrorMessage() {
        //Arrange
        Mockito.when(bookRepository.findBookByBookNo("NB001")).thenReturn(null);

        //Act
        String result = bookService.deleteBook("NB001");

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("No book found with bookNo: NB001", result);

        //Verify
        Mockito.verify(bookRepository, Mockito.times(1)).findBookByBookNo("NB001");
    }

}
