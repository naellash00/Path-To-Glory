package com.example.PathOfGlory.Controller;

import com.example.PathOfGlory.ApiResponse.ApiResponse;
import com.example.PathOfGlory.DTO.EventDTO;
import com.example.PathOfGlory.Model.Event;
import com.example.PathOfGlory.Service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {  //Renad
    // 1. Declare a dependency for EventService using Dependency Injection
    private final EventService eventService;

    // 2. CRUD
    // 2.1 GET
    @GetMapping("/get")
    public ResponseEntity getAllEvents() {
        return ResponseEntity.status(200).body(eventService.getAllEvents());
    }

    // 2.2 UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id, @RequestBody @Valid Event event) {
        eventService.updateEvent(id, event);
        return ResponseEntity.status(200).body(new ApiResponse("Event Updated."));
    }

    // Extra endpoint:
    @GetMapping("/getEventsByDateRange/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<List<EventDTO>> getEventsByDateRange(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  Date endDate) {
        List<EventDTO> events = eventService.getEventsByDateRange(startDate, endDate);
        return ResponseEntity.ok(events);
    }
}