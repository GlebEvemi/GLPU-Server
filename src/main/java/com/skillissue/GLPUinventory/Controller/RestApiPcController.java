package com.skillissue.GLPUinventory.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillissue.GLPUinventory.Entities.Computer;
import com.skillissue.GLPUinventory.Repository.ComputerRepository;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/pc")
public class RestApiPcController {

    private final ComputerRepository computerRepository;

    public RestApiPcController(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    @GetMapping
    Iterable<Computer> getComputer(){
        return computerRepository.findAll();
    }
    

    @GetMapping("/{id}")
    Optional<Computer> getComputerById(@PathVariable String id){
        return computerRepository.findById(id);
    }
    
}
