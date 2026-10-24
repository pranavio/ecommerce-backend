package com.pranav.ecommers.userCart.service;

import com.pranav.ecommers.product.entity.Product;
import com.pranav.ecommers.product.repository.ProductRepository;
import com.pranav.ecommers.user.entity.User;
import com.pranav.ecommers.user.repository.UserRepository;
import com.pranav.ecommers.userCart.DTO.CartItemRequest;
import com.pranav.ecommers.userCart.entity.CartItem;
import com.pranav.ecommers.userCart.repository.CartItempRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import javax.swing.plaf.ListUI;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
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

    public boolean deleteItemFromCart(String userID, Long productID) {
        Optional<Product> productOpt = productRepository.findById(productID);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userID));
      if(productOpt.isPresent() && userOpt.isPresent()) {
          cartItempRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
          return true;
      }
        return false;
    }

    public  List<CartItem> getCart(String userID) {
        return userRepository.findById(Long.valueOf(userID))
                .map(cartItempRepository::findByUser)
                .orElseGet(List::of);
    }
}
