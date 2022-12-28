package com.web.PetCare.repositories;

import com.web.PetCare.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SessionRepositoryTest {

    @Autowired
    SessionRepository sessionRepository;

    @Test
    public void getAllSessionsTest() {
        List<Session> sessions = sessionRepository.findAll();
        assertTrue(sessions.size() > 0);
    }

    @Test
    public void createSession() {
        Session session = new Session(
                null,
                LocalDateTime.now(),
                new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea")),
                new Treatment(29L, "maggots removal", null)
                );
        Session savedSession = sessionRepository.saveAndFlush(session);
        assertNotNull(savedSession);
        assertNotNull(savedSession.getId());
        assertEquals(session.getSessionDate(), savedSession.getSessionDate());
        assertEquals(session.getPet().getId(), savedSession.getPet().getId());
        assertEquals(session.getTreatment().getId(), savedSession.getTreatment().getId());
    }

}
