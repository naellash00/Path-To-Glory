package com.example.PathOfGlory.Service;
import com.example.PathOfGlory.ApiResponse.ApiException;
import com.example.PathOfGlory.DTO.OfferingDTO;
import com.example.PathOfGlory.Model.Arena;
import com.example.PathOfGlory.Model.Offering;
import com.example.PathOfGlory.Repository.ArenaRepository;
import com.example.PathOfGlory.Repository.OfferingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferingService { //Renad
    // 1. Declare a dependency for using Dependency Injection
    private final OfferingRepository offeringRepository;

    private final ArenaRepository arenaRepository;

    // 2. CRUD
    // 2.1 GET
    public List<OfferingDTO> getAllOfferings() {
        List<Offering> offerings = offeringRepository.findAll();
        List<OfferingDTO> offeringDTOS = new ArrayList<>();
        for (Offering offering : offerings) {
            OfferingDTO offeringDTO = new OfferingDTO(offering.getName(), offering.getDescription(), offering.getPrice());
            offeringDTOS.add(offeringDTO);
        }
        return offeringDTOS;
    }

    // 2.2 POST
    public void addOffering(Offering offering) {
        offeringRepository.save(offering);
    }

    // 2.3 UPDATE
    public void updateOffering(Integer id, Offering offering) {
        Offering oldOffering = offeringRepository.findOfferingById(id);
        if (oldOffering == null) {
            throw new ApiException("Offering Not Found.");
        }
        oldOffering.setName(offering.getName());
        oldOffering.setDescription(offering.getDescription());
        oldOffering.setPrice(oldOffering.getPrice());
        offeringRepository.save(oldOffering);
    }

    // Assign an offering to an arena
    public void assignOfferingToArena(Integer offeringId, Integer arenaId) {
        Offering offering = offeringRepository.findOfferingById(offeringId);
        Arena arena = arenaRepository.findArenaById(arenaId);

        if (offering == null && arena == null) {
            throw new ApiException("Cant Assign. Offering and Arena Not Found.");
        }
        if (offering == null) {
            throw new ApiException("Cant Assign. Offering Not Found.");
        }
        if (arena == null) {
            throw new ApiException("Cant Assign. Arena Not Found");
        }
        if(!arena.getIsActivated().equalsIgnoreCase("activated")){
            throw new ApiException("Not activated");
        }

        offering.setArena(arena);
        offeringRepository.save(offering);
    }

    // Extra endpoint:
    // Get offerings by price
    public List<Offering> getOfferingsByPriceRange(Double minPrice, Double maxPrice){
        List<Offering> offeringsByPriceRange = offeringRepository.getOfferingsByPriceRange(minPrice,maxPrice);

        if (offeringsByPriceRange.isEmpty()){
            throw new ApiException("There Are No Offerings Within This Price Range.");
        }
        return offeringsByPriceRange;
    }
}