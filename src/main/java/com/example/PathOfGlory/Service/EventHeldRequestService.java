// Osama Alghamdi

package com.example.PathOfGlory.Service;
import com.example.PathOfGlory.DTO.EventHeldRequestDTO;
import com.example.PathOfGlory.Model.EventHeldRequest;
import com.example.PathOfGlory.Repository.EventHeldRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventHeldRequestService {
    private final EventHeldRequestRepository eventHeldRequestRepository;

    public List<EventHeldRequestDTO> getAllEventHeldRequests() {
        List<EventHeldRequest> eventHeldRequests = eventHeldRequestRepository.findAll();
        List<EventHeldRequestDTO> eventHeldRequestDTOS = new ArrayList<>();
        for (EventHeldRequest eventHeldRequest : eventHeldRequests) {
            eventHeldRequestDTOS.add(new EventHeldRequestDTO(eventHeldRequest.getStatus()));
            eventHeldRequestRepository.save(eventHeldRequest);
        }
        return eventHeldRequestDTOS;
    }
}
