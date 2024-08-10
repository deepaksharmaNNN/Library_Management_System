package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.AddUserRequest;
import com.deepaksharma.Library_Management_System.dto.GetBookResponse;
import com.deepaksharma.Library_Management_System.dto.GetUserResponse;
import com.deepaksharma.Library_Management_System.enums.UserType;
import com.deepaksharma.Library_Management_System.mapper.UserMapper;
import com.deepaksharma.Library_Management_System.model.User;
import com.deepaksharma.Library_Management_System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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

    public List<GetUserResponse> fetchAllStudents() {
        List<User> users = userRepository.findByUserType(UserType.STUDENT);
        List<GetUserResponse> getUserResponses = new ArrayList<>();
        for(User user : users){
            GetUserResponse getUserResponse = UserMapper.mapToGetUserResponse(user);
            getUserResponses.add(getUserResponse);
        }
        return getUserResponses;
    }
}
