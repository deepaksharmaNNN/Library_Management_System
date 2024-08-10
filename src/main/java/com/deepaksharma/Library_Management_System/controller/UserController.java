package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.dto.AddUserRequest;
import com.deepaksharma.Library_Management_System.model.User;
import com.deepaksharma.Library_Management_System.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    //Add Student into the Database
    @PostMapping("/student")// http://localhost:8080/users/student
    public ResponseEntity<User> addStudent(@RequestBody @Valid AddUserRequest addUserRequest){
        User addedUser = userService.addStudent(addUserRequest);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }
    //get Student by Email
    @GetMapping("/student") // http://localhost:8080/users/student
    public ResponseEntity<?> fetchUserByEmail(@RequestParam("email") String email){
        User user = userService.fetchUserByEmail(email);
        return new ResponseEntity<>(user == null ? "User Not Found" : user, HttpStatus.OK);
    }
    //get All Students
    @GetMapping("/students") // http://localhost:8080/users/students
    public ResponseEntity<?> fetchAllStudents(){
        List<User> users = userService.fetchAllStudents();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
