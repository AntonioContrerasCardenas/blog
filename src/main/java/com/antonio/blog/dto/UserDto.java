package com.antonio.blog.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String about;
}
