package com.deepaksharma.Library_Management_System.repository;

import com.deepaksharma.Library_Management_System.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByEmail(String email);
}
