package com.deepaksharma.Library_Management_System.mapper;

import com.deepaksharma.Library_Management_System.dto.AddUserRequest;
import com.deepaksharma.Library_Management_System.enums.UserStatus;
import com.deepaksharma.Library_Management_System.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public User mapUserRequestToUser(AddUserRequest addUserRequest){
        return User.builder()
                .name(addUserRequest.getUserName())
                .email(addUserRequest.getUserEmail())
                .phoneNo(addUserRequest.getPhoneNumber())
                .address(addUserRequest.getUserAddress())
                .userStatus(UserStatus.ACTIVE)
                .build();
    }
}
