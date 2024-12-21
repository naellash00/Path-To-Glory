package com.example.PathOfGlory.Controller;

import com.example.PathOfGlory.ApiResponse.ApiResponse;
import com.example.PathOfGlory.Model.Admin;
import com.example.PathOfGlory.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity addAdmin(@RequestBody @Valid Admin admin){
        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin added successfully"));
    }
    @PutMapping("/active-sponsor/{admin_id}/{sponsor_id}")
    public ResponseEntity activeSponsor(@PathVariable Integer admin_id,@PathVariable Integer sponsor_id){
        adminService.activateSponsor(admin_id,sponsor_id);
        return ResponseEntity.status(200).body(new ApiResponse("Sponsor is activated"));
    }

    @PutMapping("/active-arena/{admin_id}/{arena_id}")
    public ResponseEntity activateArena(@PathVariable Integer admin_id,@PathVariable Integer arena_id){
        adminService.activateArena(admin_id,arena_id);
        return ResponseEntity.status(200).body(new ApiResponse("Arena is activated"));
    }

    @PutMapping("/active-coach/{admin_id}/{coach_id}")
    public ResponseEntity activateCoach(@PathVariable Integer admin_id,@PathVariable Integer coach_id){
        adminService.activateCoach(admin_id,coach_id);
        return ResponseEntity.status(200).body(new ApiResponse("Coach is activated"));
    }
}