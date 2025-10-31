package com.aleksej.integration;

import com.aleksej.domain.User;
import com.aleksej.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser() {
        User user = new User();
        user.setName("Alexey");
        userRepository.save(user);

        assertThat(userRepository.findAll()).isNotEmpty();
    }
}