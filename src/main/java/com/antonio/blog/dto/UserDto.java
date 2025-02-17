package com.antonio.blog.dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 characters")
    private String name;

    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty
    @Size(min = 4, max = 30, message = "Password must be min of 3 and max of 30 characters")
//    @Pattern(regexp = , message = "")
    private String password;

    @NotEmpty
    private String about;
}
