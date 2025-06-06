package com.example.backendJava.controller;


import com.example.backendJava.dto.JwtAuthResponse;
import com.example.backendJava.dto.LoginDto;
import com.example.backendJava.dto.RegisterDto;
import com.example.backendJava.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        JwtAuthResponse response=authService.login(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response=authService.register(registerDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }




//    @GetMapping("/hello")
//    public String sayHello() {
//        return "Hello from Spring Boot!";
//    }

}
