package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.model.Author;
import com.deepaksharma.Library_Management_System.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/author") //localhost:8080/authors/author
    public ResponseEntity<?> getAuthor(@RequestParam("email") String email) {
        Author author = authorService.getAuthor(email);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }
}
