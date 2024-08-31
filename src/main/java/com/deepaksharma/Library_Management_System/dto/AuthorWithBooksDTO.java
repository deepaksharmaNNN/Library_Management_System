package com.deepaksharma.Library_Management_System.dto;

import com.deepaksharma.Library_Management_System.model.Book;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorWithBooksDTO {

        String name;

        String email;

        String createdOn;

        String updatedOn;

        List<Book> books;
}
