package com.aleksej.service;

import com.aleksej.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto createUser(UserDto userDTO);
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    Page<UserDto> getAllUsers(Pageable pageable);
    UserDto updateUser(Long id, UserDto userDTO);
    void deleteUser(Long id);
}
