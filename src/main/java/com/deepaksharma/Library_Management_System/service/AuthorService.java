package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.model.Author;
import com.deepaksharma.Library_Management_System.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public Author getAuthor(String email) {
        return authorRepository.findByEmail(email);
    }
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }
    public String deleteAuthor(String email){
        Author author = authorRepository.findByEmail(email);
        if(author == null){
            return "No author found with email: "+email;
        }
        authorRepository.delete(author);
        return "Author deleted successfully";
    }
}
