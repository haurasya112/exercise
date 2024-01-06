package com.luna.test.repository;

import com.luna.test.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,String> {
    List<Transaction> findByIsSale(boolean isSale);
    Optional<Transaction> findByIdAndIsSale(String id, boolean isSale);

}
