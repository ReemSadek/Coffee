package com.backend.Coffee.controller;

import com.backend.Coffee.dto.*;
import com.backend.Coffee.enums.ERole;
import com.backend.Coffee.jwt.JwtUtils;
import com.backend.Coffee.model.Role;
import com.backend.Coffee.model.User;
import com.backend.Coffee.repository.RoleRepository;
import com.backend.Coffee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUser userDetails = (CustomUser) authentication.getPrincipal();
        //List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        //	.collect(Collectors.toList());
        return ResponseEntity.ok(new LoginResponse(userDetails.getUserName(), userDetails.getUsername(), jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER.name());
        if (userRole == null) {
            throw new RuntimeException("Error: Role is not found.");
        }
        roles.add(userRole);

        // Create new user's account
        User user = new User(null, signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getUserName(), roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
