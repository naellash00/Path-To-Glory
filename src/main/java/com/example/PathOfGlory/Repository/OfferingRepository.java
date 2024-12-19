package com.example.PathOfGlory.Repository;

import com.example.PathOfGlory.Model.Offering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferingRepository extends JpaRepository<Offering,Integer> { //Renad
    Offering findOfferingById(Integer id);

    @Query("select o from Offering o where o.price>=?1 and o.price<=?2")
    List<Offering> getOfferingsByPriceRange(Double minPrice, Double maxPrice);
}