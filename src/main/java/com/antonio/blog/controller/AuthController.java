package com.antonio.blog.controller;

import com.antonio.blog.dto.UserDto;
import com.antonio.blog.exception.ApiException;
import com.antonio.blog.payload.JwtAuthRequest;
import com.antonio.blog.security.CustomUserDetailService;
import com.antonio.blog.security.JwtAuthResponse;
import com.antonio.blog.security.JwtAuthResponse2;
import com.antonio.blog.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    @Autowired
    private JwtAuthResponse jwtUtil;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse2> createToken(
            @RequestBody JwtAuthRequest request
    ) {
        this.create(request.getUsername(), request.getPassword());

        UserDetails user = this.customUserDetailService.loadUserByUsername(request.getUsername());

        String token = this.jwtUtil.generateToken(user.getUsername());

        JwtAuthResponse2 response = new JwtAuthResponse2();
        response.setToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        UserDto user = this.userService.registerNewUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    private void create(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid username o password");
        }


    }
}
