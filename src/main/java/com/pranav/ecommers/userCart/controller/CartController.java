package com.pranav.ecommers.userCart.controller;

import com.pranav.ecommers.userCart.DTO.CartItemRequest;
import com.pranav.ecommers.userCart.entity.CartItem;
import com.pranav.ecommers.userCart.service.CartService;
//import com.pranav.ecommers.userCart.service.cartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userID,
            @RequestBody CartItemRequest request
    ) {
        if(!cartService.addToCart(userID, request)){
            return ResponseEntity.badRequest().body("Product Out of Stock or User not found or Product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/items/{productID}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userID,
            @PathVariable String productID
    ) {
        boolean deleteted = cartService.deleteItemFromCart(userID, Long.valueOf(productID));
        return  deleteted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
