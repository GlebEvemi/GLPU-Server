package com.skillissue.GLPUinventory.Repository;

import org.springframework.data.repository.CrudRepository;

import com.skillissue.GLPUinventory.Entity.User;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByUsername(String username);

    String findRoleByUsername(String username);
    
}