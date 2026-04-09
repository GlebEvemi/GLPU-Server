package com.skillissue.GLPUinventory.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.skillissue.GLPUinventory.Entity.Computer;

public interface ComputerRepository extends CrudRepository<Computer, String> {
    
    Optional<Computer> findByHostname(String hostname);

}