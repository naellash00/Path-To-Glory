// Osama Alghamdi

package com.example.PathOfGlory.Service;
import com.example.PathOfGlory.ApiResponse.ApiException;
import com.example.PathOfGlory.DTO.AchievementOutDTO;
import com.example.PathOfGlory.DTO.AthleteOutDTO;
import com.example.PathOfGlory.DTO.SponsorDTO;
import com.example.PathOfGlory.Model.*;
import com.example.PathOfGlory.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SponsorService {
    private final SponsorRepository sponsorRepository;
    private final AthleteRepository athleteRepository;
    private final SponsorShipRepository sponsorShipRepository;
    private final ArenaRepository arenaRepository;
    private final EventHeldRequestRepository eventHeldRequestRepository;
    private final EventParticipationRequestRepository eventParticipationRequestRepository;
    private final EventRepository eventRepository;

    public List<SponsorDTO> getAllSponsor() {
        List<Sponsor> sponsors = sponsorRepository.findAll();
        List<SponsorDTO> sponsorDTOS = new ArrayList<>();
        for (Sponsor sponsor : sponsors) {
            SponsorDTO sponsorDTO = new SponsorDTO(sponsor.getName(),sponsor.getPhoneNumber(),
                    sponsor.getEmail(),sponsor.getCity(),sponsor.getCertificateRecord(),sponsor.getSponsorShipList());
            sponsorDTOS.add(sponsorDTO);
        }

        return sponsorDTOS;
    }


    public void addSponsor(Sponsor sponsor) {
        sponsor.setIsActivated("not active");
        sponsorRepository.save(sponsor);
    }

    public void updateSponsor(Integer id,Sponsor sponsor) {
        Sponsor oldSponsor = sponsorRepository.findSponsorById(id);
        if(oldSponsor == null) {
            throw new ApiException("Sponsor not found");
        }
        oldSponsor.setName(sponsor.getName());
        oldSponsor.setCity(sponsor.getCity());
        oldSponsor.setEmail(sponsor.getEmail());
        oldSponsor.setPhoneNumber(sponsor.getPhoneNumber());
        sponsorRepository.save(oldSponsor);
    }

    public void deleteSponsor(Integer id) {
        Sponsor sponsor = sponsorRepository.findSponsorById(id);
        sponsorRepository.delete(sponsor);

    }

    // Osama Alghamdi
    // sponsor send sponsorship request to athlete
    public void sponsorAthlete(Integer sponsor_id,Integer athlete_id,SponsorShip sponsorShip) {

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

    // Osama Alghamdi and Renad
    // sponsor request event from arena
    public void requestEvent(Integer sponsorId, Integer arenaId, Event event) {
        Sponsor sponsor = sponsorRepository.findSponsorById(sponsorId);
        if(sponsor == null) {
            throw new ApiException("Sponsor not found");
        }
        if(!sponsor.getIsActivated().equalsIgnoreCase("activated")){
            throw new ApiException("Sponsor not activated");
        }
        Arena arena =  arenaRepository.findArenaById(arenaId);
        if(arena == null) {
            throw new ApiException("Arena not found");
        }
        EventHeldRequest eventHeldRequest = new EventHeldRequest();
        eventHeldRequest.setSponsor(sponsor);
        eventHeldRequest.setArena(arena);
        eventHeldRequest.setEvent(event);
        eventHeldRequest.setStatus("pending");
        eventHeldRequestRepository.save(eventHeldRequest);

        event.setStatus("pending");
        event.setArena(arena);
        event.setSponsor(sponsor);
        eventRepository.save(event);
    }

    // Osama Alghamdi
    // sponsor accept the event participation request from athlete
    public void acceptEventParticipation(Integer sponsor_id,Integer participationRequestId) {
        Sponsor sponsor = sponsorRepository.findSponsorById(sponsor_id);
        if(sponsor == null) {
            throw new ApiException("Sponsor not found");
        }

        if(!sponsor.getIsActivated().equalsIgnoreCase("activated")){
            throw new ApiException("Sponsor not activated");
        }
        EventParticipationRequest participationRequest = eventParticipationRequestRepository.findEventParticipationRequestById(participationRequestId);
        if(participationRequest == null) {
            throw new ApiException("Participation request not found");
        }

        if (participationRequest.getStatus().equals("pending")) {
            participationRequest.setStatus("accepted");
            eventParticipationRequestRepository.save(participationRequest);
        }
    }

    // get athlete equal or greeter than achievementNumber - Osama alghamdi
    public List<AthleteOutDTO> getAllSponsorShipByAchievementNumber(Integer sponsor_id, Integer achievementNumber) {
        Sponsor sponsor = sponsorRepository.findSponsorById(sponsor_id);
        if(sponsor == null) {
            throw new ApiException("Sponsor not found");
        }

        if(!sponsor.getIsActivated().equalsIgnoreCase("activated")){
            throw new ApiException("Sponsor not activated");
        }

        List<AthleteOutDTO> athleteDTO = new ArrayList<>();
        Set<SponsorShip> sponsorShips = sponsor.getSponsorShipList();
        List<AchievementOutDTO> achievementOutDTOS = new ArrayList<>();
        for (SponsorShip s :sponsorShips) {
            Set<Achievement> achievement = s.getAthlete().getAchievements();
            for (Achievement a : achievement) {
                AchievementOutDTO achievementOutDTO = new AchievementOutDTO(a.getTitle(),a.getDescription());
                achievementOutDTOS.add(achievementOutDTO);
            }
        }
        for (SponsorShip sponsorShip : sponsorShips) {
            if(sponsorShip.getAthlete().getAchievements().size() >= achievementNumber) {
                AthleteOutDTO athleteOutDTO = new AthleteOutDTO(sponsorShip.getAthlete().getFullName(),sponsorShip.getAthlete().getUsername(),
                        sponsorShip.getAthlete().getAge(),sponsorShip.getAthlete().getGender(),sponsorShip.getAthlete().getCity(),sponsorShip.getAthlete().getSportName(),achievementOutDTOS);
                athleteDTO.add(athleteOutDTO);
            }
        }
        return athleteDTO;
    }

    public void finishSponsorShip(Integer sponsor_id,Integer sponsorship_id){
        LocalDateTime localDateTime = LocalDateTime.now();
        Sponsor sponsor = sponsorRepository.findSponsorById(sponsor_id);
        SponsorShip sponsorShip = sponsorShipRepository.findSponsorShipById(sponsorship_id);
        if( sponsor == null || sponsorShip ==null){
            throw new ApiException("sponsor or sponsorship not found");
        }

        if(!sponsor.getIsActivated().equalsIgnoreCase("activated")){
            throw new ApiException("sponsor not activated");
        }
        if(localDateTime.isAfter(sponsorShip.getEndDate())){
            sponsorShip.setStatus("Finished");
            sponsorShipRepository.save(sponsorShip);
        }
    }
}