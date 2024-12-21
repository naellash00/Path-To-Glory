package com.example.PathOfGlory.Controller;
import com.example.PathOfGlory.ApiResponse.ApiResponse;
import com.example.PathOfGlory.DTO.BookServiceDTO;
import com.example.PathOfGlory.Model.Achievement;
import com.example.PathOfGlory.Model.Arena;
import com.example.PathOfGlory.Model.Service;
import com.example.PathOfGlory.Service.ArenaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/arena")
@RequiredArgsConstructor
public class ArenaController { //Renad
    // 1. Declare a dependency for ArenaService using Dependency Injection
    private final ArenaService arenaService;

    // 2. CRUD
    // 2.1 GET
    @GetMapping("/get")
    public ResponseEntity getAllArenas() {
        return ResponseEntity.status(200).body(arenaService.getAllArenas());
    }

    // 2.2 POST
    @PostMapping("/add")
    public ResponseEntity addArena(@RequestBody @Valid Arena arena) {
        arenaService.addArena(arena);
        return ResponseEntity.status(200).body(new ApiResponse("New Arena Added."));
    }

    // 2.3 UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity updateArena(@PathVariable Integer id, @RequestBody @Valid Arena arena) {
        arenaService.updateArena(id, arena);
        return ResponseEntity.status(200).body(new ApiResponse("Arena Updated."));
    }

    // 2.4 DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteArena(@PathVariable Integer id) {
        arenaService.deleteArena(id);
        return ResponseEntity.status(200).body(new ApiResponse("Arena Deleted."));
    }

    // Extra endpoints:
    @PutMapping("/handleBookServiceRequest/arenaId/{arenaId}/bookingId/{bookingId}/isAccepted/{isAccepted}")
    public ResponseEntity handleBookServiceRequest(@PathVariable Integer arenaId, @PathVariable Integer bookingId, @PathVariable Boolean isAccepted){
        arenaService.handleBookServiceRequest(arenaId,bookingId,isAccepted);
        return ResponseEntity.status(200).body(new ApiResponse("Request Handled."));
    }

    @GetMapping("/getAllAcceptedOfferingBookings/{arenaId}")
    public ResponseEntity getAllAcceptedServiceBookings(@PathVariable Integer arenaId){
        List<BookServiceDTO> acceptedServiceBookings = arenaService.getAllAcceptedServiceBookings(arenaId);
        return ResponseEntity.status(200).body(acceptedServiceBookings);
    }

    @PutMapping("/handleEventHeldRequest/arenaId/{arenaId}/eventHeldRequestId/{eventHeldRequestId}/isAccepted/{isAccepted}")
    public ResponseEntity handleEventHeldRequest(@PathVariable Integer arenaId, @PathVariable Integer eventHeldRequestId, @PathVariable Boolean isAccepted){
        arenaService.handleEventHeldRequest(arenaId,eventHeldRequestId,isAccepted);
        return ResponseEntity.status(200).body(new ApiResponse("Request Handled."));
    }
}