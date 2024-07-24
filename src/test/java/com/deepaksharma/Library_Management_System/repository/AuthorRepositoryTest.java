package com.deepaksharma.Library_Management_System.repository;

import com.deepaksharma.Library_Management_System.model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @BeforeEach
    public void setup() {
        System.out.println("In before each");
        Author author = Author.builder()
                .name("abc")
                .email("abc@gmail.com")
                .build();
        authorRepository.save(author);
    }

    @Test
    public void findByEmail_ValidAuthor_ReturnsData() {
        Author author = authorRepository.findByEmail("abc@gmail.com");
        Assertions.assertEquals("abc", author.getName());

    }

    @Test
    public void findByEmail_InvalidAuthor_ReturnsNull(){
        Assertions.assertNull(authorRepository.findByEmail("abd@gmail.com"));
    }

}
