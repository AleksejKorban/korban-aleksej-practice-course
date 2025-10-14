package com.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
//использую ломбок что уменьшить код
@Data
@AllArgsConstructor
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private LocalDateTime registeredAt;
    private int age;
    private String city;

}
