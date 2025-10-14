package com.statistics;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Customer client1 = new Customer("Client1", "Alexej", "korbanaleksej89@gmail.com",
                LocalDateTime.now().minusYears(2), 21, "Minsk");
        Customer client2 = new Customer("Client2", "Felix", "felix@innowise.com",
                LocalDateTime.now().minusYears(1), 39, "Stuttgart");
        OrderItem macbook = new OrderItem("MacBook Pro (2025)", 1, 6000.0, Category.ELECTRONICS);
        OrderItem sonyXM6 = new OrderItem("Sony XM6", 1, 1300.0, Category.ELECTRONICS);
        OrderItem jacket = new OrderItem("Jacket", 5, 300, Category.CLOTHING);


        Order deliveredOrder = new Order("Order1", LocalDateTime.now(), client1,
                List.of(macbook, sonyXM6), OrderStatus.DELIVERED);
        Order newOrder = new Order("Order2", LocalDateTime.now(), client2,
                List.of(jacket), OrderStatus.NEW);
        List<Order> orders = List.of(deliveredOrder, newOrder);


        System.out.println("unique cities " + Stats.uniqueCities(orders));
        System.out.println("total profit " + Stats.totalProfit(orders));
        System.out.println("most popular prod " + Stats.mostPopularProd(orders).orElse("no data"));
        System.out.println("average check " + Stats.averageCheck(orders));
        System.out.println("completedOrdersCount " + Stats.completedOrdersCount(orders));
        System.out.println("loyal Customers " + Stats.loyalCustomers(orders));
    }

        }
