package com.web.PetCare.controllers;

import com.web.PetCare.dtos.SessionDTO;
import com.web.PetCare.services.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-12-28T15:44:42.567696200+02:00[Europe/Bucharest]")
@Controller
@RequestMapping("${openapi.petCareService.base-path:}")
public class SessionsApiController implements SessionsApi {

    private final NativeWebRequest request;

    private final SessionService sessionService;

    @org.springframework.beans.factory.annotation.Autowired
    public SessionsApiController(NativeWebRequest request, SessionService sessionService) {
        this.request = request;
        this.sessionService = sessionService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<SessionDTO>> getSessions() {
        List<SessionDTO> sessionDTOS = sessionService.getSessions();
        return ResponseEntity.status(HttpStatus.OK).body(sessionDTOS);
    }

    @Override
    public ResponseEntity<SessionDTO> createSession(@Valid SessionDTO sessionDTO) {
        SessionDTO savedSessionDto = sessionService.createSession(sessionDTO);
        return new ResponseEntity<>(savedSessionDto, HttpStatus.CREATED);
    }

}
