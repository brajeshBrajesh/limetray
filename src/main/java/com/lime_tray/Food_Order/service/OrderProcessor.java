package com.lime_tray.Food_Order.service;

import com.lime_tray.Food_Order.entity.Order;
import com.lime_tray.Food_Order.entity.OrderStatus;
import com.lime_tray.Food_Order.queue.OrderQueue;
import com.lime_tray.Food_Order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProcessor {

    private final OrderRepository orderRepository;
    private final OrderQueue orderQueue;

//    @Scheduled(fixedDelay = 3000, initialDelay = 5000) 
    @Scheduled(fixedDelay = 10000)
    public void processOrders() {
        System.out.println("Checking queue for orders to process...");
        Long orderId;
        while ((orderId = orderQueue.poll()) != null) {
            final Long currentOrderId = orderId; // Create final copy for lambda
            orderRepository.findById(currentOrderId).ifPresent(order -> {
                System.out.println("Processing order: " + currentOrderId + " - Status: " + order.getStatus());
                if (order.getStatus() == OrderStatus.PENDING) {
                    order.setStatus(OrderStatus.PROCESSED);
                    orderRepository.save(order);
                    System.out.println("Successfully processed order: " + currentOrderId);
                } else {
                    System.out.println("Order " + currentOrderId + " is already " + order.getStatus());
                }
            });
        }
    }
}