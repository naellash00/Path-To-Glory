package com.example.PathOfGlory.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ArenaDTO {  //Renad
    private String username;

    private String name;

    private String city;

    private String location;

    private List<ServiceDTO> services;

    private List<EventDTO> events;
}