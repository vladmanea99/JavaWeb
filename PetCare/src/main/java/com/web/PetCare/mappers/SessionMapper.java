package com.web.PetCare.mappers;

import com.web.PetCare.dtos.SessionDTO;
import com.web.PetCare.models.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", uses = {PetMapper.class, TreatmentMapper.class})
public interface SessionMapper {

    @Mapping(target = "sessionDate", source = "sessionDTO", qualifiedByName = "sessionDateToLocalDateTime")
    Session sessionDtoToSession(SessionDTO sessionDTO);

    @Named("sessionDateToLocalDateTime")
    default LocalDateTime mapSessionDateToLocalDateTime(SessionDTO sessionDTO) {
        return sessionDTO.getSessionDate().toLocalDateTime();
    }

    @Mapping(target = "sessionDate", source = "session", qualifiedByName = "sessionDateToOffsetDateTime")
    SessionDTO sessionToSessionDto(Session session);

    @Named("sessionDateToOffsetDateTime")
    default OffsetDateTime mapSessionsDateToOffsetDateTime(Session session) {
        return session.getSessionDate().atOffset(ZoneOffset.UTC);
    }

}
