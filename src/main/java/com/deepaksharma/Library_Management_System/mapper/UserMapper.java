package com.deepaksharma.Library_Management_System.mapper;

import com.deepaksharma.Library_Management_System.dto.AddUserRequest;
import com.deepaksharma.Library_Management_System.dto.GetBookResponse;
import com.deepaksharma.Library_Management_System.dto.GetUserResponse;
import com.deepaksharma.Library_Management_System.enums.UserStatus;
import com.deepaksharma.Library_Management_System.model.User;
import lombok.experimental.UtilityClass;

import java.util.List;

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
    public GetUserResponse mapToGetUserResponse(User user){
        List<GetBookResponse> books = user.getBooks().stream()
                .map(BookMapper::mapToGetBookResponse)
                .toList();
        return GetUserResponse.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .phoneNumber(user.getPhoneNo())
                .userAddress(user.getAddress())
                .userStatus(user.getUserStatus())
                .books(books)
                .build();
    }
}
