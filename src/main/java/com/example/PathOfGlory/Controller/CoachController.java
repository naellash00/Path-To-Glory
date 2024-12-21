package com.example.PathOfGlory.Controller;

import com.example.PathOfGlory.ApiResponse.ApiResponse;
import com.example.PathOfGlory.Model.Coach;
import com.example.PathOfGlory.Service.BookCoachService;
import com.example.PathOfGlory.Service.CoachService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coach")
public class CoachController { // Naelah
    private final CoachService coachService;
    private final BookCoachService bookCoachService;

    public CoachController(CoachService coachService, BookCoachService bookCoachService) {
        this.coachService = coachService;
        this.bookCoachService = bookCoachService;
    }

    @GetMapping("/get")
    public ResponseEntity getAllCoaches(){
        return ResponseEntity.status(200).body(coachService.getAllCoaches());
    }

    @PostMapping("/add")
    public ResponseEntity addCoach(@RequestBody @Valid Coach coach){
        coachService.addCoach(coach);
        return ResponseEntity.status(200).body(new ApiResponse("Coach Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCoach(@PathVariable Integer id, @RequestBody @Valid Coach coach){
        coachService.updateCoach(id, coach);
        return ResponseEntity.status(200).body(new ApiResponse("Coach Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCoach(@PathVariable Integer id){
        coachService.deleteCoach(id);
        return ResponseEntity.status(200).body(new ApiResponse("Coach Deleted Successfully"));
    }

    @PutMapping("/accept/booking/{coach_id}/{booking_id}/{price}")
    public ResponseEntity acceptBookingRequest(@PathVariable Integer coach_id, @PathVariable Integer booking_id, @PathVariable Integer price){
        bookCoachService.acceptBookingRequest(coach_id, booking_id, price);
        return ResponseEntity.status(200).body(new ApiResponse("Booking Request Accepted Successfully"));
    }

    @PutMapping("/reject/booking/{coach_id}/{booking_id}")
    public ResponseEntity rejectBookingRequest(@PathVariable Integer coach_id, @PathVariable Integer booking_id){
        bookCoachService.rejectBookingRequest(coach_id, booking_id);
        return ResponseEntity.status(200).body(new ApiResponse("Booking Request Rejected"));
    }

    @GetMapping("/get/coach/athletes/{coach_id}")
    public ResponseEntity getAllCoachAthletes(@PathVariable Integer coach_id){
        return ResponseEntity.status(200).body(coachService.getAllCoachAthletes(coach_id));
    }
}
