// Osama Alghamdi

package com.example.PathOfGlory.Service;
import com.example.PathOfGlory.DTO.SponsorShipDTO;
import com.example.PathOfGlory.Model.SponsorShip;
import com.example.PathOfGlory.Repository.SponsorShipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SponsorShipService {
    private final SponsorShipRepository sponsorShipRepository;

    public List<SponsorShipDTO> getAllSponsorShips() {
      List<SponsorShip> sponsorShips = sponsorShipRepository.findAll();
      List<SponsorShipDTO> sponsorShipDTOS = new ArrayList<>();
      for (SponsorShip sponsorShip : sponsorShips) {
          SponsorShipDTO sponsorShipDTO = new SponsorShipDTO(sponsorShip.getSponsorShipAmount(),
                  sponsorShip.getStatus(),sponsorShip.getStartDate(),sponsorShip.getEndDate(),
                  sponsorShip.getAthleteSponsor().getName(),sponsorShip.getAthlete().getFullName());
          sponsorShipDTOS.add(sponsorShipDTO);
      }
      return sponsorShipDTOS;
    }
}
