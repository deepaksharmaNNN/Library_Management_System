package com.deepaksharma.digital_library.repository;

import com.deepaksharma.digital_library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByEmail(String email);
}
