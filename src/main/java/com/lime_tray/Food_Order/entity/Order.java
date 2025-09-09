package com.lime_tray.Food_Order.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(columnDefinition = "TEXT")
    private String items; // store as comma-separated string

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    // Helper methods to convert between String and List<String>
    public void setItemsList(List<String> itemsList) {
        this.items = String.join(",", itemsList);
    }

    public List<String> getItemsList() {
        if (items == null || items.trim().isEmpty()) {
            return List.of();
        }
        return List.of(items.split(","));
    }
}