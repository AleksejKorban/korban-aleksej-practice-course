package com.aleksej.orderservice.client;

import com.aleksej.orderservice.dto.UserDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserServiceClient {

    @GetMapping("/users/{userId}")
    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    UserDto getUserById(@PathVariable Long userId);

    default UserDto getUserFallback(Long userId, Throwable throwable) {

        return new UserDto();
    }
}