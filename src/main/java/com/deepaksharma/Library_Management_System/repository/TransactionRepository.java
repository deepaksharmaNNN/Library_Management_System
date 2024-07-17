package com.deepaksharma.Library_Management_System.repository;

import com.deepaksharma.Library_Management_System.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findByUserEmailAndBookBookNo(String userEmail, String bookNo);
}
