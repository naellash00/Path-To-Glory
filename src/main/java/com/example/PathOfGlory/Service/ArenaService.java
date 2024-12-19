package com.example.PathOfGlory.Service;

import com.example.PathOfGlory.ApiResponse.ApiException;
import com.example.PathOfGlory.DTO.ArenaDTO;
import com.example.PathOfGlory.DTO.BookOfferingDTO;
import com.example.PathOfGlory.Model.*;
import com.example.PathOfGlory.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArenaService { //Renad
    // 1. Declare a dependency for using Dependency Injection
    private final ArenaRepository arenaRepository;
    private final BookOfferingRepository bookOfferingRepository;
    private final EventHeldRequestRepository eventHeldRequestRepository;
    private final EventRepository eventRepository;
    private final AthleteRepository athleteRepository;

    // 2. CRUD
    // 2.1 GET
    public List<ArenaDTO> getAllArenas() {
        List<Arena> arenas = arenaRepository.findAll();
        List<ArenaDTO> arenaDTOS = new ArrayList<>();
        for (Arena arena : arenas) {
            ArenaDTO arenaDTO = new ArenaDTO(arena.getUsername(), arena.getName(), arena.getCity(), arena.getLocation());
            arenaDTOS.add(arenaDTO);
        }
        return arenaDTOS;
    }

    // 2.2 POST
    public void addArena(Arena arena) {
        arena.setIsActivated("not active");
        arenaRepository.save(arena);
    }

    // 2.3 UPDATE
    public void updateArena(Integer id, Arena arena) {
        Arena oldArena = arenaRepository.findArenaById(id);
        if (oldArena == null) {
            throw new ApiException("Arena Not Found.");
        }
        oldArena.setName(arena.getName());
        oldArena.setCity(arena.getCity());
        oldArena.setLocation(arena.getLocation());
        oldArena.setLicense(arena.getLicense());
        oldArena.setLicenseEndDate(arena.getLicenseEndDate());
        arenaRepository.save(oldArena);
    }

    // 2.4 DELETE
    public void deleteArena(Integer id) {
        Arena oldArena = arenaRepository.findArenaById(id);
        if (oldArena == null) {
            throw new ApiException("Arena Not Found.");
        }

        oldArena.setOfferings(null);
        arenaRepository.delete(oldArena);
    }

    // 3. Extra endpoint:
    // 3.1 Endpoint to allow arena to handel an athlete request to book an offering
    public void handleBookOfferingRequest(Integer arenaId, Integer bookingId, boolean isAccepted) {

        // Validate arena and booking
        Arena arena = arenaRepository.findArenaById(arenaId);
        BookOffering bookOffering = bookOfferingRepository.findBookOfferingById(bookingId);

        // Check if the booking has been already accepted
        if (bookOffering.getStatus().equalsIgnoreCase("Accepted")) {
            throw new ApiException("Booking Already Accepted.");
        }

        // Check if the booking has been already rejected
        if (bookOffering.getStatus().equalsIgnoreCase("Rejected")) {
            throw new ApiException("Booking Already Rejected.");
        }

        if (arena == null && bookOffering == null) {
            throw new ApiException("Arena and Booking Not Found.");
        }
        if (arena == null) {
            throw new ApiException("Arena Not Found.");
        }if (bookOffering == null) {
            throw new ApiException("Booking Not Found.");
        }
        if(!arena.getIsActivated().equalsIgnoreCase("activated")){
            throw new ApiException("Arena not activated");
        }

        // Validate that the booking is related to an offering in the arena
        Offering offering = bookOffering.getOffering();
        if (offering == null || !offering.getArena().getId().equals(arenaId)) {
            throw new ApiException("Booking Does Not Belong to an Offering in This Arena.");
        }


        // Handle acceptance or rejection
        if (isAccepted) {
            // Accept the booking
            bookOffering.setStatus("Accepted");
        } else {
            // Reject the booking
            bookOffering.setStatus("Rejected");
        }

        // Save the updated booking
        bookOfferingRepository.save(bookOffering);
    }

    // 3.2 Endpoint to get all accepted offering bookings for an arena
    public List<BookOfferingDTO> getAllAcceptedOfferingBookings(Integer arenaId){
        Arena arena = arenaRepository.findArenaById(arenaId);
        if (arena == null ) {
            throw new ApiException("Arena Not Found.");
        }

        List<BookOffering> bookOfferings = bookOfferingRepository.findAll();
        List<BookOfferingDTO> acceptedBookOfferingDTOS = new ArrayList<>();
        for (BookOffering bookOffering : bookOfferings) {
            if (bookOffering.getOffering().getArena().getId().equals(arenaId)
                    && bookOffering.getStatus().equalsIgnoreCase("Accepted")) {
                BookOfferingDTO bookOfferingDTO = new BookOfferingDTO(bookOffering.getStartDate(),bookOffering.getEndDate(),bookOffering.getStatus(),bookOffering.getBookingTimestamp());
                acceptedBookOfferingDTOS.add(bookOfferingDTO);
            }
        }
        return acceptedBookOfferingDTOS;
    }

    // 3.3 Endpoint to allow arena to handel a sponsor event held request
    public void handleEventHeldRequest(Integer arenaId, Integer eventHeldRequestId, boolean isAccepted) {
        // Validate arena and booking
        Arena arena = arenaRepository.findArenaById(arenaId);
        EventHeldRequest eventHeldRequest = eventHeldRequestRepository.findEventHeldRequestById(eventHeldRequestId);
        if (arena == null && eventHeldRequest == null) {
            throw new ApiException("Arena and Event Held Request Not Found.");
        }
        if (arena == null) {
            throw new ApiException("Arena Not Found.");
        }if (eventHeldRequest == null) {
            throw new ApiException("Event Held Request Not Found.");
        }
        if(!arena.getIsActivated().equalsIgnoreCase("activated")){
            throw new ApiException("Arena not activated");
        }

        // Validate that the event held request is related to the arena
        if (!eventHeldRequest.getArena().getId().equals(arenaId)) {
            throw new ApiException("Event Held Request Does Not Belong to This Arena.");
        }

        // Handle acceptance or rejection
        if (isAccepted) {
            // Accept the event held request
            eventHeldRequest.setStatus("Accepted");
            // Accept the event
            eventHeldRequest.getEvent().setStatus("Accepted");
        } else {
            // Reject the booking
            eventHeldRequest.setStatus("Rejected");
            // Reject the event
            eventHeldRequest.getEvent().setStatus("Rejected");
        }

        // Save the updated request and event
        eventHeldRequestRepository.save(eventHeldRequest);
        eventRepository.save(eventHeldRequest.getEvent());
    }

    // 2.1 Allow Athlete to search for arena by athlete city
    public List<ArenaDTO> searchArenaByCity(Integer athleteId) {
        Athlete athlete = athleteRepository.findAthleteById(athleteId);
        if (athlete == null ) {
            throw new ApiException("Athlete Not Found.");
        }
        List<Arena> arenas = arenaRepository.findArenasByCity(athlete.getCity());
        if (arenas.isEmpty()) {
            throw new ApiException("There Are No Arenas In " + athlete.getCity() +" Has Been Found Found.");
        }

        List<ArenaDTO> arenaDTOS = new ArrayList<>();
        for (Arena arena : arenas) {
            ArenaDTO arenaDTO = new ArenaDTO(arena.getUsername(), arena.getName(), arena.getCity(), arena.getLocation());
            arenaDTOS.add(arenaDTO);
        }
        return arenaDTOS;
    }
}