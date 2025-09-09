package com.lime_tray.Food_Order.repository;


import com.lime_tray.Food_Order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}