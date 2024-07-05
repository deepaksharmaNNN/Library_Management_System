package com.deepaksharma.digital_library.service;

import com.deepaksharma.digital_library.dto.AddUserRequest;
import com.deepaksharma.digital_library.enums.UserType;
import com.deepaksharma.digital_library.mapper.UserMapper;
import com.deepaksharma.digital_library.model.User;
import com.deepaksharma.digital_library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public User addStudent(AddUserRequest addUserRequest) {
        User user = UserMapper.mapUserRequestToUser(addUserRequest);
        user.setUserType(UserType.STUDENT);
        return userRepository.save(user);
    }
    public User fetchStudentByEmailAddress(String emailAddress) {
        return userRepository.findByEmail(emailAddress);
    }
}
