package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.dto.AddBookRequest;
import com.deepaksharma.Library_Management_System.dto.AddBookTypeRequest;
import com.deepaksharma.Library_Management_System.dto.GetBookResponse;
import com.deepaksharma.Library_Management_System.enums.BookType;
import com.deepaksharma.Library_Management_System.model.Book;
import com.deepaksharma.Library_Management_System.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/book") //http://localhost:8080/books/book
    public ResponseEntity<?> addBook(@RequestBody @Valid AddBookRequest addBookRequest) {
        // Add book
        Book book = bookService.addBook(addBookRequest);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping("/search") //http://localhost:8080/books/search
    public ResponseEntity<List<GetBookResponse>> searchAllBooks(@RequestParam(value = "title", required = false) String bookTitle
                                            , @RequestParam(value = "type", required = false) BookType bookType) {
        List<GetBookResponse> books = bookService.getAllBooks(bookTitle, bookType);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    @DeleteMapping("/book") //http://localhost:8080/books/book
    public ResponseEntity<?> deleteBook(@RequestParam("bookNo") String bookNo){
        String response = bookService.deleteBook(bookNo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/available") //http://localhost:8080/books/available
    public ResponseEntity<Map<BookType, Long>> getAvailableBooks() {
        Map<BookType, Long> bookTypeIntegerMap = bookService.getAvailableBooks();
        return new ResponseEntity<>(bookTypeIntegerMap, HttpStatus.OK);
    }

    @GetMapping("/categories") //http://localhost:8080/books/categories
    public ResponseEntity<List<BookType>> getAvailableCategories() {
        List<BookType> bookTypes = bookService.getAvailableCategories();
        return new ResponseEntity<>(bookTypes, HttpStatus.OK);
    }
}
