package com.example.demoSocket.controller;

import com.example.demoSocket.dto.LoginRequest;
import com.example.demoSocket.entity.User;
import com.example.demoSocket.repository.UserRepository;
import com.example.demoSocket.sercurity.JwtAuthenticationProvider;
import com.example.demoSocket.sercurity.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtAuthenticationProvider authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping
    public String addUser(@RequestBody User user) {
        userRepository.save(user);
        return "success";
    }


//    @PostMapping("/auth/login")
//    public User login(@RequestBody LoginRequest user) {
//      Optional<User> optionalUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//        return optionalUser.orElse(null);
//    }


    @PostMapping("/auth/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

        return jwtUtils.generateJwtToken(authenticationResponse);
    }
}
