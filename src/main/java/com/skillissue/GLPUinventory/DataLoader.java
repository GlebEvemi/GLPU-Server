package com.skillissue.GLPUinventory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.skillissue.GLPUinventory.Entities.Computer;
import com.skillissue.GLPUinventory.Repository.ComputerRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
    private final ComputerRepository computerRepository;

    public DataLoader(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    @PostConstruct
    private void loadData(){
        computerRepository.saveAll(List.of(
            new Computer("PC-IT-GLEB"),
            new Computer("PC-IT-TOLIK"),
            new Computer("PC-IT-MISHA"),
            new Computer("PC-IT-ANDREI"),
            new Computer("PC-IT-IGOR")
        ));
    }
    
}
