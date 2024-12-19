package com.example.PathOfGlory.Controller;

import com.example.PathOfGlory.ApiResponse.ApiResponse;
import com.example.PathOfGlory.DTO.ArenaDTO;
import com.example.PathOfGlory.DTO.EventDTO;
import com.example.PathOfGlory.Model.Achievement;
import com.example.PathOfGlory.Model.Athlete;
import com.example.PathOfGlory.Model.BookCoach;
import com.example.PathOfGlory.Repository.ArenaRepository;
import com.example.PathOfGlory.Service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/athlete")
public class AthleteController { // Naelah
    private final AthleteService athleteService;
    private final BookCoachService bookCoachService;
    private final BookOfferingService bookOfferingService;
    private final EventService eventService;
    private final ArenaRepository arenaRepository;
    private final ArenaService arenaService;

    @GetMapping("/get")
    public ResponseEntity getAllAthletes() {
        return ResponseEntity.status(200).body(athleteService.getAllAthletes());
    }

    @PostMapping("/add")
    public ResponseEntity addAthlete(@RequestBody @Valid Athlete athlete) {
        athleteService.addAthlete(athlete);
        return ResponseEntity.status(200).body(new ApiResponse("Athlete Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateAthlete(@PathVariable Integer id, @RequestBody @Valid Athlete athlete) {
        athleteService.updateAthlete(id, athlete);
        return ResponseEntity.status(200).body(new ApiResponse("Athlete Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAthlete(@PathVariable Integer id) {
        athleteService.deleteAthlete(id);
        return ResponseEntity.status(200).body(new ApiResponse("Athlete Deleted Successfully"));
    }

    @PostMapping("/request/coach/booking/{athlete_id}/{coach_username}")
    public ResponseEntity requestCoachBooking(@PathVariable Integer athlete_id, @PathVariable String coach_username, @RequestBody @Valid BookCoach booking) {
        bookCoachService.requestCoachBooking(athlete_id, coach_username, booking);
        return ResponseEntity.status(200).body(new ApiResponse("Booking Coach Request Created Successfully"));
    }

    @PostMapping("/add/achievement/{athlete_id}")
    public ResponseEntity addAchievement(@PathVariable Integer athlete_id, @RequestBody @Valid Achievement achievement) {
        athleteService.addAchievement(athlete_id, achievement);
        return ResponseEntity.status(200).body(new ApiResponse("Achievement Added Successfully"));
    }

    @GetMapping("/get/athlete/achievements/{athlete_id}")
    public ResponseEntity getAthleteAchievements(@PathVariable Integer athlete_id) {
        return ResponseEntity.status(200).body(athleteService.getAthleteAchievements(athlete_id));
    }

    @DeleteMapping("/delete/achievement/{achievement_id}")
    public ResponseEntity deleteAchievement(@PathVariable Integer achievement_id) {
        athleteService.deleteAchievement(achievement_id);
        return ResponseEntity.status(200).body(new ApiResponse("Achievement deleted successfully"));
    }

    @GetMapping("/get/same/sport/{sport_name}/and-city/{city}/athletes")
    public ResponseEntity findSameSportAndCityAthletes(@PathVariable String sport_name, @PathVariable String city) {
        return ResponseEntity.status(200).body(athleteService.findSameSportAndCityAthletes(sport_name, city));
    }

    @PostMapping("/send/teammate/request/from/{sender_athlete_id}/to/{receiver_athlete_username}")
    public ResponseEntity sendTeammateRequest(@PathVariable Integer sender_athlete_id, @PathVariable String receiver_athlete_username) {
        athleteService.sendTeammateRequest(sender_athlete_id, receiver_athlete_username);
        return ResponseEntity.status(200).body(new ApiResponse("Teammate Request sent successfully"));
    }

    @GetMapping("/get/teammate/requests")
    public ResponseEntity getAllTeammateRequests() {
        return ResponseEntity.status(200).body(athleteService.getAllTeammateRequests());
    }

    @PutMapping("/accept/{athlete_id}/sponsorship/{sponsorship_id}")
    public ResponseEntity acceptSponsorship(@PathVariable Integer athlete_id, @PathVariable Integer sponsorship_id){
        athleteService.acceptSponsorship(athlete_id, sponsorship_id);
        return ResponseEntity.status(200).body(new ApiResponse("SponsorShip Accepted Successfully"));
    }

    @PutMapping("/reject/{athlete_id}/sponsorship/{sponsorship_id}")
    public ResponseEntity rejectSponsorShip(@PathVariable Integer athlete_id, @PathVariable Integer sponsorship_id){
        athleteService.rejectSponsorShip(athlete_id, sponsorship_id);
        return ResponseEntity.status(200).body(new ApiResponse("SponsorShip Rejected"));

    }

    @PutMapping("/receiver-athlete/{receiver_athlete_id}/respond-to/teammate-request/{teammate_request_id}/{status}")
    public ResponseEntity respondToTeammateRequest(@PathVariable Integer receiver_athlete_id, @PathVariable Integer teammate_request_id, @PathVariable String status){
       athleteService.respondToTeammateRequest(receiver_athlete_id, teammate_request_id, status);
        return ResponseEntity.status(200).body(new ApiResponse("Response to teammate request sent successfully"));
    }

    @PostMapping("/bookOffering/offeringId/{offeringId}/athleteId/{athleteId}/startDate/{startDate}/endDate/{endDate}")  //Renad
    public ResponseEntity book(@PathVariable Integer offeringId, @PathVariable Integer athleteId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  Date startDate, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  Date endDate) {
        bookOfferingService.bookOffering(offeringId, athleteId, startDate, endDate);
        return ResponseEntity.status(200).body(new ApiResponse("Booking Completed."));
    }

    @PostMapping("/send-participate-request/athlete_id/{athlete_id}/eventNumber/{eventNumber}")
    public ResponseEntity requestParticipate(@PathVariable Integer athlete_id,@PathVariable Integer eventNumber) {
        athleteService.requestParticipateInEvent(athlete_id, eventNumber);
        return ResponseEntity.status(200).body(new ApiResponse("sent participation request successfully"));
    }

    //Renad
    @PutMapping("/cancelEventParticipation/athlete_id/{athlete_id}/eventNumber/{eventNumber}")
    public ResponseEntity cancelEventParticipation(@PathVariable Integer athlete_id,@PathVariable Integer eventNumber) {
        athleteService.cancelEventParticipation(athlete_id, eventNumber);
        return ResponseEntity.status(200).body(new ApiResponse("Participation Has Been Canceled successfully."));
    }

    //Renad
    @GetMapping("/athleteId/{athleteId}/sponsorId/{sponsorId}/get-upcoming-events")
    public ResponseEntity searchUpcomingEventsForSponsor(@PathVariable Integer athleteId, @PathVariable Integer sponsorId) {
        List<EventDTO> upcomingEvents = eventService.searchUpcomingEventsForSponsor(athleteId,sponsorId);
        return ResponseEntity.status(200).body(upcomingEvents);
    }

    //Renad
    @GetMapping("/searchArenasByAthleteCity/athleteId/{athleteId}")
    public ResponseEntity searchArenasByAthleteCity(@PathVariable Integer athleteId) {
        List<ArenaDTO> arenaDTOS = arenaService.searchArenaByCity(athleteId);
        return ResponseEntity.status(200).body(arenaDTOS);
    }
}