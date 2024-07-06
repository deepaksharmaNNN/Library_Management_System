package com.deepaksharma.digital_library.repository;

import com.deepaksharma.digital_library.enums.BookType;
import com.deepaksharma.digital_library.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomBookRepository {
    List<Book> findBookByFilters(String bookTitle, BookType bookType);
}
