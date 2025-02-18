package com.antonio.blog.dto;

import com.antonio.blog.entity.Category;
import com.antonio.blog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private String title;

    private String content;

    private String imageName = "default.png";

    private Date addedDate;

    private UserDto user;

    private CategoryDto category;
}
