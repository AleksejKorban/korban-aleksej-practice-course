package com.aleksej.orderservice;

import com.aleksej.orderservice.client.UserServiceClient;
import com.aleksej.orderservice.dto.OrderDto;
import com.aleksej.orderservice.entity.Order;
import com.aleksej.orderservice.mapper.OrderMapper;
import com.aleksej.orderservice.repository.OrderRepository;
import com.aleksej.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify; // ← ПРАВИЛЬНЫЙ ИМПОРТ
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrderSuccessfully() {
        // given
        OrderDto orderDto = new OrderDto();
        Order order = new Order();
        when(orderMapper.toEntity(orderDto)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        // when
        OrderDto result = orderService.createOrder(orderDto);

        // then
        assertNotNull(result);
        verify(orderRepository).save(order); // ← Теперь работает!
    }

    // more tests...
}