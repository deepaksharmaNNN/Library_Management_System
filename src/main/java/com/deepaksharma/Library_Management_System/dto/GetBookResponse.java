package com.deepaksharma.Library_Management_System.dto;

import com.deepaksharma.Library_Management_System.enums.BookStatus;
import com.deepaksharma.Library_Management_System.enums.BookType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class GetBookResponse {
    String bookTitle;

    String bookNo;

    int securityDeposit;

    BookType bookType;

    BookStatus bookStatus;
}
