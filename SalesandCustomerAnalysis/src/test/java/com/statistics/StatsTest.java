package com.statistics;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StatsTest {

    private Customer client1 = new Customer("Client1", "Alexej", "korbanaleksej89@gmail.com",
            LocalDateTime.now().minusYears(2), 21, "Minsk");
    private Customer client2 = new Customer("Client2", "Felix", "felix@innowise.com",
            LocalDateTime.now().minusYears(1), 39, "Stuttgart");


    private OrderItem macbook = new OrderItem("MacBook Pro (2025)", 1, 6000.0, Category.ELECTRONICS);
    private OrderItem sonyXM6 = new OrderItem("Sony XM6", 1, 1300.0, Category.ELECTRONICS);
    private OrderItem jacket = new OrderItem("Jacket", 5, 300, Category.CLOTHING);

    private Order deliveredOrder = new Order("Order1", LocalDateTime.now(), client1,
            List.of(macbook, sonyXM6), OrderStatus.DELIVERED);

    private Order newOrder = new Order("Order2", LocalDateTime.now(), client2,
            List.of(jacket), OrderStatus.NEW);

    @Test
    void testUniqueCities() {
        Set<String> cities = Stats.uniqueCities(List.of(deliveredOrder, newOrder));
        assertEquals(Set.of("Minsk", "Stuttgart"), cities);
    }
    @Test
    void testCompletedOrdersCount() {
        List<Order> orders = List.of(deliveredOrder, newOrder);
        long count = Stats.completedOrdersCount(orders);
        assertEquals(1, count);
    }
    @Test
    void testTotalProfit() {
        double profit = Stats.totalProfit(List.of(deliveredOrder, newOrder));
        assertEquals(7300.0, profit);
    }
    @Test
    void testMostPopularProd() {
        String product = Stats.mostPopularProd(List.of(deliveredOrder, newOrder)).orElse("");
        assertEquals("Jacket", product);
    }
    @Test
    void testAverageCheck() {
        double avg = Stats.averageCheck(List.of(deliveredOrder, newOrder));
        assertEquals(7300.0, avg);
    }
    @Test
    void testLoyalCustomers() {
        List<Order> manyOrders = List.of(
                deliveredOrder, deliveredOrder, deliveredOrder,
                deliveredOrder, deliveredOrder, deliveredOrder
        );
        List<Customer> loyal = Stats.loyalCustomers(manyOrders);
        assertEquals(List.of(client1), loyal);
    }
}
