// Osama Alghamdi

package com.example.PathOfGlory.Service;
import com.example.PathOfGlory.DTO.EventParticipationRequestDTO;
import com.example.PathOfGlory.Model.EventParticipationRequest;
import com.example.PathOfGlory.Repository.EventParticipationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventParticipationRequestService {
    private final EventParticipationRequestRepository eventParticipationRequestRepository;

    public List<EventParticipationRequestDTO> getAllEventParticipationRequests() {
        List<EventParticipationRequest> eventParticipationRequests = eventParticipationRequestRepository.findAll();
        List<EventParticipationRequestDTO> eventParticipationRequestDTOS = new ArrayList<>();
        for (EventParticipationRequest eventParticipationRequest : eventParticipationRequests) {
            eventParticipationRequestDTOS.add(new EventParticipationRequestDTO(eventParticipationRequest.getStatus(),
                    eventParticipationRequest.getSponsor(),eventParticipationRequest.getAthlete()));
            eventParticipationRequestRepository.save(eventParticipationRequest);
        }
        return eventParticipationRequestDTOS;
    }
}
