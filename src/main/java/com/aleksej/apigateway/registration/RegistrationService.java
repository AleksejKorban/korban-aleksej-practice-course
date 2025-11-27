package com.aleksej.apigateway.registration;

import com.aleksej.apigateway.registration.dto.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RegistrationService {

    private final WebClient authClient;
    private final WebClient userClient;

    public RegistrationService(
            @Value("${orchestrator.auth.base-url}") String authBase,
            @Value("${orchestrator.user.base-url}") String userBase) {
        this.authClient = WebClient.builder().baseUrl(authBase).build();
        this.userClient = WebClient.builder().baseUrl(userBase).build();
    }

    public Mono<RegistrationResponse> register(RegistrationRequest req) {

        return authClient.post()
                .uri("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(AuthRegisterResponse.class)
                .flatMap(authResp -> {

                    UserCreateRequest createUser = new UserCreateRequest(
                            authResp.authUserId(), req.firstName(), req.lastName(), req.email()
                    );

                    return userClient.post()
                            .uri("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(createUser)
                            .retrieve()
                            .bodyToMono(UserCreateResponse.class)
                            .map(userResp -> new RegistrationResponse(
                                    authResp.authUserId(), userResp.userId(), authResp.email()
                            ))
                            .onErrorResume(err -> compensateAuth(authResp.authUserId()).then(Mono.error(err)));
                });
    }

    private Mono<Void> compensateAuth(String authUserId) {
        return authClient.delete()
                .uri("/auth/users/{id}", authUserId)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(e -> Mono.empty()); // swallow compensation errors to avoid masking root cause
    }
}