

package com.example.PathOfGlory.Service;

import com.example.PathOfGlory.ApiResponse.ApiException;
import com.example.PathOfGlory.DTO.CoachAthleteOutDTO;
import com.example.PathOfGlory.DTO.CoachOutDTO;
import com.example.PathOfGlory.Model.Athlete;
import com.example.PathOfGlory.Model.BookCoach;
import com.example.PathOfGlory.Model.Coach;
import com.example.PathOfGlory.Repository.CoachRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoachService { // naelah
    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    //    public List<Coach> getAllCoaches(){
//        return coachRepository.findAll();
//    }
    public List<CoachOutDTO> getAllCoaches() {
        List<Coach> coaches = coachRepository.findAll();
        List<CoachOutDTO> coachOutDTOS = new ArrayList<>();
        for (Coach c : coaches) {
            CoachOutDTO coachOutDTO = new CoachOutDTO(c.getFullName(), c.getUsername(), c.getCity(), c.getPhoneNumber(), c.getEmail(), c.getSportDiscipline());
            coachOutDTOS.add(coachOutDTO);
        }
        return coachOutDTOS;
    }

    public void addCoach(Coach coach) {
        coach.setIsActivated("not active");
        coachRepository.save(coach);
    }

    public void updateCoach(Integer id, Coach coach) {
        Coach oldCoach = coachRepository.findCoachById(id);
        if (oldCoach == null) {
            throw new ApiException("coach not found");
        }
        oldCoach.setFullName(coach.getFullName());
        oldCoach.setUsername(coach.getUsername());
        oldCoach.setAge(coach.getAge());
        oldCoach.setCity(coach.getCity());
        oldCoach.setPhoneNumber(coach.getPhoneNumber());
        oldCoach.setEmail(coach.getEmail());
        oldCoach.setSportDiscipline(coach.getSportDiscipline());
        oldCoach.setLicense(coach.getLicense());
        oldCoach.setLicenseEndDate(coach.getLicenseEndDate());
        coachRepository.save(oldCoach);
    }

    public void deleteCoach(Integer id) {
        Coach coach = coachRepository.findCoachById(id);
        if (coach == null) {
            throw new ApiException("coach not found");
        }
        coachRepository.delete(coach);
    }

    // extra endpoint 9 - Naelah
    public List<CoachAthleteOutDTO> getAllCoachAthletes(Integer coach_id){
        // check coach id
        Coach coach = coachRepository.findCoachById(coach_id);
        if(coach == null){
            throw new ApiException("coach not found");
        }
        List<CoachAthleteOutDTO> athletes = new ArrayList<>();
        // loop through this coack bookings to acces its athletes
        for(BookCoach booking : coach.getBookings()){
            // the athlete in this coach booking
            Athlete athlete = booking.getAthlete();
            // this athlete dto
            CoachAthleteOutDTO coachAthlete = new CoachAthleteOutDTO(athlete.getFullName(), athlete.getUsername(),athlete.getSportType());
            athletes.add(coachAthlete);
        }
        return athletes;
    }
}
