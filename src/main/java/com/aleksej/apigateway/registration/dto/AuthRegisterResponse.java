package com.aleksej.apigateway.registration.dto;

public record AuthRegisterResponse(
        String authUserId,
        String email
) {}