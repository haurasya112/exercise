package com.luna.test.repository;

import com.luna.test.model.TransactionItemLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionItemLineRepository extends JpaRepository<TransactionItemLine,String> {
    boolean existsByProductId(String s);
}
