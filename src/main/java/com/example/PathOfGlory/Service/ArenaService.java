package com.example.PathOfGlory.Service;

import com.example.PathOfGlory.ApiResponse.ApiException;
import com.example.PathOfGlory.DTO.*;
import com.example.PathOfGlory.Model.*;
import com.example.PathOfGlory.Repository.*;
import lombok.RequiredArgsConstructor;
import com.example.PathOfGlory.Model.Service;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ArenaService { //Renad
    // 1. Declare a dependency for using Dependency Injection
    private final ArenaRepository arenaRepository;
    private final BookServiceRepository bookServiceRepository;
    private final EventHeldRequestRepository eventHeldRequestRepository;
    private final EventRepository eventRepository;
    private final AthleteRepository athleteRepository;
    private final ServiceRepository serviceRepository;

    // 2. CRUD
    // 2.1 GET
    public List<ArenaDTO> getAllArenas() {
        List<Arena> arenas = arenaRepository.findAll();
        List<ArenaDTO> arenaDTOS = new ArrayList<>();
        for (Arena arena : arenas) {
            // Map Services to ServiceDTO
            List<ServiceDTO> serviceDTOS = new ArrayList<>();
            for (Service service : arena.getServices()) {
                serviceDTOS.add(new ServiceDTO(service.getName(), service.getDescription(), service.getPricePerDay()));
            }

            // Map Events to EventDTO
            List<EventDTO> eventDTOS = new ArrayList<>();
            for (Event event : arena.getEvents()) {
                eventDTOS.add(new EventDTO(event.getName(), event.getDescription(), event.getCity(), event.getLocation(), event.getStartDate(), event.getEndDate()));
            }

            // Create ArenaDTO
            ArenaDTO arenaDTO = new ArenaDTO(
                    arena.getUsername(),
                    arena.getName(),
                    arena.getCity(),
                    arena.getLocation(),
                    serviceDTOS,
                    eventDTOS
            );

            arenaDTOS.add(arenaDTO);
        }

        return arenaDTOS;
    }

    // 2.2 POST
    public void addArena(Arena arena) {
        arena.setIsActivated("Not Active");
        arenaRepository.save(arena);
    }

    // 2.3 UPDATE
    public void updateArena(Integer id, Arena arena) {
        Arena oldArena = arenaRepository.findArenaById(id);
        if (oldArena == null) {
            throw new ApiException("Arena Not Found.");
        }
        oldArena.setName(arena.getName());
        oldArena.setUsername(arena.getUsername());
        oldArena.setPassword(arena.getPassword());
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

        oldArena.setServices(null);
        arenaRepository.delete(oldArena);
    }

    // 3. Extra endpoint:
    // 3.1 Endpoint to allow arena to handel an athlete request to book a service
    public void handleBookServiceRequest(Integer arenaId, Integer bookingId, boolean isAccepted) {

        // Validate arena and booking
        Arena arena = arenaRepository.findArenaById(arenaId);
        BookService bookService = bookServiceRepository.findBookServiceById(bookingId);

        // Check if the booking has been already accepted
        if (bookService.getStatus().equalsIgnoreCase("Accepted")) {
            throw new ApiException("Booking Already Accepted.");
        }

        // Check if the booking has been already rejected
        if (bookService.getStatus().equalsIgnoreCase("Rejected")) {
            throw new ApiException("Booking Already Rejected.");
        }

        if (arena == null && bookService == null) {
            throw new ApiException("Arena and Booking Not Found.");
        }
        if (arena == null) {
            throw new ApiException("Arena Not Found.");
        }
        if (bookService == null) {
            throw new ApiException("Booking Not Found.");
        }
        if (!arena.getIsActivated().equalsIgnoreCase("activated")) {
            throw new ApiException("Arena not activated");
        }

        // Validate that the booking is related to a service offered by the exact arena
        Service service = bookService.getService();
        if (service == null || !service.getArena().getId().equals(arenaId)) {
            throw new ApiException("Booking Does Not Belong to an Service in This Arena.");
        }


        // Handle acceptance or rejection
        if (isAccepted) {
            // Accept the booking
            bookService.setStatus("Accepted");
        } else {
            // Reject the booking
            bookService.setStatus("Rejected");
        }

        // Save the updated booking
        bookServiceRepository.save(bookService);
    }

    // 3.2 Endpoint to get all accepted service bookings for an arena
    public List<BookServiceDTO> getAllAcceptedServiceBookings(Integer arenaId) {
        Arena arena = arenaRepository.findArenaById(arenaId);
        if (arena == null) {
            throw new ApiException("Arena Not Found.");
        }

        List<BookService> bookServices = bookServiceRepository.findAll();
        List<BookServiceDTO> acceptedServiceBookings = new ArrayList<>();
        for (BookService bookService : bookServices) {
            if (bookService.getService().getArena().getId().equals(arenaId)
                    && bookService.getStatus().equalsIgnoreCase("Accepted")) {
                BookServiceDTO bookServiceDTO = new BookServiceDTO(bookService.getStartDate(), bookService.getEndDate(), bookService.getBookingPrice(), bookService.getStatus(), bookService.getBookingTimestamp());
                acceptedServiceBookings.add(bookServiceDTO);
            }
        }
        return acceptedServiceBookings;
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
        }
        if (eventHeldRequest == null) {
            throw new ApiException("Event Held Request Not Found.");
        }
        if (!arena.getIsActivated().equalsIgnoreCase("activated")) {
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
    public List<ArenaDTO> searchArenasByAthleteCity(Integer athleteId) {
        Athlete athlete = athleteRepository.findAthleteById(athleteId);
        if (athlete == null) {
            throw new ApiException("Athlete Not Found.");
        }
        List<Arena> arenas = arenaRepository.findArenasByCity(athlete.getCity());
        if (arenas.isEmpty()) {
            throw new ApiException("There Are No Arenas In " + athlete.getCity() + " Has Been Found Found.");
        }

        List<ArenaDTO> arenaDTOS = new ArrayList<>();
        for (Arena arena : arenas) {
            // Map Services to ServiceDTO
            List<ServiceDTO> serviceDTOS = new ArrayList<>();
            for (Service service : arena.getServices()) {
                serviceDTOS.add(new ServiceDTO(service.getName(), service.getDescription(), service.getPricePerDay()));
            }

            // Map Events to EventDTO
            List<EventDTO> eventDTOS = new ArrayList<>();
            for (Event event : arena.getEvents()) {
                eventDTOS.add(new EventDTO(event.getName(), event.getDescription(), event.getCity(), event.getLocation(), event.getStartDate(), event.getEndDate()));
            }

            // Create ArenaDTO
            ArenaDTO arenaDTO = new ArenaDTO(
                    arena.getUsername(),
                    arena.getName(),
                    arena.getCity(),
                    arena.getLocation(),
                    serviceDTOS,
                    eventDTOS
            );

            arenaDTOS.add(arenaDTO);
        }
        return arenaDTOS;
    }
}