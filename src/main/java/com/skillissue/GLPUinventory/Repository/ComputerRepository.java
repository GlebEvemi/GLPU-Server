package com.skillissue.GLPUinventory.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.skillissue.GLPUinventory.Entity.Computer;

public interface ComputerRepository extends CrudRepository<Computer, String> {
    
    Optional<Computer> findByHostname(String hostname);

    boolean existsByHostname(String hostname);

    @Query("SELECT c.hostname FROM Computer c ORDER BY c.hostname ASC")
    List<String> findAllHostname();

}