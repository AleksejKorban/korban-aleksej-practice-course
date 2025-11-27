package com.aleksej.apigateway.registration.dto;

public record UserCreateRequest(
        String authUserId,
        String firstName,
        String lastName,
        String email
) {}