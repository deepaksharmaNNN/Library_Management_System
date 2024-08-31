package com.deepaksharma.Library_Management_System.mapper;

import com.deepaksharma.Library_Management_System.dto.AddBookRequest;
import com.deepaksharma.Library_Management_System.dto.AuthorDTO;
import com.deepaksharma.Library_Management_System.dto.AuthorWithBooksDTO;
import com.deepaksharma.Library_Management_System.model.Author;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class AuthorMapper {

        public Author mapToAuthorEntity(AddBookRequest addBookRequest) {
            return Author.builder()
                    .name(addBookRequest.getAuthorName())
                    .email(addBookRequest.getAuthorEmail())
                    .build();
        }
    public AuthorDTO mapToAuthor(Author author) {
        return AuthorDTO.builder()
                .name(author.getName())
                .email(author.getEmail())
                .createdOn(author.getCreatedOn().toString())
                .updatedOn(author.getUpdatedOn().toString())
                .build();
    }

    public AuthorWithBooksDTO mapToAuthorWithBooks(Author author) {
        return AuthorWithBooksDTO.builder()
                .name(author.getName())
                .email(author.getEmail())
                .createdOn(author.getCreatedOn().toString())
                .updatedOn(author.getUpdatedOn().toString())
                .books(author.getBooks().stream()
                        .map(BookMapper::mapToBook)
                        .collect(Collectors.toList()))
                .build();
    }
}
