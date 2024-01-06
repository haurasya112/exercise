package com.luna.test.repository;

import com.luna.test.model.Product;
import com.luna.test.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxRepository extends JpaRepository<Tax, String> {

    Optional<Tax> findById(String id);
}
