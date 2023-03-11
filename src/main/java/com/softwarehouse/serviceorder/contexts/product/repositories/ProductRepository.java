package com.softwarehouse.serviceorder.contexts.product.repositories;

import com.softwarehouse.serviceorder.contexts.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
