package com.pranav.ecommers.Order.service;

import com.pranav.ecommers.Order.DTO.OrderResponse;
import com.pranav.ecommers.Order.OrderItem;
import com.pranav.ecommers.Order.OrderItemDTO;
import com.pranav.ecommers.Order.OrderStatus;
import com.pranav.ecommers.Order.entity.Order;
import com.pranav.ecommers.Order.repository.OrderRepository;
import com.pranav.ecommers.user.entity.User;
import com.pranav.ecommers.user.repository.UserRepository;
import com.pranav.ecommers.userCart.entity.CartItem;
import com.pranav.ecommers.userCart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    public Optional<OrderResponse> createOrder(String userId) {
        //Validate for cart Item
        List<CartItem> cartItems = cartService.getCart(userId);
        if(cartItems.isEmpty()) {
            return Optional.empty();
        }
        //Validate for user also
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty()){
        return Optional.empty();
        }
        User user = userOptional.get();
        // Calculate the total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //Create the Order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                                        null,
                                        item.getProduct(),
                        order,
                        item.getQuantity(),
                        item.getPrice()
                                )).toList();
        order.setItems(orderItems);
        Order savedOrder =  orderRepository.save(order);
        // Clear the Cart
        cartService.clearCart(userId);
        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProoduct().getId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        )).toList(),
                order.getCreatedAt()
        );
    }
}
