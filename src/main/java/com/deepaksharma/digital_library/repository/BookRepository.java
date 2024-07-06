package com.deepaksharma.digital_library.repository;

import com.deepaksharma.digital_library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer>, CustomBookRepository{
    Book findBookByBookNo(String bookNo);
}
