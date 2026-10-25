package com.pranav.ecommers.Order.controller;

import com.pranav.ecommers.Order.DTO.OrderResponse;
import com.pranav.ecommers.Order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> crateOrder(
            @RequestHeader("X-User-ID") String userId
    ){
    return orderService.createOrder(userId)
            .map(orderResponse -> new ResponseEntity<>(orderResponse, HttpStatus.CREATED))
            .orElseGet(() -> ResponseEntity.badRequest().build());

    }
}
