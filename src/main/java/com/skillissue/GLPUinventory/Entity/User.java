package com.skillissue.GLPUinventory.Entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * User entity for authentication and authorization.
 * Maps to the 'user' table in the database.
 * Stores user credentials and role information for Spring Security.
 */
@Entity
@Table(name = "user")
public class User {

    // Unique identifier for the user (UUID format)
    @Id
    private String id;

    // Username for login - must be unique
    @Column(name = "username")
    private String username;

    // Encrypted password field
    @Column(name = "password")
    private String pass;

    // User role for authorization (e.g., ROLE_ADMIN, ROLE_USER)
    @Column(name = "role")
    private String role;
    

    /**
     * Constructor that generates a new UUID for the user.
     * @param username the user's login name
     * @param pass the encrypted password
     * @param role the user's role for authorization
     */
    public User(String username, String pass, String role) {
        this(UUID.randomUUID().toString(), username, pass, role);
    }


    /**
     * Constructor with explicit ID assignment.
     * @param id the user's unique identifier
     * @param username the user's login name
     * @param pass the encrypted password
     * @param role the user's role for authorization
     */
    public User(String id, String username, String pass, String role) {
        this.id = id;
        this.username = username;
        this.pass = pass;
        this.role = role;
    }


    public User() {
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPass() {
        return pass;
    }


    public void setPass(String pass) {
        this.pass = pass;
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }    

    
    
    
}
