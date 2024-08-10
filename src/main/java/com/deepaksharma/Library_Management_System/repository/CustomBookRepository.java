package com.deepaksharma.Library_Management_System.repository;

import com.deepaksharma.Library_Management_System.enums.BookType;
import com.deepaksharma.Library_Management_System.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomBookRepository {
    List<Book> findBookByFilters(String bookTitle, String bookNo);
}
