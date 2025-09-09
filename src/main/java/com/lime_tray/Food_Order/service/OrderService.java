package com.lime_tray.Food_Order.service;

import com.lime_tray.Food_Order.dto.OrderRequest;
import com.lime_tray.Food_Order.dto.OrderResponse;
import com.lime_tray.Food_Order.entity.Order;
import com.lime_tray.Food_Order.entity.OrderStatus;
import com.lime_tray.Food_Order.queue.OrderQueue;
import com.lime_tray.Food_Order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderQueue orderQueue;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .totalAmount(request.getTotalAmount())
                .orderTime(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build();
        
        // Set items using helper method
        order.setItemsList(request.getItems());

        orderRepository.save(order);

        orderQueue.push(order.getId()); // async processing
        return mapToResponse(order);
    }

    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public OrderResponse getOrderStatus(Long id) {
        return orderRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        try {
            order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status + ". Valid statuses are: PENDING, PROCESSED");
        }
        return mapToResponse(orderRepository.save(order));
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .items(order.getItemsList())
                .totalAmount(order.getTotalAmount())
                .orderTime(order.getOrderTime())
                .status(order.getStatus().name())
                .build();
    }
}