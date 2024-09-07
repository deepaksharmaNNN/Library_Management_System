package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.dto.AuthorDTO;
import com.deepaksharma.Library_Management_System.model.Author;
import com.deepaksharma.Library_Management_System.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class
AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/get/author") // http://localhost:8080/authors/get/author
    public ResponseEntity<?> getAuthorWithBooks(@RequestParam("email") String email) {
        Author author = authorService.getAuthorWithBooks(email);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping("/get/author-entity") // http://localhost:8080/authors/get/author-entity
    public ResponseEntity<AuthorDTO> getAuthorEntity(@RequestParam("email") String email) {
        AuthorDTO authorDTO = authorService.getAuthorEntity(email);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }
    @PostMapping("/add/author") // http://localhost:8080/authors/add/author
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        Author newAuthor = authorService.addAuthor(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }
    @DeleteMapping("delete/author") // http://localhost:8080/authors/delete/author
    public ResponseEntity<?> deleteAuthor(@RequestParam("email") String email){
        String response = authorService.deleteAuthor(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/all") // http://localhost:8080/authors/all
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authorList = authorService.getAllAuthors();
        return new ResponseEntity<>(authorList, HttpStatus.OK);
    }
}
