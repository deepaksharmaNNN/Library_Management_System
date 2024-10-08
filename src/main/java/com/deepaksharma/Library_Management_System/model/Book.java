package com.deepaksharma.Library_Management_System.model;

import com.deepaksharma.Library_Management_System.enums.BookStatus;
import com.deepaksharma.Library_Management_System.enums.BookType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(exclude = {"transactions", "author", "user"})
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, length = 50)
    String bookTitle;

    @Column(nullable = false, length = 20, unique = true)
    String bookNo;

    int securityDeposit;

    @Enumerated(value = EnumType.STRING)
    BookType bookType;

    @Enumerated(value = EnumType.STRING)
    BookStatus bookStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    Author author;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    User user;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    @JsonManagedReference
    List<Transaction> transactions;

    @CreationTimestamp
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;
}
