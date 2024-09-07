package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.dto.AddUserRequest;
import com.deepaksharma.Library_Management_System.dto.GetUserResponse;
import com.deepaksharma.Library_Management_System.model.User;
import com.deepaksharma.Library_Management_System.response.ApiResponse;
import com.deepaksharma.Library_Management_System.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    //Add Student into the Database
    @PostMapping("/add/student") // http://localhost:8080/users/add/student
    public ResponseEntity<User> addStudent(@RequestBody @Valid AddUserRequest addUserRequest){
        User addedUser = userService.addStudent(addUserRequest);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }
    //get Student by Email
    @GetMapping("/fetch/student") // http://localhost:8080/users/fetch/student
    public ResponseEntity<?> fetchUserByEmail(@RequestParam("email") String email){
        User user = userService.fetchUserByEmail(email);
        return new ResponseEntity<>(user == null ? "User Not Found" : user, HttpStatus.OK);
    }
    //get All Students
    @GetMapping("/students") // http://localhost:8080/users/students
    public ResponseEntity<List<GetUserResponse>> fetchAllStudents(){
        List<GetUserResponse> users = userService.fetchAllStudents();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    //Unblock Student
    @PutMapping("/student/unblock") // http://localhost:8080/users/student/unblock
    public ResponseEntity<?> unblockStudent(@RequestParam("email") String email){
        String response = userService.unblockStudent(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Add admin into the Database
    @PostMapping("/add/admin") // http://localhost:8080/users/add/admin
    public ResponseEntity<User> addAdmin(@RequestBody @Valid AddUserRequest addUserRequest){
        User addedUser = userService.addAdmin(addUserRequest);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }

    // get User Details and email will be fetched from authentication
    @GetMapping("/details") // http://localhost:8080/users/details
    public ResponseEntity<ApiResponse> fetchUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        try {
            return ResponseEntity.ok(new ApiResponse("User Details Fetched Successfully", userService.fetchUserByEmail(user.getEmail())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("User Not Found", null));
        }
    }
}
