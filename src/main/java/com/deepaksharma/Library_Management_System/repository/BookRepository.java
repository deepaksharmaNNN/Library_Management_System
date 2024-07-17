package com.deepaksharma.Library_Management_System.repository;

import com.deepaksharma.Library_Management_System.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer>, CustomBookRepository{
    Book findBookByBookNo(String bookNo);
}
