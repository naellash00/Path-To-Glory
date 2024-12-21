package com.example.PathOfGlory.Service;

import com.example.PathOfGlory.ApiResponse.ApiException;
import com.example.PathOfGlory.DTO.BookServiceDTO;
import com.example.PathOfGlory.Model.Athlete;
import com.example.PathOfGlory.Model.BookService;
import com.example.PathOfGlory.Model.Service;
import com.example.PathOfGlory.Repository.AthleteRepository;
import com.example.PathOfGlory.Repository.BookServiceRepository;
import com.example.PathOfGlory.Repository.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class BookServiceService { //Renad
    // 1. Declare a dependency for using Dependency Injection
    private final BookServiceRepository bookServiceRepository;
    private final ServiceRepository serviceRepository;
    private final AthleteRepository athleteRepository;

    // CRUD
    // GET
    public List<BookServiceDTO> getAllBookServices(){
        List<BookService> bookServices = bookServiceRepository.findAll();
        List<BookServiceDTO> bookServiceDTOS = new ArrayList<>();
        for (BookService bookService: bookServices) {
            BookServiceDTO bookServiceDTO = new BookServiceDTO(bookService.getStartDate(),bookService.getEndDate(),bookService.getBookingPrice(), bookService.getStatus(),bookService.getBookingTimestamp());
            bookServiceDTOS.add(bookServiceDTO);
        }
        return bookServiceDTOS;
    }

    // Extra endpoint:
    // Endpoint to allow athlete to book a Service
    public void bookService(Integer serviceId, Integer athleteId, Date startDate, Date endDate) {
        // Validate the service and athlete availability
        Service service = serviceRepository.findServiceById(serviceId);
        Athlete athlete = athleteRepository.findAthleteById(athleteId);

        if (service == null && athlete == null) {
            throw new ApiException("Service and athlete Not Found.");
        }
        if (service == null) {
            throw new ApiException("Service Not Found.");
        }
        if (athlete == null) {
            throw new ApiException("Athlete Not Found");
        }

        // Validate dates
        if (startDate == null || endDate == null || !endDate.after(startDate)) {
            throw new ApiException("Invalid date range. End date must be after start date.");
        }

        // Calculate the number of days
        long diffInMillis = endDate.getTime() - startDate.getTime();
        long days = (diffInMillis / (1000 * 60 * 60 * 24)) + 1; // Include the start day

        // Calculate the booking price
        double bookingPrice = days * service.getPricePerDay();

        // Create the booking
        BookService bookService = new BookService();
        bookService.setService(service);
        bookService.setAthlete(athlete);
        bookService.setStartDate(startDate);
        bookService.setEndDate(endDate);
        bookService.setBookingPrice(bookingPrice);
        bookService.setBookingTimestamp(LocalDateTime.now());
        bookService.setStatus("Pending");

        bookServiceRepository.save(bookService);
    }
}