package com.deepaksharma.Library_Management_System.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {

    @NotNull(message = "Book number is mandatory")
    String bookNo;

    @NotNull(message = "User email is mandatory")
    String userEmail;
}
