package com.example.PathOfGlory.DTO;
import com.example.PathOfGlory.Model.Athlete;
import com.example.PathOfGlory.Model.Sponsor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventParticipationRequestDTO {// Osama Alghamdi
    private String status;

    private Sponsor sponsor;

    private Athlete athlete;
}
