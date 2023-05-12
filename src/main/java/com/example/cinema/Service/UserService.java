package com.example.cinema.Service;

import com.example.cinema.Entity.UserEntity;
import com.example.cinema.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserEntity getById(Long user_id) {
        return userRepository.getById(user_id);
    }

    public boolean existsById(Long user_id) {
        return userRepository.existsById(user_id);
    }
}
