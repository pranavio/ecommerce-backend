package com.pranav.ecommers.userCart.repository;

import com.pranav.ecommers.product.entity.Product;
import com.pranav.ecommers.user.entity.User;
import com.pranav.ecommers.userCart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItempRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);
}
