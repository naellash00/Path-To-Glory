

package com.example.PathOfGlory.Service;

import com.example.PathOfGlory.ApiResponse.ApiException;
import com.example.PathOfGlory.DTO.BookCoachOutDTO;
import com.example.PathOfGlory.Model.Athlete;
import com.example.PathOfGlory.Model.BookCoach;
import com.example.PathOfGlory.Model.Coach;
import com.example.PathOfGlory.Repository.AthleteRepository;
import com.example.PathOfGlory.Repository.BookCoachRepository;
import com.example.PathOfGlory.Repository.CoachRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookCoachService { // Naelah
    private final BookCoachRepository bookCoachRepository;
    private final AthleteRepository athleteRepository;
    private final CoachRepository coachRepository;

//    public List<BookCoach> getAllBookings(){
//        return bookCoachRepository.findAll();
//    }

    public List<BookCoachOutDTO> getAllBookings() {
        List<BookCoach> bookings = bookCoachRepository.findAll();
        List<BookCoachOutDTO> bookCoachOutDTOS = new ArrayList<>();
        for (BookCoach b : bookings) {
            BookCoachOutDTO bookCoachOutDTO = new BookCoachOutDTO(b.getStartDate(), b.getEndDate(), b.getStatus(), b.getBookingPrice(), b.getAthlete().getFullName(), b.getCoach().getFullName());
            bookCoachOutDTOS.add(bookCoachOutDTO);
        }
        return bookCoachOutDTOS;
    }

    // extra endpoint 1 - Naelah
    public void requestCoachBooking(Integer athlete_id, String coach_username, BookCoach booking) {
        // check athlete id and check coach id
        Athlete athlete = athleteRepository.findAthleteById(athlete_id);
        //Coach coach = coachRepository.findCoachById(coach_id);
        Coach coach = coachRepository.findCoachByUsername(coach_username);

        if (athlete == null) {
            throw new ApiException("athlete not found");
        }
        if (coach == null) {
            throw new ApiException("coach not found");
        }

        // check if athlete and coach has same sport type
        if (!athlete.getSportName().equalsIgnoreCase(coach.getSportDiscipline())) {
            throw new ApiException("Your Sport Type Are Not Related. cannot book this coach");
        }
        // check if they are in the same city
        if (!athlete.getCity().equalsIgnoreCase(coach.getCity())) {
            throw new ApiException("cannot book coach not in the same city");
        }

        booking.setAthlete(athlete);
        booking.setCoach(coach);
        booking.setStatus("Requested");
        bookCoachRepository.save(booking);
    }

    //extra endpoint 2 - Naelah
    public void acceptBookingRequest(Integer coach_id, Integer booking_id, Integer price) {
        // check its the same booking and the same coach
        Coach coach = coachRepository.findCoachById(coach_id);
        BookCoach booking = bookCoachRepository.findBookCoachById(booking_id);
        if (coach == null) {
            throw new ApiException("coach not found");
        }
        if (booking == null) {
            throw new ApiException("cant accept booking not created");
        }
        // check if this coach is the same coach in the booking request
        if (!booking.getCoach().getId().equals(coach_id)) {
            throw new ApiException("this coach cannot accept this booking");
        }

        booking.setBookingPrice(price);
//        booking.setCoach(coach);
        booking.setStatus("Accepted");
        bookCoachRepository.save(booking);
    }

    // extra endpoint 2 - Naelah
    public void rejectBookingRequest(Integer coach_id, Integer booking_id) {
        // check its the same booking and the same coach
        Coach coach = coachRepository.findCoachById(coach_id);
        BookCoach booking = bookCoachRepository.findBookCoachById(booking_id);
        if (coach == null) {
            throw new ApiException("coach not found");
        }
        if (booking == null) {
            throw new ApiException("cant reject booking not created");
        }
        // check if this coach is the same coach in the booking request
        if (!booking.getCoach().getId().equals(coach_id)) {
            throw new ApiException("this coach cannot reject this booking");
        }

        // booking.setCoach(coach);
        booking.setStatus("Rejected");
        bookCoachRepository.save(booking);
    }
    // maybe cancel booking request

    // get all accepted requests
    // extra endpoint 3 - Naelah
    public List<BookCoachOutDTO> getAllAcceptedBookings() {
        List<BookCoach> allBookings = bookCoachRepository.findAll();
        List<BookCoachOutDTO> acceptedBookings = new ArrayList<>();
        for (BookCoach b : allBookings) {
            if (b.getStatus().equalsIgnoreCase("Accepted")) {
                BookCoachOutDTO bookCoachOutDTO = new BookCoachOutDTO(b.getStartDate(), b.getEndDate(), b.getStatus(), b.getBookingPrice(), b.getAthlete().getFullName(), b.getCoach().getFullName());
                acceptedBookings.add(bookCoachOutDTO);
            }
        }
        return acceptedBookings;
    }
}
