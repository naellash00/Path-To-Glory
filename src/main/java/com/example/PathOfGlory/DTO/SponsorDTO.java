package com.example.PathOfGlory.DTO;
import com.example.PathOfGlory.Model.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SponsorDTO { // Osama Alghamdi
    private String name;

    private String phoneNumber;

    private String email;

    private String city;

    private String certificateRecord;

    private Set<SponsorShip> sponsorShipList;
}