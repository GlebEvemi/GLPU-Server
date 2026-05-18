package com.skillissue.GLPUinventory.Repository;

import org.springframework.data.repository.CrudRepository;

import com.skillissue.GLPUinventory.Entity.User;

import java.util.Optional;

/**
 * Repository interface for User entity database operations.
 * Extends CrudRepository for basic CRUD functionality and adds custom query methods.
 */
public interface UserRepository extends CrudRepository<User, String> {

    /**
     * Finds a user by their username.
     * @param username the user's login name
     * @return Optional containing the User if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Retrieves the role of a user by username.
     * @param username the user's login name
     * @return the user's role (e.g., ROLE_ADMIN, ROLE_AGENT)
     */
    String findRoleByUsername(String username);
    
}