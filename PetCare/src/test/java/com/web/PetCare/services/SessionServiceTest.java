package com.web.PetCare.services;

import com.google.common.collect.ImmutableList;
import com.web.PetCare.dtos.*;
import com.web.PetCare.mappers.SessionMapper;
import com.web.PetCare.models.*;
import com.web.PetCare.repositories.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class SessionServiceTest {

    @MockBean
    private SessionRepository sessionRepository;

    @MockBean
    private SessionMapper sessionMapper;

    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        sessionService = new SessionService(sessionRepository, sessionMapper);
    }

    @Test
    public void getAllSessionsTest() {
        final List<SessionDTO> sessionDTOList = SESSION_DTO_LIST;
        final List<Session> sessionList = SESSION_LIST;

        when(sessionRepository.findAll()).thenReturn(sessionList);
        when(sessionMapper.sessionToSessionDto(eq(sessionList.get(0)))).thenReturn(sessionDTOList.get(0));

        List<SessionDTO> actualSessionDtoList = sessionService.getSessions();

        assertNotNull(actualSessionDtoList);

        assertEquals(sessionDTOList.get(0).getId(), actualSessionDtoList.get(0).getId());
        assertEquals(sessionDTOList.get(0).getSessionDate().toLocalDateTime(), actualSessionDtoList.get(0).getSessionDate().toLocalDateTime());
        assertEquals(sessionDTOList.get(0).getTreatment().getId(), actualSessionDtoList.get(0).getTreatment().getId());
        assertEquals(sessionDTOList.get(0).getTreatment().getName(), actualSessionDtoList.get(0).getTreatment().getName());
        assertEquals(sessionDTOList.get(0).getTreatment().getDescription(), actualSessionDtoList.get(0).getTreatment().getDescription());
        assertEquals(sessionDTOList.get(0).getPet().getId(), actualSessionDtoList.get(0).getPet().getId());
        assertEquals(sessionDTOList.get(0).getPet().getName(), actualSessionDtoList.get(0).getPet().getName());
        assertEquals(sessionDTOList.get(0).getPet().getBreed().getId(), actualSessionDtoList.get(0).getPet().getBreed().getId());
        assertEquals(sessionDTOList.get(0).getPet().getBreed().getName(), actualSessionDtoList.get(0).getPet().getBreed().getName());
        assertEquals(sessionDTOList.get(0).getPet().getBreed().getDescription(), actualSessionDtoList.get(0).getPet().getBreed().getDescription());
        assertEquals(sessionDTOList.get(0).getPet().getOwner().getId(), actualSessionDtoList.get(0).getPet().getOwner().getId());
        assertEquals(sessionDTOList.get(0).getPet().getOwner().getFirstName(), actualSessionDtoList.get(0).getPet().getOwner().getFirstName());
        assertEquals(sessionDTOList.get(0).getPet().getOwner().getLastName(), actualSessionDtoList.get(0).getPet().getOwner().getLastName());

    }

    @Test
    public void createSessionTest() {
        final SessionDTO entrySessionDto = defaultEntrySessionDto();
        final Session entrySession = defaultEntrySession();

        final SessionDTO exitSessionDto = defaultExitSessionDto();
        final Session exitSession = defaultExitSession();

        when(sessionMapper.sessionDtoToSession(any())).thenReturn(entrySession);
        when(sessionRepository.saveAndFlush(any())).thenReturn(exitSession);
        when(sessionMapper.sessionToSessionDto(any())).thenReturn(exitSessionDto);

        final SessionDTO actualSessionDto = sessionService.createSession(entrySessionDto);
        assertNotNull(actualSessionDto);
        assertEquals(exitSessionDto.getId(), actualSessionDto.getId());
        assertEquals(exitSessionDto.getSessionDate(), actualSessionDto.getSessionDate());
        assertEquals(exitSessionDto.getPet().getId(), actualSessionDto.getPet().getId());
        assertEquals(exitSessionDto.getPet().getName(), actualSessionDto.getPet().getName());
        assertEquals(exitSessionDto.getTreatment().getId(), actualSessionDto.getTreatment().getId());
        assertEquals(exitSessionDto.getTreatment().getName(), actualSessionDto.getTreatment().getName());
        assertEquals(exitSessionDto.getTreatment().getDescription(), actualSessionDto.getTreatment().getDescription());
    }

    private static final List<SessionDTO> SESSION_DTO_LIST = ImmutableList.of(
            new SessionDTO()
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
                            .description(null))
    );

    private static final List<Session> SESSION_LIST = ImmutableList.of(
            new Session(
                    1L,
                    LocalDateTime.now(),
                    new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea")),
                    new Treatment(29L, "maggots removal", null)
            )
    );

    private SessionDTO defaultEntrySessionDto() {
       return new SessionDTO()
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
    }

    private SessionDTO defaultExitSessionDto() {
        return new SessionDTO()
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
    }

    private Session defaultExitSession() {
        return new Session(
                1L,
                LocalDateTime.now(),
                new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea")),
                new Treatment(29L, "maggots removal", null)
        );
    }

    private Session defaultEntrySession() {
        return new Session(
                null,
                LocalDateTime.now(),
                new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea")),
                new Treatment(29L, "maggots removal", null)
        );
    }
}
