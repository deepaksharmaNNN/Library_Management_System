package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.dto.AddUserRequest;
import com.deepaksharma.Library_Management_System.model.User;
import com.deepaksharma.Library_Management_System.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/student")// http://localhost:8080/users/student
    public ResponseEntity<User> addStudent(@RequestBody @Valid AddUserRequest addUserRequest){
        User addedUser = userService.addStudent(addUserRequest);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }
    @GetMapping("/student") // http://localhost:8080/users/student
    public ResponseEntity<?> fetchUserByEmail(@RequestParam("email") String email){
        User user = userService.fetchUserByEmail(email);
        return new ResponseEntity<>(user == null ? "User Not Found" : user, HttpStatus.OK);
    }
}
