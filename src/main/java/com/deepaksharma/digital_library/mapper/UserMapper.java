package com.deepaksharma.digital_library.mapper;

import com.deepaksharma.digital_library.dto.AddUserRequest;
import com.deepaksharma.digital_library.enums.UserStatus;
import com.deepaksharma.digital_library.model.User;
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
