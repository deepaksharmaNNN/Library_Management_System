package com.deepaksharma.digital_library.service;

import com.deepaksharma.digital_library.dto.AddBookRequest;
import com.deepaksharma.digital_library.mapper.AuthorMapper;
import com.deepaksharma.digital_library.mapper.BookMapper;
import com.deepaksharma.digital_library.model.Author;
import com.deepaksharma.digital_library.model.Book;
import com.deepaksharma.digital_library.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    AuthorService authorService;

    @Autowired
    BookRepository bookRepository;

    public Book addBook(@Valid AddBookRequest addBookRequest) {
        // Add book
        Author author = authorService.getAuthorByEmail(addBookRequest.getAuthorEmail());
        if(author == null){
            author = AuthorMapper.mapToAuthorEntity(addBookRequest);
            authorService.addAuthor(author);
        }
        Book book = BookMapper.mapToBookEntity(addBookRequest);
        book.setAuthor(author);
        bookRepository.save(book);
        return book;
    }
    public Book getBookByBookNo(String bookNo) {
        return bookRepository.findBookByBookNo(bookNo);
    }
    public void updateBookMetadata(Book book) {
        bookRepository.save(book);
    }
}
