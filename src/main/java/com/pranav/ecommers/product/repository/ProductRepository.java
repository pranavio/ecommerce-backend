package com.pranav.ecommers.product.repository;

import com.pranav.ecommers.product.DTO.ProductResponse;
import com.pranav.ecommers.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();
}
