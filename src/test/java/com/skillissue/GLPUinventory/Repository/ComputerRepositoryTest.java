package com.skillissue.GLPUinventory.Repository;

import static org.assertj.core.api.Assertions.*;

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

/**
 * Unit tests for ComputerRepository.
 * Tests database operations for Computer entities.
 * Uses @SpringBootTest with H2 in-memory database for isolation.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("ComputerRepository Tests")
class ComputerRepositoryTest {

    @Autowired
    private ComputerRepository computerRepository;

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
    @DisplayName("Should save a new computer successfully")
    void testSaveComputer() {
        // Act
        Computer savedComputer = computerRepository.save(testComputer);

        // Assert
        assertThat(savedComputer).isNotNull();
        assertThat(savedComputer.getHostname()).isEqualTo("PC-TEST-001");
        assertThat(savedComputer.getOsName()).isEqualTo("Windows");
    }

    @Test
    @DisplayName("Should find computer by hostname")
    void testFindByHostname() {
        // Arrange
        computerRepository.save(testComputer);

        // Act
        Optional<Computer> foundComputer = computerRepository.findByHostname("PC-TEST-001");

        // Assert
        assertThat(foundComputer).isPresent();
        assertThat(foundComputer.get().getHostname()).isEqualTo("PC-TEST-001");
        assertThat(foundComputer.get().getOsName()).isEqualTo("Windows");
    }

    @Test
    @DisplayName("Should return empty when computer not found by hostname")
    void testFindByHostnameNotFound() {
        // Act
        Optional<Computer> foundComputer = computerRepository.findByHostname("NON-EXISTENT");

        // Assert
        assertThat(foundComputer).isEmpty();
    }

    @Test
    @DisplayName("Should check if computer exists by hostname")
    void testExistsByHostname() {
        // Arrange
        computerRepository.save(testComputer);

        // Act & Assert
        assertThat(computerRepository.existsByHostname("PC-TEST-001")).isTrue();
        assertThat(computerRepository.existsByHostname("UNKNOWN")).isFalse();
    }

    @Test
    @DisplayName("Should return all hostnames in ascending order")
    void testFindAllHostname() {
        // Arrange
        Computer computer1 = new Computer("PC-Z", "Windows", "10", "Dell", "Ok", 
            "CORP", "Model1", "Intel i5", 16.0, 512.0, 256.0);
        Computer computer2 = new Computer("PC-A", "Windows", "11", "HP", "Ok", 
            "CORP", "Model2", "Intel i7", 32.0, 1024.0, 512.0);
        Computer computer3 = new Computer("PC-M", "Windows", "10", "Lenovo", "Ok", 
            "CORP", "Model3", "AMD Ryzen", 8.0, 256.0, 128.0);

        computerRepository.save(computer1);
        computerRepository.save(computer2);
        computerRepository.save(computer3);

        // Act
        List<String> hostnames = computerRepository.findAllHostname();

        // Assert
        assertThat(hostnames).hasSize(3);
        assertThat(hostnames).containsExactly("PC-A", "PC-M", "PC-Z");
    }

    @Test
    @DisplayName("Should update an existing computer")
    void testUpdateComputer() {
        // Arrange
        computerRepository.save(testComputer);

        // Act
        testComputer.setOsVersion("12");
        testComputer.setTotalRam(64.0);
        Computer updatedComputer = computerRepository.save(testComputer);

        // Assert
        assertThat(updatedComputer.getOsVersion()).isEqualTo("12");
        assertThat(updatedComputer.getTotalRam()).isEqualTo(64.0);
    }

    @Test
    @DisplayName("Should delete a computer by hostname")
    void testDeleteComputer() {
        // Arrange
        computerRepository.save(testComputer);
        assertThat(computerRepository.existsByHostname("PC-TEST-001")).isTrue();

        // Act
        computerRepository.deleteById("PC-TEST-001");

        // Assert
        assertThat(computerRepository.existsByHostname("PC-TEST-001")).isFalse();
    }

    @Test
    @DisplayName("Should return empty list when no computers exist")
    void testFindAllHostnameEmpty() {
        // Act
        List<String> hostnames = computerRepository.findAllHostname();

        // Assert
        assertThat(hostnames).isEmpty();
    }

}
