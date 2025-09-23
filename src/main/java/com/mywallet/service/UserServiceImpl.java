package com.mywallet.service;

import com.mywallet.model.User;
import com.mywallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); 
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
            return optionalUser.get();
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
