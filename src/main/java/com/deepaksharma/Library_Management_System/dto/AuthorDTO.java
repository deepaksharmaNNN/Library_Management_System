package com.deepaksharma.Library_Management_System.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorDTO{

    String name;

    String email;

    String createdOn;

    String updatedOn;
}
