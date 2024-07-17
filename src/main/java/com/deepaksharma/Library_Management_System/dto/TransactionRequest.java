package com.deepaksharma.Library_Management_System.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {

    @NotBlank(message = "Book number is mandatory")
    String bookNo;

    @NotBlank(message = "User email is mandatory")
    String userEmail;
}
