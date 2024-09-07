package com.deepaksharma.Library_Management_System.model;

import com.deepaksharma.Library_Management_System.enums.UserStatus;
import com.deepaksharma.Library_Management_System.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(exclude = {"books", "transactions"})
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(length = 30)
    String name;

    @Column(nullable = false, unique = true, length = 50)
    String email;

    @Column(unique = true, length = 12)
    String phoneNo;

    String address;

    String password;

    String authorities;

    @Enumerated(value = EnumType.STRING)
    UserType userType;

    @Enumerated(value = EnumType.STRING)
    UserStatus userStatus;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    List<Book> books;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    List<Transaction> transactions;

    @CreationTimestamp
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
