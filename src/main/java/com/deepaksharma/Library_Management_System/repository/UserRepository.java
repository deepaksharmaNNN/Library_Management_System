package com.deepaksharma.Library_Management_System.repository;

import com.deepaksharma.Library_Management_System.enums.UserType;
import com.deepaksharma.Library_Management_System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> findByUserType(UserType userType);
}
