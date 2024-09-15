package com.deepaksharma.Library_Management_System.service;

import com.deepaksharma.Library_Management_System.dto.AddUserRequest;
import com.deepaksharma.Library_Management_System.dto.GetUserResponse;
import com.deepaksharma.Library_Management_System.enums.UserStatus;
import com.deepaksharma.Library_Management_System.enums.UserType;
import com.deepaksharma.Library_Management_System.mapper.UserMapper;
import com.deepaksharma.Library_Management_System.model.User;
import com.deepaksharma.Library_Management_System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public User addStudent(AddUserRequest addUserRequest) {
        User user = UserMapper.mapToUser(addUserRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserType(UserType.STUDENT);
        user.setAuthorities("STUDENT");
        return userRepository.save(user);
    }
    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User getUserByEmail(String email) {
        log.info("Attempting to fetch user with email: {}", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.warn("User not found for email: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        log.info("User found: {}", user);
        return user;
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
    public void updateUserMetaData(User user) {
        userRepository.save(user);
    }
    public String unblockStudent(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            return "User Not Found";
        }
        if(user.getUserStatus() == UserStatus.ACTIVE){
            return "User Already Unblocked";
        }
        if(user.getUserType() != UserType.STUDENT){
            return "User is not a Student";
        }
        //Only unblock student after 45 days
        if(user.getUserStatus() == UserStatus.BLOCKED){
            long days = (System.currentTimeMillis() - user.getUpdatedOn().getTime()) / (1000 * 60 * 60 * 24);
            if(days < 45){
                return "User can be unblocked after 45 days";
            }else {
                user.setUserStatus(UserStatus.ACTIVE);
                updateUserMetaData(user);
            }
        }
        return "User Unblocked";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User addAdmin(AddUserRequest addUserRequest) {
        User user = UserMapper.mapToUser(addUserRequest);
        user.setUserType(UserType.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities("ADMIN,STUDENT");
        return userRepository.save(user);
    }
}
