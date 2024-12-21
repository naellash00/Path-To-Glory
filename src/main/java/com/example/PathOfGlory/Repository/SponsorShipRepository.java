// Osama Alghamdi

package com.example.PathOfGlory.Repository;
import com.example.PathOfGlory.Model.SponsorShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorShipRepository extends JpaRepository<SponsorShip, Integer> {
    SponsorShip findSponsorShipById(Integer id);
}