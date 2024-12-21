package com.example.PathOfGlory.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookCoachOutDTO { // Naelah
    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    private Integer bookingPrice;

    private String athleteName;

    private String coachName;
}