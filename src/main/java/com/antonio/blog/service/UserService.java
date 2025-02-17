package com.antonio.blog.service;

import com.antonio.blog.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Long userId);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    void deteleUser(Long userId);
}
