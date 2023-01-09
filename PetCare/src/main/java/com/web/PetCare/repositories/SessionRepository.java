package com.web.PetCare.repositories;

import com.web.PetCare.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    void deleteById(Long id);

    @Query(nativeQuery = true, value = "select distinct sessions.id, sessions.pet_id, sessions.treatment_id, sessions.session_date from sessions " +
            "join payments where payments.session_id = sessions.id;")
    public List<Session> getSessionsThatGotPaid();
}
