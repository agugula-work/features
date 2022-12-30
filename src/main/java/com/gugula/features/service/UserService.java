package com.gugula.features.service;

import com.gugula.features.controller.exception.NotFoundException;
import com.gugula.features.entity.User;
import com.gugula.features.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Delegate
    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(NotFoundException::new);
    }
}
