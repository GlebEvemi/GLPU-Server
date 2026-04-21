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


@RestController
@RequestMapping("/api/pc")
public class RestApiPcController {

    private final ComputerRepository computerRepository;

    public RestApiPcController(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    @GetMapping
    String getComputer(){
        return computerRepository.findAllByHostname();
    }
    

    @GetMapping("/{hostname}")
    Optional<Computer> getComputerById(@PathVariable String hostname){
        return computerRepository.findByHostname(hostname);
    }

    @PostMapping
    Computer postComputer(@RequestBody Computer computer){
        return computerRepository.save(computer);
    }

    // Create or update a computer
    @PutMapping("/{hostname}")
    ResponseEntity<Computer> createComputer(@PathVariable String hostname, @RequestBody Computer computer){
        
        return (!computerRepository.existByHostname(hostname))
            ? new ResponseEntity<>(computerRepository.save(computer), HttpStatus.CREATED)
            : new ResponseEntity<>(computerRepository.save(computer), HttpStatus.OK);
    }


}
