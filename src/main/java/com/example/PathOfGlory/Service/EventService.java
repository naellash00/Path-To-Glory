package com.example.PathOfGlory.Service;

import com.example.PathOfGlory.ApiResponse.ApiException;
import com.example.PathOfGlory.DTO.EventDTO;
import com.example.PathOfGlory.Model.Athlete;
import com.example.PathOfGlory.Model.Event;
import com.example.PathOfGlory.Model.Sponsor;
import com.example.PathOfGlory.Repository.AthleteRepository;
import com.example.PathOfGlory.Repository.EventRepository;
import com.example.PathOfGlory.Repository.SponsorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService { //Renad
    // 1. Declare a dependency for EventRepository & ArenaRepository using Dependency Injection
    private final EventRepository eventRepository;
    private final AthleteRepository athleteRepository;
    private final SponsorRepository sponsorRepository;

    //private final ArenaRepository arenaRepository;

    // 2. CRUD
    // 2.1 GET
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOS = new ArrayList<>();
        for (Event event : events) {
            EventDTO eventDTO = new EventDTO(event.getName(), event.getDescription(), event.getCity(), event.getLocation(), event.getStartDate(), event.getEndDate());
            eventDTOS.add(eventDTO);
        }
        return eventDTOS;
    }

    // 2.3 UPDATE
    public void updateEvent(Integer id, Event event) {
        Event oldEvent = eventRepository.findEventById(id);
        if (oldEvent == null) {
            throw new ApiException("Event Not Found.");
        }
        oldEvent.setName(event.getName());
        oldEvent.setNumber(event.getNumber());
        oldEvent.setDescription(event.getDescription());
        oldEvent.setCity(event.getCity());
        oldEvent.setLocation(event.getLocation());
        oldEvent.setStartDate(event.getStartDate());
        oldEvent.setEndDate(event.getEndDate());
        eventRepository.save(oldEvent);
    }

    // Extra endpoint:
    // Allow athlete to search for all future accepted events sponsored by a particular sponsor within athlete city
    public List<EventDTO> searchUpcomingEventsForSponsor(Integer athleteId, Integer sponsorId) {
        LocalDate today = LocalDate.now();
        Athlete athlete = athleteRepository.findAthleteById(athleteId);
        Sponsor sponsor = sponsorRepository.findSponsorById(sponsorId);

        if (athlete == null || sponsor == null) {
            throw new ApiException("Athlete or Sponsor Not Found.");
        }
        List<Event> upcomingEvents = eventRepository.findBySponsorIdAndStartDateAfterAndCityAndStatus(sponsorId, today, athlete.getCity(), "accepted");

        if (upcomingEvents.isEmpty()) {
            throw new ApiException("There Are No Upcoming Accepted Events For This Sponsor Within Athlete City.");
        }

        List<EventDTO> upcomingEventsDTOS = new ArrayList<>();
        for (Event event : upcomingEvents) {
            EventDTO eventDTO = new EventDTO(event.getName(), event.getDescription(), event.getCity(), event.getLocation(), event.getStartDate(), event.getEndDate());
            upcomingEventsDTOS.add(eventDTO);
        }
        return upcomingEventsDTOS;
    }

    // Filter Events by Date Range
    public List<EventDTO> getEventsByDateRange(Integer athleteId, Date startDate, Date endDate) {
        Athlete athlete = athleteRepository.findAthleteById(athleteId);

        if (athlete == null) {
            throw new ApiException("Athlete or Sponsor Not Found.");
        }

        List<Event> events = eventRepository.findByStartDateBetween(startDate, endDate);

        if (events.isEmpty()) {
            throw new ApiException("No events found in the specified date range.");
        }

        List<EventDTO> eventDTOS = new ArrayList<>();
        for (Event event : events) {
            EventDTO eventDTO = new EventDTO(event.getName(), event.getDescription(), event.getCity(), event.getLocation(), event.getStartDate(), event.getEndDate());
            eventDTOS.add(eventDTO);
        }
        return eventDTOS;
    }
}