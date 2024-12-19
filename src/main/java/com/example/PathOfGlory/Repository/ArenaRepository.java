package com.example.PathOfGlory.Repository;

import com.example.PathOfGlory.Model.Arena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArenaRepository extends JpaRepository<Arena, Integer> {  //Renad
    Arena findArenaById(Integer id);
    List<Arena> findArenasByCity(String city);
}
