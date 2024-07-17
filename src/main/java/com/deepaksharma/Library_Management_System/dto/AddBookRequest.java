package com.deepaksharma.Library_Management_System.dto;

import com.deepaksharma.Library_Management_System.enums.BookType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddBookRequest {

    @NotBlank(message = "Book title is mandatory")
    String bookTitle;

    @NotBlank(message = "Book number is mandatory")
    String bookNo;

    @Positive(message = "Security deposit should be positive")
    int securityDeposit;

    @NotNull(message = "Book type is mandatory")
    BookType bookType;

    @NotBlank(message = "Author name is mandatory")
    String authorName;

    @NotBlank(message = "Author email is mandatory")
    String authorEmail;
}
