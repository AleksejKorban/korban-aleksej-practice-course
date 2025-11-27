package com.aleksej.apigateway.registration;

import com.aleksej.apigateway.registration.dto.RegistrationRequest;
import com.aleksej.apigateway.registration.dto.RegistrationResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}