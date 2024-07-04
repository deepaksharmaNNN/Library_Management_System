package com.deepaksharma.digital_library.model;

import com.deepaksharma.digital_library.enums.BookType;
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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, length = 50)
    String bookTitle;

    @Column(nullable = false, length = 10, unique = true)
    String bookNo;

    int securityDeposit;

    @Enumerated(value = EnumType.STRING)
    BookType bookType;

    @ManyToOne
    @JoinColumn
    Author author;

    @CreationTimestamp
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;
}
