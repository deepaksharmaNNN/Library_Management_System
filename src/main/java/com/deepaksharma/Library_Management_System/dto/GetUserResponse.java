package com.deepaksharma.Library_Management_System.dto;

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

    List<GetBookResponse> books;
}
