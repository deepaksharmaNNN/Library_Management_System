package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.AuthorDTO;
import com.deepaksharma.Library_Management_System.mapper.AuthorMapper;
import com.deepaksharma.Library_Management_System.model.Author;
import com.deepaksharma.Library_Management_System.repository.AuthorRepository;
import com.deepaksharma.Library_Management_System.repository.RedisDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    RedisDataRepository redisDataRepository;

    public Author getAuthorWithBooks(String email) {
        Author author = redisDataRepository.getAuthorFromRedis(email);
        if(author != null){
            log.info("Author found in Redis");
            return author;
        }
        author = authorRepository.findByEmail(email);
        if(author != null){
            log.info("Author found in DB");
            redisDataRepository.saveAuthorToRedis(author);
            log.info("Author saved to Redis");
            return author;
        }
        return null;
    }

    public AuthorDTO getAuthorEntity(String email) {
        Author author = redisDataRepository.getAuthorFromRedis(email);
        if(author != null){
            log.info("Author found in Redis");
            return AuthorMapper.mapToAuthor(author);
        }
        author = authorRepository.findByEmail(email);
        if(author != null){
            log.info("Author found in DB");
            redisDataRepository.saveAuthorToRedis(author);
            log.info("Author saved to Redis");
            return AuthorMapper.mapToAuthor(author);
        }
        return null;
    }
    public Author addAuthor(Author author) {
        Author savedAuthor = authorRepository.save(author);
        redisDataRepository.saveAuthorToRedis(savedAuthor);
        log.info("Author saved to Redis and DB");
        return savedAuthor;
    }
    public String deleteAuthor(String email){
        Author author = authorRepository.findByEmail(email);
        if(author == null){
            return "No author found with email: "+email;
        }
        authorRepository.delete(author);
        return "Author deleted successfully";
    }

    public List<AuthorDTO> getAllAuthors(){
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(AuthorMapper :: mapToAuthor)
                .collect(Collectors.toList());
    }
}
