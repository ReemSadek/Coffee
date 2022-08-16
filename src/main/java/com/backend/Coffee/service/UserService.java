package com.backend.Coffee.service;

import java.util.ArrayList;
import java.util.List;

import com.backend.Coffee.dto.CustomUser;
import com.backend.Coffee.model.User;
import com.backend.Coffee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);


        if (user == null)
            throw new UsernameNotFoundException("Email " + email + " Not found");

        return new CustomUser(user.getEmail(),
                user.getPassword(),true, true, true,true,
                new ArrayList<>(), user.getUserName());

    }

    public User createNewUser(User user) {
        if(user != null) {
            user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }
}
