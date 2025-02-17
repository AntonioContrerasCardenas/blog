package com.antonio.blog.service.impl;

import com.antonio.blog.dto.UserDto;
import com.antonio.blog.entity.User;
import com.antonio.blog.exception.ResourceNotFoundException;
import com.antonio.blog.repository.UserRepo;
import com.antonio.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User savedUser = this.userRepo.save(user);

        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = this.userRepo.findAll();

        List<UserDto> usersDto = users.stream().map(this::userToUserDto).toList();

        return usersDto;
    }

    @Override
    public void deteleUser(Long userId) {
        this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userRepo.deleteById(userId);
    }

//    public UserDto userToUserDto(User user) {
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
//        return userDto;
//    }

//    public User dtoToUser(UserDto userDto) {
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
//        return user;
//    }

    public UserDto userToUserDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }

    public User dtoToUser(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }
}
