package com.skillissue.GLPUinventory.Repository;

import org.springframework.data.repository.CrudRepository;

import com.skillissue.GLPUinventory.Entities.Computer;

public interface ComputerRepository extends CrudRepository<Computer, String> {
    
    
}
