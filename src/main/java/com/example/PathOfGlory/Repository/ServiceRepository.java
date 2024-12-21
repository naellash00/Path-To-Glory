package com.example.PathOfGlory.Repository;

import com.example.PathOfGlory.Model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Integer> { //Renad
    Service findServiceById(Integer id);

    @Query("select o from Service o where o.pricePerDay>=?1 and o.pricePerDay<=?2")
    List<Service> getServicesByPriceRange(Double minPrice, Double maxPrice);

    List<Service> findServicesByArenaId(Integer arenaId);
}