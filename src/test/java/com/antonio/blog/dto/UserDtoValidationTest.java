package com.antonio.blog.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

//Test de validacion
public class UserDtoValidationTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void userDto_InvalidEmail_ReturnsValidationError() {
        UserDto userDto = new UserDto();
        userDto.setEmail("antonio@");
        userDto.setPassword("Passw0rd!");
        userDto.setName("John");
        userDto.setAbout("Desarrollador Java apasionado");
        userDto.setRoles(new ArrayList<>());

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        System.out.println(violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
        assertFalse(violations.isEmpty());
    }
}
