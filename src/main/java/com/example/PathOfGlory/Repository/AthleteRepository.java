package com.example.PathOfGlory.Repository;

import com.example.PathOfGlory.Model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Integer> {
    Athlete findAthleteById(Integer id);

    List<Athlete> findAthleteBySportTypeAndCity(String sportType, String city);

    //Athlete findAthleteByUsername(String username);
}