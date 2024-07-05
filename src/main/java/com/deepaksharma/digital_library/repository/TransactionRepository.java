package com.deepaksharma.digital_library.repository;

import com.deepaksharma.digital_library.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
