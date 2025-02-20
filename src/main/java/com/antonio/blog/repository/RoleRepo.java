package com.antonio.blog.repository;

import com.antonio.blog.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    
}
