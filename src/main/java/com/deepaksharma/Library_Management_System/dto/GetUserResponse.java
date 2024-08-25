package com.deepaksharma.Library_Management_System.dto;

import com.deepaksharma.Library_Management_System.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserResponse {
    String userName;

    String userEmail;

    String phoneNumber;

    String userAddress;

    UserStatus userStatus;

    List<GetBookResponse> books;

    public GetUserResponse(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }
}
