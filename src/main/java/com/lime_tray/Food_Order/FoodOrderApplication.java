package com.lime_tray.Food_Order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class FoodOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderApplication.class, args);
	}

}
