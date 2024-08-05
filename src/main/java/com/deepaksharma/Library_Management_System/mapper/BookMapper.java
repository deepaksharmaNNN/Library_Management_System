package com.deepaksharma.Library_Management_System.mapper;

import com.deepaksharma.Library_Management_System.dto.AddBookRequest;
import com.deepaksharma.Library_Management_System.enums.BookStatus;
import com.deepaksharma.Library_Management_System.model.Book;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookMapper {

    public Book mapToBookEntity(AddBookRequest addBookRequest) {
        return Book.builder()
                .bookTitle(addBookRequest.getBookTitle())
                .bookNo(addBookRequest.getBookNo())
                .bookStatus(BookStatus.AVAILABLE)
                .securityDeposit(addBookRequest.getSecurityDeposit())
                .bookType(addBookRequest.getBookType())
                .build();
    }
}
