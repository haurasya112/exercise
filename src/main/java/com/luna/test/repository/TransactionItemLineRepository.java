package com.luna.test.repository;

import com.luna.test.model.TransactionItemLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionItemLineRepository extends JpaRepository<TransactionItemLine,String> {
}
