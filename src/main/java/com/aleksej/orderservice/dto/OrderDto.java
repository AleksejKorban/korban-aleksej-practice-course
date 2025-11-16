package com.aleksej.orderservice.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;

    @NotNull
    private Long userId;

    @NotBlank
    private String status;

    private LocalDateTime creationDate;

    @Valid
    private List<OrderItemDto> orderItems;

    private UserDto user;


}