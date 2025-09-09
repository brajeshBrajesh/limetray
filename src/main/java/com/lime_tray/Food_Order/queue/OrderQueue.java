package com.lime_tray.Food_Order.queue;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class OrderQueue {
    private final BlockingQueue<Long> queue = new LinkedBlockingQueue<>();

    public void push(Long orderId) {
        queue.offer(orderId);
    }

    public Long poll() {
        return queue.poll();
    }
}