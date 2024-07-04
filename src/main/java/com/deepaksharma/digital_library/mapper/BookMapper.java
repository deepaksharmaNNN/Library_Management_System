package com.deepaksharma.digital_library.mapper;

import com.deepaksharma.digital_library.dto.AddBookRequest;
import com.deepaksharma.digital_library.model.Book;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookMapper {

    public Book mapToBookEntity(AddBookRequest addBookRequest) {
        return Book.builder()
                .bookTitle(addBookRequest.getBookTitle())
                .bookNo(addBookRequest.getBookNo())
                .securityDeposit(addBookRequest.getSecurityDeposit())
                .bookType(addBookRequest.getBookType())
                .build();
    }
}
