package com.example.PathOfGlory.Service;

import com.example.PathOfGlory.ApiResponse.ApiException;
import com.example.PathOfGlory.DTO.AchievementOutDTO;
import com.example.PathOfGlory.DTO.AthleteOutDTO;
import com.example.PathOfGlory.DTO.SponsorDTO;
import com.example.PathOfGlory.Repository.SponsorRepository;
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
    private final EventRepository eventRepository;
    private final EventParticipationRequestRepository eventParticipationRequestRepository;
    private final SponsorRepository sponsorRepository;


    public List<AthleteOutDTO> getAllAthletes() {
        List<Athlete> athletes = athleteRepository.findAll();
        List<AthleteOutDTO> athleteOutDTOS = new ArrayList<>();
        for (Athlete a : athletes) {
            // to conver achivement set to dto list
            List<AchievementOutDTO> achievementOutDTOS = new ArrayList<>();

            for (Achievement achievement : a.getAchievements()) {
                achievementOutDTOS.add(new AchievementOutDTO(achievement.getTitle(), achievement.getDescription()));
            }

            AthleteOutDTO athleteOutDTO = new AthleteOutDTO(a.getFullName(), a.getUsername(), a.getAge(), a.getGender(), a.getCity(), a.getSportType(), achievementOutDTOS);
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
    public List<AthleteOutDTO> findSameSportAndCityAthletes(String sportType, String city) {
        List<Athlete> sameSportAndCityAthletes = athleteRepository.findAthleteBySportTypeAndCity(sportType, city);
        List<AthleteOutDTO> sameSportAndCityAthletesOutDTOS = new ArrayList<>();
        for (Athlete a : sameSportAndCityAthletes) {
            // achivement out dto
            List<AchievementOutDTO> achievementOutDTOS = new ArrayList<>();
            for (Achievement achievement : a.getAchievements()) {
                achievementOutDTOS.add(new AchievementOutDTO(achievement.getTitle(), achievement.getDescription()));
            }

            AthleteOutDTO athleteOutDTO = new AthleteOutDTO(a.getFullName(), a.getUsername(), a.getAge(), a.getGender(), a.getCity(), a.getSportType(), achievementOutDTOS);
            sameSportAndCityAthletesOutDTOS.add(athleteOutDTO);
        }
        return sameSportAndCityAthletesOutDTOS;
    }

    // extra endpoint 7 - Naelah
    public void sendTeammateRequest(Integer sender_athlete_id, Integer receiver_athlete_id) {
        // check both athlete exist
        Athlete senderAthlete = athleteRepository.findAthleteById(sender_athlete_id);
        Athlete receiverAthlete = athleteRepository.findAthleteById(receiver_athlete_id);
        if (senderAthlete == null || receiverAthlete == null) {
            throw new ApiException("Athlete Not Found");
        }
        // check they are in the same city and same sport
        if (!senderAthlete.getSportType().equalsIgnoreCase(receiverAthlete.getSportType())) {
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

   public List<TeammateRequestOutDTO> getAllTeammateRequests(){
        List<TeammateRequest> teammateRequests = teammateRequestRepository.findAll();
        List<TeammateRequestOutDTO> teammateRequestOutDTOS = new ArrayList<>();
        for(TeammateRequest t : teammateRequests){
            TeammateRequestOutDTO teammateRequestOutDTO = new TeammateRequestOutDTO(t.getSender().getFullName(), t.getSender().getUsername(), t.getReceiver().getFullName(), t.getReceiver().getUsername(), t.getStatus(), t.getRequestDate(), t.getResponseDate());
            teammateRequestOutDTOS.add(teammateRequestOutDTO);
        }
        return teammateRequestOutDTOS;
   }

    // Osama Alghamdi
    // athlete send sponsorship request to sponsor
    public void sponsorShipRequest(Integer sponsor_id,Integer athlete_id,SponsorShip sponsorShip) {

        Sponsor sponsor = sponsorRepository.findSponsorById(sponsor_id);
        Athlete athlete = athleteRepository.findAthleteById(athlete_id);

        if(sponsor == null || athlete == null) {
            throw new ApiException("Sponsor or athlete not found");
        }

        if (!sponsor.getIsActivated().equalsIgnoreCase("activated")){
            throw new ApiException("The sponsor not activated");
        }

        if(sponsorShip.getStatus()==null){
            sponsorShip.setStatus("Pending");
            sponsorShip.setAthlete(athlete);
            sponsorShip.setAthleteSponsor(sponsor);
            sponsorShipRepository.save(sponsorShip);
        }

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
    // Endpoint to allow participants to cancel their participation in an event
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

    // Renad
    // Endpoint to allow athlete to handel a sponsorship request sent by a sponsor
    public void handleSponsorshipRequest(Integer athleteId, Integer sponsorshipId, boolean isAccepted) {

        // Validate arena and booking
        Athlete athlete = athleteRepository.findAthleteById(athleteId);
        SponsorShip sponsorShip = sponsorShipRepository.findSponsorShipById(sponsorshipId);

        // Check if the sponsorship has been already accepted
        if (sponsorShip.getStatus().equalsIgnoreCase("Accepted")) {
            throw new ApiException("Sponsorship Already Accepted.");
        }

        // Check if the sponsorship has been already rejected
        if (sponsorShip.getStatus().equalsIgnoreCase("Rejected")) {
            throw new ApiException("Sponsorship Already Rejected.");
        }

        if (athlete == null && sponsorshipId == null) {
            throw new ApiException("Athlete and Sponsorship Not Found.");
        }
        if (athlete == null) {
            throw new ApiException("Athlete Not Found.");
        }
        if (sponsorshipId == null) {
            throw new ApiException("Sponsorship Not Found.");
        }

        // Handle acceptance or rejection
        if (isAccepted) {
            // Accept the sponsorship
            sponsorShip.setStatus("Accepted");
        } else {
            // Reject the sponsorship
            sponsorShip.setStatus("Rejected");
        }

        // Save the updated sponsorship
        sponsorShipRepository.save(sponsorShip);
    }

    // Osama
    public List<SponsorDTO> getSponsorsByCity(String city){
        List<Sponsor> sponsors = sponsorRepository.findAll();
        List<SponsorDTO> sponsorDTOS = new ArrayList<>();
        for (Sponsor s : sponsors) {
            if(s.getCity().equals(city)){
                SponsorDTO sponsorDTO = new SponsorDTO(s.getName(),s.getPhoneNumber(),s.getEmail(),s.getCity(),s.getCertificateRecord(),s.getSponsorShipList());
                sponsorDTOS.add(sponsorDTO);
            }

        }
        return sponsorDTOS;
    }
}