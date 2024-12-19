package com.example.PathOfGlory.Repository;

import com.example.PathOfGlory.Model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Integer> {
    Athlete findAthleteById(Integer id);

    List<Athlete> findAthleteBySportName(String sportName); // in the same support to make team

    List<Athlete> findAthleteBySportNameAndCity(String sport_name, String city);

    Athlete findAthleteByUsername(String username);
}
