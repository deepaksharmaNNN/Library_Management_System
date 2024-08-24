package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.GetAuthorResponse;
import com.deepaksharma.Library_Management_System.exceptions.TransactionException;
import com.deepaksharma.Library_Management_System.mapper.AuthorMapper;
import com.deepaksharma.Library_Management_System.model.Author;
import com.deepaksharma.Library_Management_System.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public Author getAuthor(String email) {
        return authorRepository.findByEmail(email);
    }
    public Author addAuthor(Author author) {
        Author existingAuthor = authorRepository.findByEmail(author.getEmail());
        if(existingAuthor != null){
            throw new TransactionException("Author already exists with email: "+author.getEmail());
        }
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

    public List<GetAuthorResponse> getAllAuthors(){
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(AuthorMapper :: mapToGetAuthorResponse)
                .collect(Collectors.toList());
    }
}
