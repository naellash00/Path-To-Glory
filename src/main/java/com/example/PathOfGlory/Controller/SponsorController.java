// Osama Alghamdi
package com.example.PathOfGlory.Controller;
import com.example.PathOfGlory.ApiResponse.ApiResponse;
import com.example.PathOfGlory.Model.Event;
import com.example.PathOfGlory.Model.Sponsor;
import com.example.PathOfGlory.DTO.SponsorDTO;
import com.example.PathOfGlory.DTO.AthleteOutDTO;
import com.example.PathOfGlory.Model.SponsorShip;
import com.example.PathOfGlory.Service.SponsorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sponsor")
public class SponsorController {
    private final SponsorService sponsorService;

    @GetMapping("/get")
    public ResponseEntity getAllSponsors() {
        List<SponsorDTO> sponsorList = sponsorService.getAllSponsor();
        return ResponseEntity.status(200).body(sponsorList);
    }


    @PostMapping("/add")
    public ResponseEntity addSponsor(@RequestBody @Valid Sponsor sponsor) {
        sponsorService.addSponsor(sponsor);
        return ResponseEntity.status(200).body(new ApiResponse("Sponsor added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateSponsor(@PathVariable Integer id, @RequestBody @Valid Sponsor sponsor) {
        sponsorService.updateSponsor(id, sponsor);
        return ResponseEntity.status(200).body(new ApiResponse("Sponsor updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteSponsor(@PathVariable Integer id) {
        sponsorService.deleteSponsor(id);
        return ResponseEntity.status(200).body(new ApiResponse("Sponsor deleted successfully"));
    }

    @PostMapping("/add-sponsorship/{sponsor_id}/{athlete_id}")
    public ResponseEntity sponsorAthlete(@PathVariable Integer sponsor_id,@PathVariable Integer athlete_id,@RequestBody @Valid SponsorShip sponsorShip) {
        sponsorService.sponsorAthlete(sponsor_id,athlete_id,sponsorShip);
        return ResponseEntity.status(200).body(new ApiResponse("Sponsor add sponsorship successfully"));
    }

    @PostMapping("/add-request-event/sponsorId/{sponsorId}/arenaId/{arenaId}")
    public ResponseEntity requestEvent(@PathVariable Integer sponsorId, @PathVariable Integer arenaId, @RequestBody @Valid Event event) {
        sponsorService.requestEvent(sponsorId,arenaId,event);
        return ResponseEntity.status(200).body(new ApiResponse("Sponsor request event successfully"));
    }

    @PutMapping("/accept-event-participate/{sponsor_id}/{eventParticipate_id}")
    public ResponseEntity acceptEventParticipation(@PathVariable Integer sponsor_id,@PathVariable Integer eventParticipate_id) {
        sponsorService.acceptEventParticipation(sponsor_id, eventParticipate_id);
        return ResponseEntity.status(200).body(new ApiResponse("Sponsor accept event participation successfully"));
    }

    @GetMapping("/get-by-achievement-number/{sponsor_id}/{event_number}")
    public ResponseEntity getAllSponsorShipByAchievementNumber(@PathVariable Integer sponsor_id, @PathVariable Integer event_number) {
        List<AthleteOutDTO> athleteOutDTOS = sponsorService.getAllSponsorShipByAchievementNumber(sponsor_id, event_number);
        return ResponseEntity.status(200).body(athleteOutDTOS);
    }

    @PutMapping("/finish-sponsorship/{sponsor_id}/{sponsorShip_id}")
    public ResponseEntity finishSponsorShip(@PathVariable Integer sponsor_id,@PathVariable Integer sponsorShip_id){
        sponsorService.finishSponsorShip(sponsor_id,sponsorShip_id);
        return ResponseEntity.status(200).body(new ApiResponse("Sponsorship is finished"));
    }
}