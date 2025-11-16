package com.aleksej.orderservice.mapper;

import com.aleksej.orderservice.dto.OrderDto;
import com.aleksej.orderservice.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
    List<OrderDto> toDtoList(List<Order> orders);
}