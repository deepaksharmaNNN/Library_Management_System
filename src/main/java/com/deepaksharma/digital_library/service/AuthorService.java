package com.deepaksharma.digital_library.service;

import com.deepaksharma.digital_library.model.Author;
import com.deepaksharma.digital_library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public Author getAuthorByEmail(String email) {
        return authorRepository.findByEmail(email);
    }
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }
}
