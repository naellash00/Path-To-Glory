// Osama Alghamdi
package com.example.PathOfGlory.Controller;
import com.example.PathOfGlory.DTO.EventParticipationRequestDTO;
import com.example.PathOfGlory.Service.EventParticipationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event-participation-request")
public class EventParticipationRequestController {
    private final EventParticipationRequestService eventParticipationRequestService;

    @GetMapping("/get")
    public ResponseEntity getAllEventParticipationRequests() {
        List<EventParticipationRequestDTO> eventParticipationRequests = eventParticipationRequestService.getAllEventParticipationRequests();
        return ResponseEntity.status(200).body(eventParticipationRequests);
    }
}
