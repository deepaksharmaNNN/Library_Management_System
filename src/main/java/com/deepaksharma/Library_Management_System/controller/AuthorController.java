package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.model.Author;
import com.deepaksharma.Library_Management_System.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/author") //http://localhost:8080/authors/author
    public ResponseEntity<?> getAuthor(@RequestParam("email") String email) {
        Author author = authorService.getAuthor(email);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }
    @PostMapping("/author") //http://localhost:8080/authors/author
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        Author newAuthor = authorService.addAuthor(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }
    @DeleteMapping("author") //http://localhost:8080/authors/author
    public ResponseEntity<?> deleteAuthor(@RequestParam("email") String email){
        String response = authorService.deleteAuthor(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
