package com.example.PathOfGlory.Controller;

import com.example.PathOfGlory.ApiResponse.ApiResponse;
import com.example.PathOfGlory.DTO.ArenaDTO;
import com.example.PathOfGlory.DTO.EventDTO;
import com.example.PathOfGlory.DTO.SponsorDTO;
import com.example.PathOfGlory.Service.SponsorService;
import com.example.PathOfGlory.Model.Achievement;
import com.example.PathOfGlory.Model.Athlete;
import com.example.PathOfGlory.Model.BookCoach;
import com.example.PathOfGlory.Model.SponsorShip;
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
    private final BookServiceService bookServiceService;
    private final EventService eventService;
    private final ArenaService arenaService;
    private final SponsorService sponsorService;

    // CRUD
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

    // Extra endpoints:
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

    @PostMapping("/send/teammate/request/from/{sender_athlete_id}/to/{receiver_athlete_id}")
    public ResponseEntity sendTeammateRequest(@PathVariable Integer sender_athlete_id, @PathVariable Integer receiver_athlete_id) {
        athleteService.sendTeammateRequest(sender_athlete_id, receiver_athlete_id);
        return ResponseEntity.status(200).body(new ApiResponse("Teammate Request sent successfully"));
    }

    @GetMapping("/get/teammate/requests")
    public ResponseEntity getAllTeammateRequests() {
        return ResponseEntity.status(200).body(athleteService.getAllTeammateRequests());
    }

    @PostMapping("/add-sponsorship/{sponsor_id}/{athlete_id}")
    public ResponseEntity sponsorAthlete(@PathVariable Integer sponsor_id,@PathVariable Integer athlete_id,@RequestBody @Valid SponsorShip sponsorShip) {
        athleteService.sponsorShipRequest(sponsor_id,athlete_id,sponsorShip);
        return ResponseEntity.status(200).body(new ApiResponse("Sponsor add sponsorship successfully"));
    }

    @PutMapping("/receiver-athlete/{receiver_athlete_id}/respond-to/teammate-request/{teammate_request_id}/{status}")
    public ResponseEntity respondToTeammateRequest(@PathVariable Integer receiver_athlete_id, @PathVariable Integer teammate_request_id, @PathVariable String status){
       athleteService.respondToTeammateRequest(receiver_athlete_id, teammate_request_id, status);
        return ResponseEntity.status(200).body(new ApiResponse("Response to teammate request sent successfully"));
    }

    @PostMapping("/send-participate-request/athlete_id/{athlete_id}/eventNumber/{eventNumber}")
    public ResponseEntity requestParticipate(@PathVariable Integer athlete_id,@PathVariable Integer eventNumber) {
        athleteService.requestParticipateInEvent(athlete_id, eventNumber);
        return ResponseEntity.status(200).body(new ApiResponse("sent participation request successfully"));
    }

    //Renad
    @PostMapping("/bookService/serviceId/{serviceId}/athleteId/{athleteId}/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity bookService(@PathVariable Integer serviceId, @PathVariable Integer athleteId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  Date startDate, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  Date endDate) {
        bookServiceService.bookService(serviceId, athleteId, startDate, endDate);
        return ResponseEntity.status(200).body(new ApiResponse("Booking Completed."));
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

    // Renad
    @GetMapping("/getEventsByDateRange/athleteId/{athleteId}/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity getEventsByDateRange(@PathVariable Integer athleteId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  Date endDate) {
        List<EventDTO> eventsByDateRange = eventService.getEventsByDateRange(athleteId,startDate, endDate);
        return ResponseEntity.status(200).body(eventsByDateRange);
    }

    //Renad
    @GetMapping("/searchArenasByAthleteCity/athleteId/{athleteId}")
    public ResponseEntity searchArenasByAthleteCity(@PathVariable Integer athleteId) {
        List<ArenaDTO> arenaDTOS = arenaService.searchArenasByAthleteCity(athleteId);
        return ResponseEntity.status(200).body(arenaDTOS);
    }

    // Renad
    @PutMapping("/handleSponsorshipRequest/athleteId/{athleteId}/sponsorshipId/{sponsorshipId}/isAccepted/{isAccepted}")
    public ResponseEntity handleSponsorshipRequest(@PathVariable Integer athleteId, @PathVariable Integer sponsorshipId,@PathVariable Boolean isAccepted ) {
        athleteService.handleSponsorshipRequest(athleteId,sponsorshipId,isAccepted);
        return ResponseEntity.status(200).body(new ApiResponse("Request Handled."));
    }

    @GetMapping("/get-by-city/{city}")
    public ResponseEntity getSponsorsByCity(@PathVariable String city) {
        List<SponsorDTO> sponsorDTOS = athleteService.getSponsorsByCity(city);
        return ResponseEntity.status(200).body(sponsorDTOS);
    }
}