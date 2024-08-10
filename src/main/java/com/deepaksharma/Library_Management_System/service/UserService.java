package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.AddUserRequest;
import com.deepaksharma.Library_Management_System.enums.UserType;
import com.deepaksharma.Library_Management_System.mapper.UserMapper;
import com.deepaksharma.Library_Management_System.model.User;
import com.deepaksharma.Library_Management_System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public User addStudent(AddUserRequest addUserRequest) {
        User user = UserMapper.mapUserRequestToUser(addUserRequest);
        user.setUserType(UserType.STUDENT);
        return userRepository.save(user);
    }
    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> fetchAllStudents() {
        return userRepository.findAllByUserType(UserType.STUDENT);
    }
}
