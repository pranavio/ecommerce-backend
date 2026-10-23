package com.pranav.ecommers.userCart.service;

import com.pranav.ecommers.product.entity.Product;
import com.pranav.ecommers.product.repository.ProductRepository;
import com.pranav.ecommers.user.entity.User;
import com.pranav.ecommers.user.repository.UserRepository;
import com.pranav.ecommers.userCart.DTO.CartItemRequest;
import com.pranav.ecommers.userCart.entity.CartItem;
import com.pranav.ecommers.userCart.repository.CartItempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {
    private final ProductRepository productRepository;
    private final CartItempRepository cartItempRepository;
    private final UserRepository userRepository;
    public boolean addToCart(String userID, CartItemRequest request) {

        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if (productOpt.isEmpty())
            return false;

        Product product = productOpt.get();
        if (product.getStockQuantity() < request.getQuantity())
            return false;

        Optional<User> userOpt =
                userRepository.findById(Long.valueOf(userID));
        if (userOpt.isEmpty())
            return false;

        User user = userOpt.get();

        CartItem existingCartItem = cartItempRepository.findByUserAndProduct(user , product);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItempRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItempRepository.save(cartItem);
        }
        return true;
    }
}
