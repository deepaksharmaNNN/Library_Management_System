package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.AddUserRequest;
import com.deepaksharma.Library_Management_System.dto.GetUserResponse;
import com.deepaksharma.Library_Management_System.enums.UserStatus;
import com.deepaksharma.Library_Management_System.enums.UserType;
import com.deepaksharma.Library_Management_System.mapper.UserMapper;
import com.deepaksharma.Library_Management_System.model.User;
import com.deepaksharma.Library_Management_System.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService;

    // add Student -> addStudent_WithValidRequest_ReturnsUser
    @Test
    public void addStudent_WithValidRequest_ReturnsUser() {
        // Arrange
        AddUserRequest addUserRequest = AddUserRequest.builder()
                .userName("user")
                .userEmail("user@gmail.com")
                .build();
        User user = UserMapper.mapUserRequestToUser(addUserRequest);
        user.setUserType(UserType.STUDENT);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        // Act
        User result = userService.addStudent(addUserRequest);
        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(UserType.STUDENT, result.getUserType());

        // Verify
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    // fetch User by Email -> fetchUserByEmail_WithValidEmail_ReturnsUser
    @Test
    public void fetchUserByEmail_WithValidEmail_ReturnsUser() {
        // Arrange
        String email = "user@gmail.com";
        User user = User.builder()
                .name("user")
                .email(email)
                .build();
        when(userRepository.findByEmail(email)).thenReturn(user);
        // Act
        User result = userService.fetchUserByEmail(email);
        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(email, result.getEmail());
        // Verify
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }

    // fetch User by Email -> fetchUserByEmail_WithInvalidEmail_ReturnsNull
    @Test
    public void fetchUserByEmail_WithInvalidEmail_ReturnsNull() {
        // Arrange
        String email = "user@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(null);
        // Act
        User result = userService.fetchUserByEmail(email);
        // Assert
        Assertions.assertNull(result);
        // Verify
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }

    // fetch All Students -> fetchAllStudents_WithExistingStudents_ReturnsListOfStudents
    @Test
    public void fetchAllStudents_WithExistingStudents_ReturnsListOfStudents() {
        // Arrange
        List<User> users = new ArrayList<>();
        User user = User.builder().userType(UserType.STUDENT).build();
        user.setBooks(new ArrayList<>());
        users.add(user);

        Mockito.when(userRepository.findByUserType(UserType.STUDENT)).thenReturn(users);
        // Act
        List<GetUserResponse> result = userService.fetchAllStudents();
        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        // Verify
        Mockito.verify(userRepository, Mockito.times(1)).findByUserType(UserType.STUDENT);
    }

    // fetch All Students -> fetchAllStudents_WithNoStudents_ReturnsEmptyList
    @Test
    public void fetchAllStudents_WithNoStudents_ReturnsEmptyList() {
        // Arrange
        List<User> users = new ArrayList<>();
        Mockito.when(userRepository.findByUserType(UserType.STUDENT)).thenReturn(users);
        // Act
        List<GetUserResponse> result = userService.fetchAllStudents();
        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
        // Verify
        Mockito.verify(userRepository, Mockito.times(1)).findByUserType(UserType.STUDENT);
    }

    // update User Meta Data -> updateUserMetaData_WithValidUser_ReturnsVoid
    @Test
    public void updateUserMetaData_WithValidUser_ReturnsVoid() {
        // Arrange
        User user = User.builder()
                .id(1)
                .name("user")
                .email("user@gmail.com")
                .build();
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        // Act
        userService.updateUserMetaData(user);
        // Assert & Verify
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));

    }

    // unblock Student -> unblockStudent_WithValidCondition_ReturnsUserUnblocked -> more than 45 days
    @Test
    public void unblockStudent_WithValidCondition_ReturnsUserUnblocked() {
        // Arrange
        String email = "user@gmail.com";
        User user = User.builder()
                .email(email)
                .userType(UserType.STUDENT)
                .userStatus(UserStatus.BLOCKED)
                .userType(UserType.STUDENT)
                .updatedOn(new java.util.Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 46))) // 46 days ago
                .build();
        Mockito.when(userRepository.findByEmail(email)).thenReturn(user);
        // Act
        String result = userService.unblockStudent(email);
        // Assert
        Assertions.assertEquals("User Unblocked", result);
        Assertions.assertEquals(UserStatus.ACTIVE, user.getUserStatus());
        // Verify
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }

    // unblock Student -> unblockStudent_WithInvalidCondition_ReturnsUserNotUnblocked -> less than 45 days
    @Test
    public void unblockStudent_WithInvalidCondition_ReturnsUserNotUnblocked() {
        // Arrange
        String email = "user@gmail.com";
        User user = User.builder()
                .email(email)
                .userType(UserType.STUDENT)
                .userStatus(UserStatus.BLOCKED)
                .userType(UserType.STUDENT)
                .updatedOn(new java.util.Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 44))) // 44 days ago
                .build();
        Mockito.when(userRepository.findByEmail(email)).thenReturn(user);
        // Act
        String result = userService.unblockStudent(email);
        // Assert
        Assertions.assertEquals("User can be unblocked after 45 days", result);
        Assertions.assertEquals(UserStatus.BLOCKED, user.getUserStatus());
        // Verify
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }

    // unblock Student -> unblockStudent_WithNullUser_ReturnsUserNotFound
    @Test
    public void unblockStudent_WithNullUser_ReturnsUserNotFound() {
        // Arrange
        String email = "user@gmail.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(null);
        // Act
        String result = userService.unblockStudent(email);
        // Assert
        Assertions.assertEquals("User Not Found", result);
        // Verify
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }

    // unblock Student -> unblockStudent_WithActiveUser_ReturnsUserAlreadyUnblocked
    @Test
    public void unblockStudent_WithActiveUser_ReturnsUserAlreadyUnblocked() {
        // Arrange
        String email = "user@gmail.com";
        User user = User.builder()
                .email(email)
                .userType(UserType.STUDENT)
                .userStatus(UserStatus.ACTIVE)
                .userType(UserType.STUDENT)
                .updatedOn(new java.util.Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 46))) // 46 days ago
                .build();
        Mockito.when(userRepository.findByEmail(email)).thenReturn(user);
        // Act
        String result = userService.unblockStudent(email);
        // Assert
        Assertions.assertEquals("User Already Unblocked", result);
        Assertions.assertEquals(UserStatus.ACTIVE, user.getUserStatus());
        // Verify
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }

    // unblock Student -> unblockStudent_WithNonStudentUser_ReturnsUserIsNotAStudent
    @Test
    public void unblockStudent_WithNonStudentUser_ReturnsUserIsNotAStudent() {
        // Arrange
        String email = "user@gmail.com";
        User user = User.builder()
                .email(email)
                .userType(UserType.ADMIN)
                .updatedOn(new java.util.Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 46))) // 46 days ago
                .build();
        Mockito.when(userRepository.findByEmail(email)).thenReturn(user);
        // Act
        String result = userService.unblockStudent(email);
        // Assert
        Assertions.assertEquals("User is not a Student", result);
        // Verify
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }
}
