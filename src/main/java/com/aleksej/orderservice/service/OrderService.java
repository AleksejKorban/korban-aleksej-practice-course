package com.aleksej.orderservice.service;

import com.aleksej.orderservice.client.UserServiceClient;
import com.aleksej.orderservice.dto.OrderDto;
import com.aleksej.orderservice.dto.UserDto;
import com.aleksej.orderservice.entity.Order;
import com.aleksej.orderservice.exception.OrderNotFoundException;
import com.aleksej.orderservice.mapper.OrderMapper;
import com.aleksej.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserServiceClient userServiceClient;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, UserServiceClient userServiceClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userServiceClient = userServiceClient;
    }

    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        order.setCreationDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        return enrichWithUserInfo(orderMapper.toDto(savedOrder));
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        return enrichWithUserInfo(orderMapper.toDto(order));
    }

    public List<OrderDto> getOrdersByIds(List<Long> ids) {
        List<Order> orders = orderRepository.findByIdIn(ids);
        return orders.stream()
                .map(orderMapper::toDto)
                .map(this::enrichWithUserInfo)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersByStatuses(List<String> statuses) {
        List<Order> orders = orderRepository.findByStatusIn(statuses);
        return orders.stream()
                .map(orderMapper::toDto)
                .map(this::enrichWithUserInfo)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));


        existingOrder.setStatus(orderDto.getStatus());


        Order updatedOrder = orderRepository.save(existingOrder);
        return enrichWithUserInfo(orderMapper.toDto(updatedOrder));
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

    private OrderDto enrichWithUserInfo(OrderDto orderDto) {
        try {
            UserDto user = userServiceClient.getUserById(orderDto.getUserId());
            orderDto.setUser(user);
        } catch (Exception e) {

        }
        return orderDto;
    }
}