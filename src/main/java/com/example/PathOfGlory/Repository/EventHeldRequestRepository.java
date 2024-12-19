// Osama Alghamdi

package com.example.PathOfGlory.Repository;
import com.example.PathOfGlory.Model.EventHeldRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventHeldRequestRepository extends JpaRepository<EventHeldRequest, Integer> {
    EventHeldRequest findEventHeldRequestById(Integer id);
}
