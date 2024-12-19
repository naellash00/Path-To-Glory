package com.example.PathOfGlory.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AthleteOutDTO { // Naelah

    private String fullName;

    private String username;

    private Integer age;

    private String gender;

    private String city;

    private String sportName;

    private List<AchievementOutDTO> achievements;
}
