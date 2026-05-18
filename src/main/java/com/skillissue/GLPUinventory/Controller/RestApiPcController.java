package com.skillissue.GLPUinventory.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillissue.GLPUinventory.Entity.Computer;
import com.skillissue.GLPUinventory.Repository.ComputerRepository;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * REST Controller for computer inventory management.
 * All endpoints require ADMIN role authentication.
 * Base path: /api/pc
 */
@RestController
@RequestMapping("/api/pc")
public class RestApiPcController {

    private final ComputerRepository computerRepository;

    /**
     * Constructor with dependency injection of ComputerRepository.
     * @param computerRepository the data access object for Computer entities
     */
    public RestApiPcController(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    /**
     * Retrieves a list of all computer hostnames in the inventory.
     * GET /api/pc
     * @return Iterable of all hostname strings
     */
    @GetMapping
    Iterable<String> getComputer(){
        return computerRepository.findAllHostname();
    }
    
    /**
     * Retrieves detailed information about a specific computer.
     * GET /api/pc/{hostname}
     * @param hostname the unique identifier of the computer
     * @return Optional containing the Computer object if found
     */
    @GetMapping("/{hostname}")
    Optional<Computer> getComputerById(@PathVariable String hostname){
        return computerRepository.findByHostname(hostname);
    }

    /**
     * Creates a new computer record in the inventory.
     * POST /api/pc
     * @param computer the Computer object to be created
     * @return the saved Computer object with any database-generated values
     */
    @PostMapping
    Computer postComputer(@RequestBody Computer computer){
        return computerRepository.save(computer);
    }

    /**
     * Creates or updates a computer record.
     * Returns 201 (CREATED) if new computer, 200 (OK) if updated.
     * PUT /api/pc
     * @param computer the Computer object to be created or updated
     * @return ResponseEntity with appropriate HTTP status and Computer object
     */
    @PutMapping
    ResponseEntity<Computer> createComputer(@RequestBody Computer computer){
        // Check if computer already exists; if not, return CREATED status
        return (!computerRepository.existsByHostname(computer.getHostname()))
            ? new ResponseEntity<>(computerRepository.save(computer), HttpStatus.CREATED)
            : new ResponseEntity<>(computerRepository.save(computer), HttpStatus.OK);
    }


}
