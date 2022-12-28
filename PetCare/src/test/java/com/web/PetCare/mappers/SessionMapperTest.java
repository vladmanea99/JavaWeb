package com.web.PetCare.mappers;

import com.web.PetCare.dtos.*;
import com.web.PetCare.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SessionMapperImpl.class, PetMapperImpl.class, BreedMapperImpl.class, OwnerMapperImpl.class, TreatmentMapperImpl.class})
public class SessionMapperTest {

    @Autowired
    SessionMapper sessionMapper;

    @Test
    public void sessionToSessionDtoTest() {
        final Session session = new Session(
                1L,
                LocalDateTime.now(),
                new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea")),
                new Treatment(29L, "maggots removal", null)
        );

        SessionDTO actualSessionDto = sessionMapper.sessionToSessionDto(session);

        assertEquals(session.getId(), actualSessionDto.getId());
        assertEquals(session.getSessionDate(), actualSessionDto.getSessionDate().toLocalDateTime());
        assertEquals(session.getTreatment().getId(), actualSessionDto.getTreatment().getId());
        assertEquals(session.getTreatment().getName(), actualSessionDto.getTreatment().getName());
        assertEquals(session.getTreatment().getDescription(), actualSessionDto.getTreatment().getDescription());
        assertEquals(session.getPet().getId(), actualSessionDto.getPet().getId());
        assertEquals(session.getPet().getName(), actualSessionDto.getPet().getName());
        assertEquals(session.getPet().getBreed().getId(), actualSessionDto.getPet().getBreed().getId());
        assertEquals(session.getPet().getBreed().getName(), actualSessionDto.getPet().getBreed().getName());
        assertEquals(session.getPet().getBreed().getDescription(), actualSessionDto.getPet().getBreed().getDescription());
        assertEquals(session.getPet().getOwner().getId(), actualSessionDto.getPet().getOwner().getId());
        assertEquals(session.getPet().getOwner().getFirstName(), actualSessionDto.getPet().getOwner().getFirstName());
        assertEquals(session.getPet().getOwner().getLastName(), actualSessionDto.getPet().getOwner().getLastName());

    }

    @Test
    public void sessionDtoToSessionTest() {
        final SessionDTO sessionDTO = new SessionDTO()
                .id(1L)
                .sessionDate(OffsetDateTime.now())
                .pet(new PetDTO()
                        .id(1L)
                        .name("Aron")
                        .breed(new BreedDTO().id(1L).name("rottweiler").description("best breed"))
                        .owner(new OwnerDTO().id(2L).firstName("Vlad").lastName("Manea")))
                .treatment(new TreatmentDTO()
                .id(1L)
                .name("massage")
                .description(null));

        final Session actualSession = sessionMapper.sessionDtoToSession(sessionDTO);

        assertEquals(sessionDTO.getId(), actualSession.getId());
        assertEquals(sessionDTO.getSessionDate().toLocalDateTime(), actualSession.getSessionDate());
        assertEquals(sessionDTO.getTreatment().getId(), actualSession.getTreatment().getId());
        assertEquals(sessionDTO.getTreatment().getName(), actualSession.getTreatment().getName());
        assertEquals(sessionDTO.getTreatment().getDescription(), actualSession.getTreatment().getDescription());
        assertEquals(sessionDTO.getPet().getId(), actualSession.getPet().getId());
        assertEquals(sessionDTO.getPet().getName(), actualSession.getPet().getName());
        assertEquals(sessionDTO.getPet().getBreed().getId(), actualSession.getPet().getBreed().getId());
        assertEquals(sessionDTO.getPet().getBreed().getName(), actualSession.getPet().getBreed().getName());
        assertEquals(sessionDTO.getPet().getBreed().getDescription(), actualSession.getPet().getBreed().getDescription());
        assertEquals(sessionDTO.getPet().getOwner().getId(), actualSession.getPet().getOwner().getId());
        assertEquals(sessionDTO.getPet().getOwner().getFirstName(), actualSession.getPet().getOwner().getFirstName());
        assertEquals(sessionDTO.getPet().getOwner().getLastName(), actualSession.getPet().getOwner().getLastName());

    }

}
