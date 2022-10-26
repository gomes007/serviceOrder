package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
