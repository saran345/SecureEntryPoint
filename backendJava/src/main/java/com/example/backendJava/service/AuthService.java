package com.example.backendJava.service;


import com.example.backendJava.dto.JwtAuthResponse;
import com.example.backendJava.dto.LoginDto;
import com.example.backendJava.dto.RegisterDto;
import com.example.backendJava.entity.User;
import com.example.backendJava.repository.UserRepository;
import com.example.backendJava.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final JwtTokenProvider tokenProvider;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public JwtAuthResponse login(LoginDto loginDto){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=tokenProvider.generateToken(authentication);

        User user=userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow();


        JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();

        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRole(user.getRole());

        return jwtAuthResponse;
    }

    public String register(RegisterDto registerDto){
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new RuntimeException("Username is already taken");
        }
        User user=new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setRole(registerDto.getRole());

        userRepository.save(user);

        return "User Registered successfully!";
    }



}
