package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.AddBookRequest;
import com.deepaksharma.Library_Management_System.enums.BookType;
import com.deepaksharma.Library_Management_System.mapper.AuthorMapper;
import com.deepaksharma.Library_Management_System.mapper.BookMapper;
import com.deepaksharma.Library_Management_System.model.Author;
import com.deepaksharma.Library_Management_System.model.Book;
import com.deepaksharma.Library_Management_System.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    AuthorService authorService;

    @Autowired
    BookRepository bookRepository;

    public Book addBook(@Valid AddBookRequest addBookRequest) {
        // Add book
        Author author = authorService.getAuthor(addBookRequest.getAuthorEmail());
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

    public List<Book> getAllBooks(String bookTitle, BookType bookType) {
        return bookRepository.findBookByFilters(bookTitle, bookType);
    }
    public String deleteBook(String bookNo){
        Book book = bookRepository.findBookByBookNo(bookNo);
        if(book == null){
            return "No book found with bookNo: "+bookNo;
        }
        bookRepository.delete(book);
        return "Book deleted successfully";
    }
    public List<BookType> getAvailableCategories() {
        return bookRepository.getAvailableBookTypes();
    }
}
