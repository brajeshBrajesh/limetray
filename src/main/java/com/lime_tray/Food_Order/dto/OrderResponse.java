package com.lime_tray.Food_Order.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;
    private String customerName;
    private List<String> items;
    private Double totalAmount;
    private LocalDateTime orderTime;
    private String status;
}