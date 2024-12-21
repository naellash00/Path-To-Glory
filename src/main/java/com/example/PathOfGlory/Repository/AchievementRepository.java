package com.example.PathOfGlory.Repository;

import com.example.PathOfGlory.Model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    Achievement findAchievementById(Integer id);
    List<Achievement> findAchievementByAthlete_Id(Integer athlete_id);
}