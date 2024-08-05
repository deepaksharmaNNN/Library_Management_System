package com.deepaksharma.Library_Management_System.repository;

import java.util.*;
import com.deepaksharma.Library_Management_System.enums.BookType;
import com.deepaksharma.Library_Management_System.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer>, CustomBookRepository{
    Book findBookByBookNo(String bookNo);

    @Query("SELECT distinct b.bookType FROM Book b")
    List<BookType> getAvailableBookTypes();
}
