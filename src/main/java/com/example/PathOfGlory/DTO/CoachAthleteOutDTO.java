package com.example.PathOfGlory.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoachAthleteOutDTO { // Naelah
    // this dto is for the get coach athletes endpoint
    // to return less data for athlete
    private String fullName;

    private String username;

    private String sportName;
}
