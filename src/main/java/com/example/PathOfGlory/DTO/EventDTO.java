package com.example.PathOfGlory.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class EventDTO {  //Renad
    private String name;

    private String city;

    private String location;

    private Date startDate;

    private Date endDate;
}