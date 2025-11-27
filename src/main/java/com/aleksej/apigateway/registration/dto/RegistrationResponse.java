package com.aleksej.apigateway.registration.dto;
public record RegistrationResponse(
        String authUserId,
        String userId,
        String email
) {}