package com.skillissue.GLPUinventory.Entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String pass;

    @Column(name = "role")
    private String role;
    

    public User(String username, String pass, String role) {
        this(UUID.randomUUID().toString(), username, pass, role);
    }


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
