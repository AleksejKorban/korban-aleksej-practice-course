package com.aleksej.authenticationservice.controller;

import com.aleksej.authenticationservice.model.AppUser;
import com.aleksej.authenticationservice.repo.UserRepository;
import com.aleksej.authenticationservice.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;



import io.jsonwebtoken.Claims;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(UserRepository repo, PasswordEncoder encoder, JwtService jwt) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public void register(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam(defaultValue = "USER") String role) {
        AppUser u = new AppUser();
        u.setUsername(username);

        u.setPassword(encoder.encode(password));

        u.setRole("ROLE_" + role.toUpperCase());
        repo.save(u);
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestParam String username,
                                    @RequestParam String password) {

        AppUser u = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));


        if (!encoder.matches(password, u.getPassword())) {
            throw new RuntimeException("Bad credentials");
        }


        String access = jwt.generateToken(u.getUsername(), u.getRole(), 15); // 15 минут

        String refresh = jwt.generateToken(u.getUsername(), u.getRole(), 60*24); // 1 день

        return Map.of("access", access, "refresh", refresh);
    }

    @PostMapping("/refresh")
    public Map<String,String> refresh(@RequestParam String token) {

        Claims claims = jwt.parse(token);
        String username = claims.getSubject();
        String role = claims.get("role", String.class);


        String access = jwt.generateToken(username, role, 15); // 15 минут
        return Map.of("access", access);
    }
}