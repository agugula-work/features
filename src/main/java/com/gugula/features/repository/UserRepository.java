package com.gugula.features.repository;


import com.gugula.features.entity.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;

public interface UserRepository extends JpaRepositoryImplementation<User, Long> {

    Optional<User> findByName(String name);
}
