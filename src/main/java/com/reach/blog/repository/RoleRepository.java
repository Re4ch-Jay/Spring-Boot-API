package com.reach.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reach.blog.models.Role;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(String name);
}
