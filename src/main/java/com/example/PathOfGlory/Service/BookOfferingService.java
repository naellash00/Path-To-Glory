package com.example.PathOfGlory.Service;

import com.example.PathOfGlory.ApiResponse.ApiException;
import com.example.PathOfGlory.DTO.BookOfferingDTO;
import com.example.PathOfGlory.Model.Athlete;
import com.example.PathOfGlory.Model.BookOffering;
import com.example.PathOfGlory.Model.Offering;
import com.example.PathOfGlory.Repository.AthleteRepository;
import com.example.PathOfGlory.Repository.BookOfferingRepository;
import com.example.PathOfGlory.Repository.OfferingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/bookOffering")
@RequiredArgsConstructor
public class BookOfferingService { //Renad
    // 1. Declare a dependency for using Dependency Injection
    private final BookOfferingRepository bookOfferingRepository;
    private final OfferingRepository offeringRepository;
    private final AthleteRepository athleteRepository;

    // CRUD
    // GET
    public List<BookOfferingDTO> getAllBookOffering(){
        List<BookOffering> bookOfferings = bookOfferingRepository.findAll();
        List<BookOfferingDTO> bookOfferingDTOS = new ArrayList<>();
        for (BookOffering bookOffering: bookOfferings) {
            BookOfferingDTO bookOfferingDTO = new BookOfferingDTO(bookOffering.getStartDate(),bookOffering.getEndDate(),bookOffering.getStatus(),bookOffering.getBookingTimestamp());
            bookOfferingDTOS.add(bookOfferingDTO);
        }
        return bookOfferingDTOS;
    }

    // Extra endpoint:
    // Endpoint to allow athlete to book an offering
    public void bookOffering(Integer offeringId, Integer athleteId, Date startDate, Date endDate) {
        // Validate the offering and athlete availability
        Offering offering = offeringRepository.findOfferingById(offeringId);
        Athlete athlete = athleteRepository.findAthleteById(athleteId);

        if (offering == null && athlete == null) {
            throw new ApiException("Offering and athlete Not Found.");
        }
        if (offering == null) {
            throw new ApiException("Offering Not Found.");
        }
        if (athlete == null) {
            throw new ApiException("Athlete Not Found");
        }

        // Create the booking
        BookOffering bookOffering = new BookOffering();
        bookOffering.setOffering(offering);
        bookOffering.setAthlete(athlete);
        bookOffering.setStartDate(startDate);
        bookOffering.setEndDate(endDate);
        bookOffering.setBookingTimestamp(LocalDateTime.now());
        bookOffering.setStatus("Pending");

        bookOfferingRepository.save(bookOffering);
    }
}