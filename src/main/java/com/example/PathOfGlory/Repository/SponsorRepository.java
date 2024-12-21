// Osama Alghamdi

package com.example.PathOfGlory.Repository;
import com.example.PathOfGlory.Model.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {
    Sponsor findSponsorById(Integer shipId);
}