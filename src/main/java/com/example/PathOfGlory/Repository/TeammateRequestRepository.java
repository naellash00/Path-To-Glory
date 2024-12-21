
package com.example.PathOfGlory.Repository;

import com.example.PathOfGlory.Model.Athlete;
import com.example.PathOfGlory.Model.TeammateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeammateRequestRepository extends JpaRepository<TeammateRequest, Integer> {
    TeammateRequest findTeammateRequestById(Integer id);
    TeammateRequest findTeammateRequestBySenderAndReceiver(Athlete sender, Athlete receiver);
}