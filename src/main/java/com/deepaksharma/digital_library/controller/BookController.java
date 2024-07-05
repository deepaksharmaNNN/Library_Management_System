package com.deepaksharma.digital_library.controller;

import com.deepaksharma.digital_library.dto.AddBookRequest;
import com.deepaksharma.digital_library.model.Book;
import com.deepaksharma.digital_library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/book") //localhost:8080/books/book
    public ResponseEntity<?> addBook(@RequestBody @Valid AddBookRequest addBookRequest) {
        // Add book
        Book book = bookService.addBook(addBookRequest);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }
}
