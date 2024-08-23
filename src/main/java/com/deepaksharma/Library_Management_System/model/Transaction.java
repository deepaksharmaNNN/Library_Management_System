package com.deepaksharma.Library_Management_System.model;

import com.deepaksharma.Library_Management_System.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@ToString(exclude = {"book", "user"})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, length = 30, unique = true)
    String transactionId;

    @Enumerated(value = EnumType.STRING)
    TransactionStatus transactionStatus;

    int settlementAmount;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    Book book;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    User user;

    @CreationTimestamp
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;
}
