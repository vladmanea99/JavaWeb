package com.web.PetCare.services;

import com.web.PetCare.dtos.SessionDTO;
import com.web.PetCare.mappers.SessionMapper;
import com.web.PetCare.models.Session;
import com.web.PetCare.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {


    SessionRepository sessionRepository;

    SessionMapper sessionMapper;

    @Autowired
    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
    }


    public List<SessionDTO> getSessions() {
        return sessionRepository.findAll()
                .stream()
                .map(sessionMapper::sessionToSessionDto)
                .collect(Collectors.toList());
    }

    public SessionDTO createSession(SessionDTO sessionDTO) {
        Session savedSession = sessionRepository.saveAndFlush(sessionMapper.sessionDtoToSession(sessionDTO));
        return sessionMapper.sessionToSessionDto(savedSession);
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}
