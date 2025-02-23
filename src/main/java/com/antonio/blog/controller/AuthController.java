package com.antonio.blog.controller;

import com.antonio.blog.dto.ErrorResponse;
import com.antonio.blog.dto.UserDto;
import com.antonio.blog.entity.User;
import com.antonio.blog.exception.ApiException;
import com.antonio.blog.payload.JwtAuthRequest;
import com.antonio.blog.payload.RegisterUserBody;
import com.antonio.blog.security.CustomUserDetailService;
import com.antonio.blog.security.JwtAuthResponse;
import com.antonio.blog.security.JwtAuthResponse2;
import com.antonio.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Autenticación", description = "APIs para registro y login de usuarios")
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

    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica un usuario y devuelve un token JWT"
    )
    @ApiResponse(responseCode = "200", description = "Login exitoso", content = @Content(schema = @Schema(implementation = JwtAuthResponse2.class)))
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse2> createToken(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciales de usuario",
                    required = true
            )
            @RequestBody JwtAuthRequest request
    ) {
        this.create(request.getUsername(), request.getPassword());

        UserDetails user = this.customUserDetailService.loadUserByUsername(request.getUsername());

        String token = this.jwtUtil.generateToken(user.getUsername());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtAuthResponse2 response = new JwtAuthResponse2();
        response.setToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Crea una cuenta de usuario en el sistema"
    )
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo usuario",
                    required = true
            )
            @RequestBody RegisterUserBody userBody) {

        UserDto userDto = new UserDto();
        userDto.setName(userBody.getName());
        userDto.setEmail(userBody.getEmail());
        userDto.setPassword(userBody.getPassword());
        userDto.setAbout(userBody.getAbout());


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
