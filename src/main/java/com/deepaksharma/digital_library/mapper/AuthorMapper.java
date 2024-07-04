package com.deepaksharma.digital_library.mapper;

import com.deepaksharma.digital_library.dto.AddBookRequest;
import com.deepaksharma.digital_library.model.Author;
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
