// Osama Alghamdi

package com.example.PathOfGlory.Repository;
import com.example.PathOfGlory.Model.EventParticipationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParticipationRequestRepository extends JpaRepository<EventParticipationRequest, Integer> {
    EventParticipationRequest findEventParticipationRequestById(Integer id);
    EventParticipationRequest findEventParticipationRequestByAthleteId(Integer id);
}