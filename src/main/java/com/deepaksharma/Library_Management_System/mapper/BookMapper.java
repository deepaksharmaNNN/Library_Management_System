package com.deepaksharma.Library_Management_System.mapper;

import com.deepaksharma.Library_Management_System.dto.AddBookRequest;
import com.deepaksharma.Library_Management_System.dto.GetBookResponse;
import com.deepaksharma.Library_Management_System.enums.BookStatus;
import com.deepaksharma.Library_Management_System.enums.BookType;
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
    public GetBookResponse mapToGetBookResponse(Book book) {
        return GetBookResponse.builder()
                .bookTitle(book.getBookTitle())
                .bookNo(book.getBookNo())
                .securityDeposit(book.getSecurityDeposit())
                .bookType(book.getBookType())
                .bookStatus(book.getBookStatus())
                .build();
    }

    public Book mapToBook(Book book) {
        return Book.builder()
                .bookTitle(book.getBookTitle())
                .bookNo(book.getBookNo())
                .securityDeposit(book.getSecurityDeposit())
                .bookType(book.getBookType())
                .bookStatus(book.getBookStatus())
                .build();
    }

}
