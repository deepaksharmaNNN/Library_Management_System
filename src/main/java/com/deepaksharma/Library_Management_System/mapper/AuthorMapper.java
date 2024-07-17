package com.deepaksharma.Library_Management_System.mapper;

import com.deepaksharma.Library_Management_System.dto.AddBookRequest;
import com.deepaksharma.Library_Management_System.model.Author;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthorMapper {

        public Author mapToAuthorEntity(AddBookRequest addBookRequest) {
            return Author.builder()
                    .name(addBookRequest.getAuthorName())
                    .email(addBookRequest.getAuthorEmail())
                    .build();
        }
}
