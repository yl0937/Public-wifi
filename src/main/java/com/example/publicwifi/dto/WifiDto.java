package com.example.publicwifi.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class WifiDto {

    @Id
    @GeneratedValue
    private Long id;
    private String manageNum;
    private String districName;
    private String name;
    private String address;
    private String specificAddress;
    private String floor;
    private String type;
    private String establishment;
    private String classification;
    private String mang;

    private String year;
    private String inOut;
    private String enviroment;

    private double y;
    private double x;

    private LocalDateTime date;
}
