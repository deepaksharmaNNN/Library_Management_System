package com.deepaksharma.Library_Management_System.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddUserRequest {

    String userName;

    @NotBlank(message = "User email is mandatory")
    String email;

    @NotBlank(message = "User password is mandatory")
    String password;

    String phoneNumber;

    String address;
}
