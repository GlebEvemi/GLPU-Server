package com.skillissue.GLPUinventory.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.skillissue.GLPUinventory.Entity.Computer;

/**
 * Repository interface for Computer entity database operations.
 * Extends CrudRepository for basic CRUD functionality and adds custom query methods.
 */
public interface ComputerRepository extends CrudRepository<Computer, String> {
    
    /**
     * Finds a computer by its hostname.
     * @param hostname the unique identifier of the computer
     * @return Optional containing the Computer if found
     */
    Optional<Computer> findByHostname(String hostname);

    /**
     * Checks if a computer with the given hostname exists in the database.
     * @param hostname the hostname to check
     * @return true if computer exists, false otherwise
     */
    boolean existsByHostname(String hostname);

    /**
     * Retrieves all computer hostnames sorted alphabetically.
     * Used for listing available computers without loading full entities.
     * @return List of hostname strings in ascending order
     */
    @Query("SELECT c.hostname FROM Computer c ORDER BY c.hostname ASC")
    List<String> findAllHostname();

}