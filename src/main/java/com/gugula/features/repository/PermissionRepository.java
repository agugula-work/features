package com.gugula.features.repository;


import com.gugula.features.entity.Permission;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;
import java.util.Set;

public interface PermissionRepository extends JpaRepositoryImplementation<Permission, Long> {

    Set<Permission> findByGlobal(Boolean global);

    Optional<Permission> findByName(String name);
}
