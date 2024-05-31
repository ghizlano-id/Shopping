package com.e2y.shopping.repository;

import com.e2y.shopping.model.CartEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartEntryRepository extends JpaRepository<CartEntry,Long> {
}
