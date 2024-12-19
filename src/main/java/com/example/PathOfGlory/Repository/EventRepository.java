package com.example.PathOfGlory.Repository;

import com.example.PathOfGlory.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> { //Renad
    Event findEventById(Integer id);
    Event findEventByNumber(Integer eventNumber);
    List<Event> findBySponsorIdAndStartDateAfterAndCityAndStatus(Integer sponsorId, LocalDate date,String city, String status);
    List<Event> findByStartDateBetween(Date startDate, Date endDate);
}