package com.skillissue.GLPUinventory.Entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Computer {
    @Id
    private String id;

    private String hostname;
    private String ipAddress;
    private String cpu;
    private String gpu;
    private String ram;
    private String storage;
    

    public Computer() {
    }
    

    public Computer(String hostname) {
        this(UUID.randomUUID().toString(), hostname, null, null, null, null, null);
    }


    public Computer(String id, String hostname, String ipAddress, String cpu, String gpu, String ram, String storage) {
        this.id = id;
        this.hostname = hostname;
        this.ipAddress = ipAddress;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.storage = storage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    
    
}
