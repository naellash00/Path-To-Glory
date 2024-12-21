package com.example.PathOfGlory.Service;

import com.example.PathOfGlory.ApiResponse.ApiException;
import com.example.PathOfGlory.Model.Coach;
import com.example.PathOfGlory.Model.Sponsor;
import com.example.PathOfGlory.Model.Admin;
import com.example.PathOfGlory.Model.Arena;
import com.example.PathOfGlory.Repository.AdminRepository;
import com.example.PathOfGlory.Repository.ArenaRepository;
import com.example.PathOfGlory.Repository.CoachRepository;
import com.example.PathOfGlory.Repository.SponsorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final SponsorRepository sponsorRepository;
    private final ArenaRepository arenaRepository;
    private final CoachRepository coachRepository;

    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void activateSponsor(Integer admin_id,Integer sponsor_id){
        Admin admin = adminRepository.findAdminById(admin_id);
        Sponsor sponsor = sponsorRepository.findSponsorById(sponsor_id);
        if (sponsor ==null || admin ==null){
            throw new ApiException("admin or sponsor not found");
        }
        if (sponsor.getIsActivated().equalsIgnoreCase("not active")){
            sponsor.setIsActivated("activated");
            sponsorRepository.save(sponsor);
        }
    }

    public void activateArena(Integer admin_id,Integer arena_id){
        Admin admin = adminRepository.findAdminById(admin_id);
        Arena arena = arenaRepository.findArenaById(arena_id);
        if (arena ==null || admin ==null){
            throw new ApiException("admin or arena not found");
        }
        if (arena.getIsActivated().equalsIgnoreCase("not active")){
            arena.setIsActivated("activated");
            arenaRepository.save(arena);
        }
    }

    public void activateCoach(Integer admin_id,Integer coach_id){
        Admin admin = adminRepository.findAdminById(admin_id);
        Coach coach = coachRepository.findCoachById(coach_id);
        if (coach ==null || admin ==null){
            throw new ApiException("admin or coach not found");
        }
        if (coach.getIsActivated().equalsIgnoreCase("not active")){
            coach.setIsActivated("activated");
            coachRepository.save(coach);
        }
    }
}