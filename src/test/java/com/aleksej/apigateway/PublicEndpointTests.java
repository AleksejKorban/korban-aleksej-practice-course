package com.aleksej.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class PublicEndpointTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void loginEndpointAccessibleWithoutJwt() {
        webTestClient.get()
                .uri("/auth/login")
                .exchange()
                .expectStatus().isOk(); // Должен быть доступен без токена
    }
}