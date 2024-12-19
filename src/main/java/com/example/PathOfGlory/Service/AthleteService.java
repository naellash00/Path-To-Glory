package com.example.PathOfGlory.Service;

import com.example.PathOfGlory.ApiResponse.ApiException;
import com.example.PathOfGlory.DTO.AchievementOutDTO;
import com.example.PathOfGlory.DTO.AthleteOutDTO;
import com.example.PathOfGlory.DTO.TeammateRequestOutDTO;
import com.example.PathOfGlory.Model.*;
import com.example.PathOfGlory.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AthleteService { // Naelah

    private final AchievementRepository achievementRepository;
    private final AthleteRepository athleteRepository;
    private final TeammateRequestRepository teammateRequestRepository;
    private final SponsorShipRepository sponsorShipRepository;
    private final OfferingRepository offeringRepository;
    private final BookOfferingRepository bookOfferingRepository;
    private final EventRepository eventRepository;
    private final EventParticipationRequestRepository eventParticipationRequestRepository;
    private final EventHeldRequestRepository eventHeldRequestRepository;


//    private final AchievementRepository achievementRepository;
//    private final TeammateRequestRepository teammateRequestRepository;
//    public List<Athlete> getAllAthletes(){
//        return athleteRepository.findAll();
//    }

    public List<AthleteOutDTO> getAllAthletes() {
        List<Athlete> athletes = athleteRepository.findAll();
        List<AthleteOutDTO> athleteOutDTOS = new ArrayList<>();
        for (Athlete a : athletes) {
            // to conver achivement set to dto list
            List<AchievementOutDTO> achievementOutDTOS = new ArrayList<>();

            for (Achievement achievement : a.getAchievements()) {
                achievementOutDTOS.add(new AchievementOutDTO(achievement.getTitle(), achievement.getDescription()));
            }

            AthleteOutDTO athleteOutDTO = new AthleteOutDTO(a.getFullName(), a.getUsername(), a.getAge(), a.getGender(), a.getCity(), a.getSportName(), achievementOutDTOS);
            athleteOutDTOS.add(athleteOutDTO);
        }
        return athleteOutDTOS;
    }

    public void addAthlete(Athlete athlete) {
        athleteRepository.save(athlete);
    }

    public void updateAthlete(Integer id, Athlete athlete) {
        Athlete oldAthlete = athleteRepository.findAthleteById(id);
        if (oldAthlete == null) {
            throw new ApiException("athlete not found");
        }
        oldAthlete.setFullName(athlete.getFullName());
        oldAthlete.setUsername(athlete.getUsername());
        oldAthlete.setPhoneNumber(athlete.getPhoneNumber());
        oldAthlete.setEmail(athlete.getEmail());
        oldAthlete.setAge(athlete.getAge());
        athleteRepository.save(oldAthlete);
    }

    public void deleteAthlete(Integer id) {
        Athlete athlete = athleteRepository.findAthleteById(id);
        if (athlete == null) {
            throw new ApiException("athlete not found");
        }
        athleteRepository.delete(athlete);
    }

    // extra endpoint 4 - Naelah
    public void addAchievement(Integer athlete_id, Achievement achievement) {
        // check if athlete id valid
        Athlete athlete = athleteRepository.findAthleteById(athlete_id);
        if (athlete == null) {
            throw new ApiException("athlete not found");
        }
        achievement.setAthlete(athlete);
        achievementRepository.save(achievement);
    }

    //extra endpoint 5 - Naelah
    public List<AchievementOutDTO> getAthleteAchievements(Integer athlete_id) {
        // check athlete if
        Athlete athlete = athleteRepository.findAthleteById(athlete_id);
        if (athlete == null) {
            throw new ApiException("athlete not found");
        }
        List<Achievement> athleteAchievements = achievementRepository.findAchievementByAthlete_Id(athlete_id);
        List<AchievementOutDTO> achievementOutDTOS = new ArrayList<>();
        for (Achievement a : athleteAchievements) {
            AchievementOutDTO achievementOutDTO = new AchievementOutDTO(a.getTitle(), a.getDescription());
            achievementOutDTOS.add(achievementOutDTO);
        }
        return achievementOutDTOS;
    }

    public void deleteAchievement(Integer achievement_id) {
        Achievement achievement = achievementRepository.findAchievementById(achievement_id);
        if (achievement == null) {
            throw new ApiException("achievement not found");
        }
        achievementRepository.delete(achievement);
    }

    // extra endpoint 6 - Naelah
    public List<AthleteOutDTO> findSameSportAndCityAthletes(String sport_name, String city) {
        List<Athlete> sameSportAndCityAthletes = athleteRepository.findAthleteBySportNameAndCity(sport_name, city);
        List<AthleteOutDTO> sameSportAndCityAthletesOutDTOS = new ArrayList<>();
        for (Athlete a : sameSportAndCityAthletes) {
            // achivement out dto
            List<AchievementOutDTO> achievementOutDTOS = new ArrayList<>();
            for (Achievement achievement : a.getAchievements()) {
                achievementOutDTOS.add(new AchievementOutDTO(achievement.getTitle(), achievement.getDescription()));
            }

            AthleteOutDTO athleteOutDTO = new AthleteOutDTO(a.getFullName(), a.getUsername(), a.getAge(), a.getGender(), a.getCity(), a.getSportName(), achievementOutDTOS);
            sameSportAndCityAthletesOutDTOS.add(athleteOutDTO);
        }
        return sameSportAndCityAthletesOutDTOS;
    }

    // extra endpoint 7 - Naelah
    public void sendTeammateRequest(Integer sender_athlete_id, String receiver_athlete_username) {
        // check both athlete exist
        Athlete senderAthlete = athleteRepository.findAthleteById(sender_athlete_id);
        Athlete receiverAthlete = athleteRepository.findAthleteByUsername(receiver_athlete_username);
        if (senderAthlete == null || receiverAthlete == null) {
            throw new ApiException("Athlete Not Found");
        }
        // check they are in the same city and same sport
        if (!senderAthlete.getSportName().equalsIgnoreCase(receiverAthlete.getSportName())) {
            throw new ApiException("cannot send teammate request. sport filed are different");
        }

        if (!senderAthlete.getCity().equalsIgnoreCase(receiverAthlete.getCity())) {
            throw new ApiException("Athlete not in the same city");
        }

        // check if request been made before
        TeammateRequest existingRequest = teammateRequestRepository.findTeammateRequestBySenderAndReceiver(senderAthlete, receiverAthlete);
        if (existingRequest != null && existingRequest.getStatus().equalsIgnoreCase("requested")) {
            throw new ApiException("Teammate request been made before");
        }

        TeammateRequest teammateRequest = new TeammateRequest();
        teammateRequest.setSender(senderAthlete);
        teammateRequest.setReceiver(receiverAthlete);
        teammateRequest.setRequestDate(LocalDateTime.now());
        teammateRequest.setStatus("requested");
        teammateRequestRepository.save(teammateRequest);
    }

//    public List<TeammateRequest> getAllTeammateRequests() {
//
//        return teammateRequestRepository.findAll();
//    }

   public List<TeammateRequestOutDTO> getAllTeammateRequests(){
        List<TeammateRequest> teammateRequests = teammateRequestRepository.findAll();
        List<TeammateRequestOutDTO> teammateRequestOutDTOS = new ArrayList<>();
        for(TeammateRequest t : teammateRequests){
            TeammateRequestOutDTO teammateRequestOutDTO = new TeammateRequestOutDTO(t.getSender().getFullName(), t.getSender().getUsername(), t.getReceiver().getFullName(), t.getReceiver().getUsername(), t.getStatus(), t.getRequestDate(), t.getResponseDate());
            teammateRequestOutDTOS.add(teammateRequestOutDTO);
        }
        return teammateRequestOutDTOS;
   }

    //accept sponsorship request from sponsor
    // extra endpoint 8 - Naelah
    public void acceptSponsorship(Integer athlete_id, Integer sponsorship_id) {
        // check athlete id and sponsorship id and
        // check if this athlete is the same one in the request
        Athlete athlete = athleteRepository.findAthleteById(athlete_id);
        SponsorShip sponsorShip = sponsorShipRepository.findSponsorShipById(sponsorship_id);
        if (athlete == null) {
            throw new ApiException("athlete not found");
        }
        if (sponsorShip == null) {
            throw new ApiException("sponsorship not found");
        }
        // check if its bending --> if the request is made
        if (!(sponsorShip != null && sponsorShip.getStatus().equalsIgnoreCase("pending"))) {
            throw new ApiException("sponsorship status not requested");
        }
        // check if it has the same athlete
        if (!sponsorShip.getAthlete().getId().equals(athlete.getId())) {
            throw new ApiException("Cannot accept this request. not the same athlete");
        }
        //sponsorShip.setAthlete(athlete);
        sponsorShip.setStatus("accepted");
        sponsorShipRepository.save(sponsorShip);
    }

    //extra endpoint 8 - Naelah
    public void rejectSponsorShip(Integer athlete_id, Integer sponsorship_id) {
        Athlete athlete = athleteRepository.findAthleteById(athlete_id);
        SponsorShip sponsorShip = sponsorShipRepository.findSponsorShipById(sponsorship_id);
        if (athlete == null) {
            throw new ApiException("athlete not found");
        }
        if (sponsorShip == null) {
            throw new ApiException("sponsorship not found");
        }
        // check if its bending --> if the request is made
        if (!(sponsorShip != null && sponsorShip.getStatus().equalsIgnoreCase("pending"))) {
            throw new ApiException("sponsorship status not requested");
        }
        // check if it has the same athlete
        if (!sponsorShip.getAthlete().getId().equals(athlete.getId())) {
            throw new ApiException("Cannot reject this request. not the same athlete");
        }
        sponsorShip.setStatus("rejected");
        sponsorShipRepository.save(sponsorShip);
    }

    // get all athlete sponsor ship
    // accept teammate request
    //extra endpoint 10 - Naelah
    public void respondToTeammateRequest(Integer receiver_athlete_id, Integer teammate_request_id, String status){
      // check reciver athlete and check request and check reciver is the same one in the request
        Athlete athlete = athleteRepository.findAthleteById(receiver_athlete_id);
        TeammateRequest teammateRequest = teammateRequestRepository.findTeammateRequestById(teammate_request_id);
        if(athlete == null){
            throw new ApiException("athlete not found");
        }
        // check request exist
        if(teammateRequest == null){
            throw new ApiException("teammate request not found");
        }
        // check its the same athlete in the request
        if(!(teammateRequest.getReceiver().getId().equals(athlete.getId()))){
            throw new ApiException("athlete cannot accept this teammate request");
        }
        if (!status.equalsIgnoreCase("accepted") && !status.equalsIgnoreCase("rejected")) {
            throw new ApiException("Invalid status");
        }
        teammateRequest.setStatus(status.toLowerCase());
        teammateRequest.setResponseDate(LocalDateTime.now());
        teammateRequestRepository.save(teammateRequest);
    }

    // Osama alghamdi
    public void requestParticipateInEvent(Integer athlete_id,Integer eventNumber){
        Athlete athlete = athleteRepository.findAthleteById(athlete_id);
        Event event = eventRepository.findEventByNumber(eventNumber);
        if (athlete == null || event == null){
            throw new ApiException("athlete or event not found");
        }
        EventParticipationRequest requestParticipate = new EventParticipationRequest();
        requestParticipate.setAthlete(athlete);
        requestParticipate.setSponsor(event.getSponsor());
        requestParticipate.setStatus("pending");
        eventParticipationRequestRepository.save(requestParticipate);
    }

    // Renad
    // Allow participants to cancel their participation in an event
    public void cancelEventParticipation(Integer athlete_id,Integer eventNumber){
        Athlete athlete = athleteRepository.findAthleteById(athlete_id);
        Event event = eventRepository.findEventByNumber(eventNumber);
        if (athlete == null || event == null){
            throw new ApiException("athlete or event not found");
        }
        // Find the athlete's event participation request
        EventParticipationRequest eventParticipationRequest = eventParticipationRequestRepository.findEventParticipationRequestByAthleteId(athlete_id);

        // If the participation request exists and is accepted, cancel it
        if (eventParticipationRequest != null && eventParticipationRequest.getStatus().equals("accepted")) {
            eventParticipationRequest.setStatus("canceled");
            eventParticipationRequestRepository.save(eventParticipationRequest); // Save the updated status
        } else if (eventParticipationRequest == null) {
            throw new ApiException("Participation request not found for the athlete in this event.");
        }
    }
}