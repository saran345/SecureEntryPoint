package com.example.backendJava.security;

import com.example.backendJava.entity.User;
import com.example.backendJava.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user=userRepository.findByUsername(username)
               .orElseThrow(()-> new UsernameNotFoundException("User not Found: "+username));

     return new org.springframework.security.core.userdetails.User(
             user.getUsername(),
             user.getPassword(),
             user.getAuthorities()
     );
    }
}
