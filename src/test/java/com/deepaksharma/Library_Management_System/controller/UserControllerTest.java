package com.deepaksharma.Library_Management_System.controller;

import com.deepaksharma.Library_Management_System.dto.AddUserRequest;
import com.deepaksharma.Library_Management_System.dto.GetUserResponse;
import com.deepaksharma.Library_Management_System.model.User;
import com.deepaksharma.Library_Management_System.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {


    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    // addStudent_ValidRequest_ReturnsResponseCreated
    @Test
    public void testAddStudent_ValidRequest_ReturnsResponseCreated() {
        // Arrange
        AddUserRequest validRequest = AddUserRequest.builder()
                .email("user@gmail.com")
                .userName("User Name")
                .build();

        User user = new User();
        user.setEmail("user@gmail.com");
        user.setName("User Name");

        Mockito.when(userService.addStudent(Mockito.any(AddUserRequest.class))).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.addStudent(validRequest);

        // Assert
        Assertions.assertEquals(user, response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // fetchUserByEmail_ValidEmail_ReturnsUser
    @Test
    public void testFetchUserByEmail_ValidEmail_ReturnsUser() {
        // Arrange
        String email = "student@gmail.com";
        User user = new User();
        user.setEmail(email);
        user.setName("Student Name");

        Mockito.when(userService.fetchUserByEmail(email)).thenReturn(user);

        // Act
        ResponseEntity<?> response = userController.fetchUserByEmail(email);

        // Assert
        Assertions.assertEquals(user, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // fetchUserByEmail_InvalidEmail_ReturnsNotFound
    @Test
    public void testFetchUserByEmail_InvalidEmail_ReturnsNotFound() {
        // Arrange
        String email = "invalid@gmail.com";

        Mockito.when(userService.fetchUserByEmail(email)).thenReturn(null);

        // Act
        ResponseEntity<?> response = userController.fetchUserByEmail(email);

        // Assert
        Assertions.assertEquals("User Not Found", response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // unblockStudent_ValidEmail_ReturnsOk
    @Test
    public void testUnblockStudent_ValidEmail_ReturnsOk() {
        // Arrange
        String email = "student@gmail.com";
        String responseMessage = "Student unblocked successfully";

        Mockito.when(userService.unblockStudent(email)).thenReturn(responseMessage);

        // Act
        ResponseEntity<?> response = userController.unblockStudent(email);

        // Assert
        Assertions.assertEquals(responseMessage, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // fetchAllStudents_ReturnsListOfStudents
    @Test
    public void testFetchAllStudents_ReturnsListOfStudents() {
        // Arrange
        GetUserResponse student1 = new GetUserResponse("student1@gmail.com", "Student One");
        GetUserResponse student2 = new GetUserResponse("student2@gmail.com", "Student Two");
        List<GetUserResponse> expectedStudents = Arrays.asList(student1, student2);

        Mockito.when(userService.fetchAllStudents()).thenReturn(expectedStudents);

        // Act
        ResponseEntity<List<GetUserResponse>> response = userController.fetchAllStudents();

        // Assert
        Assertions.assertEquals(expectedStudents, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}