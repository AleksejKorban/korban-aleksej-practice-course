package com.aleksej.apigateway.registration.dto;
public record RegistrationRequest(
        String email,
        String password,
        String firstName,
        String lastName
) {}