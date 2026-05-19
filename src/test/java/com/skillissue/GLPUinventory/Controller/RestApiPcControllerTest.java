package com.skillissue.GLPUinventory.Controller;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.skillissue.GLPUinventory.Entity.Computer;
import com.skillissue.GLPUinventory.Repository.ComputerRepository;

/**
 * Unit tests for RestApiPcController.
 * Tests REST API endpoints and business logic.
 * Uses @SpringBootTest with H2 in-memory database for isolation.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("RestApiPcController Tests")
class RestApiPcControllerTest {

    @Autowired
    private ComputerRepository computerRepository;

    @Autowired
    private RestApiPcController restApiPcController;

    private Computer testComputer;

    /**
     * Setup method - runs before each test.
     * Creates a test Computer object for use in tests.
     */
    @BeforeEach
    void setUp() {
        testComputer = new Computer(
            "PC-TEST-001",
            "Windows",
            "11",
            "Dell",
            "Ok",
            "CORPORATE",
            "OptiPlex 7090",
            "Intel Core i7",
            32.0,
            1024.0,
            512.0
        );
    }

    @Test
    @DisplayName("Should retrieve all computer hostnames")
    void testGetAllComputers() {
        // Arrange - save multiple computers
        Computer computer1 = new Computer("PC-001", "Windows", "10", "Dell", "Ok",
            "CORP", "Model1", "Intel i5", 16.0, 512.0, 256.0);
        Computer computer2 = new Computer("PC-002", "Windows", "11", "HP", "Ok",
            "CORP", "Model2", "Intel i7", 32.0, 1024.0, 512.0);

        computerRepository.save(computer1);
        computerRepository.save(computer2);

        // Act
        Iterable<String> hostnamesIterable = restApiPcController.getComputer();
        List<String> hostnames = new ArrayList<>();
        hostnamesIterable.forEach(hostnames::add);

        // Assert
        assertThat(hostnames).hasSize(2);
        assertThat(hostnames).contains("PC-001", "PC-002");
    }

    @Test
    @DisplayName("Should retrieve computer by hostname")
    void testGetComputerById() {
        // Arrange
        computerRepository.save(testComputer);

        // Act
        Optional<Computer> foundComputer = restApiPcController.getComputerById("PC-TEST-001");

        // Assert
        assertThat(foundComputer).isPresent();
        assertThat(foundComputer.get().getHostname()).isEqualTo("PC-TEST-001");
        assertThat(foundComputer.get().getOsName()).isEqualTo("Windows");
    }

    @Test
    @DisplayName("Should return empty when computer not found")
    void testGetComputerByIdNotFound() {
        // Act
        Optional<Computer> foundComputer = restApiPcController.getComputerById("NON-EXISTENT");

        // Assert
        assertThat(foundComputer).isEmpty();
    }

    @Test
    @DisplayName("Should create a new computer via POST")
    void testPostComputer() {
        // Arrange
        Computer newComputer = new Computer(
            "PC-NEW-001",
            "Windows",
            "10",
            "HP",
            "Ok",
            "CORP",
            "Model123",
            "Intel i5",
            16.0,
            512.0,
            256.0
        );

        // Act
        Computer savedComputer = restApiPcController.postComputer(newComputer);

        // Assert
        assertThat(savedComputer).isNotNull();
        assertThat(savedComputer.getHostname()).isEqualTo("PC-NEW-001");
        assertThat(computerRepository.existsByHostname("PC-NEW-001")).isTrue();
    }

    @Test
    @DisplayName("Should create new computer via PUT - returns CREATED status")
    void testPutComputerCreated() {
        // Arrange
        Computer newComputer = new Computer(
            "PC-PUT-NEW",
            "Linux",
            "20.04",
            "ASRock",
            "Ok",
            "CORP",
            "Model456",
            "AMD Ryzen",
            16.0,
            256.0,
            128.0
        );

        // Act
        var response = restApiPcController.createComputer(newComputer);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(201); // CREATED
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getHostname()).isEqualTo("PC-PUT-NEW");
        assertThat(computerRepository.existsByHostname("PC-PUT-NEW")).isTrue();
    }

    @Test
    @DisplayName("Should update existing computer via PUT - returns OK status")
    void testPutComputerUpdated() {
        // Arrange
        computerRepository.save(testComputer);

        Computer updatedComputer = new Computer(
            "PC-TEST-001",
            "Windows",
            "12",
            "Dell",
            "Ok",
            "CORPORATE",
            "OptiPlex 7090",
            "Intel Core i9",
            64.0,
            2048.0,
            1024.0
        );

        // Act
        var response = restApiPcController.createComputer(updatedComputer);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200); // OK
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTotalRam()).isEqualTo(64.0);
        assertThat(computerRepository.findByHostname("PC-TEST-001").get().getTotalRam()).isEqualTo(64.0);
    }

    @Test
    @DisplayName("Should handle multiple operations correctly")
    void testMultipleOperations() {
        // Act - Create multiple computers
        Computer comp1 = new Computer("COMP-A", "Windows", "11", "Dell", "Ok",
            "CORP", "M1", "i7", 32.0, 1024.0, 512.0);
        Computer comp2 = new Computer("COMP-B", "Windows", "10", "HP", "Ok",
            "CORP", "M2", "i5", 16.0, 512.0, 256.0);

        restApiPcController.postComputer(comp1);
        restApiPcController.postComputer(comp2);

        // Act - Get all
        Iterable<String> hostnamesIterable = restApiPcController.getComputer();
        List<String> hostnames = new ArrayList<>();
        hostnamesIterable.forEach(hostnames::add);

        // Assert
        assertThat(hostnames).hasSize(2);
        assertThat(hostnames).contains("COMP-A", "COMP-B");

        // Act - Get by ID
        Optional<Computer> retrieved = restApiPcController.getComputerById("COMP-A");
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getOsVersion()).isEqualTo("11");
    }

}
