package com.deepaksharma.digital_library.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, length = 30)
    String name;

    @Column(nullable = false, unique = true, length = 50)
    String email;

    @CreationTimestamp
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;
}
