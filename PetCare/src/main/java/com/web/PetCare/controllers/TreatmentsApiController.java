package com.web.PetCare.controllers;

import com.web.PetCare.dtos.TreatmentDTO;
import com.web.PetCare.services.TreatmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-12-28T12:02:22.049058400+02:00[Europe/Bucharest]")
@Controller
@RequestMapping("${openapi.petCareService.base-path:}")
public class TreatmentsApiController implements TreatmentsApi {

    private final NativeWebRequest request;

    private final TreatmentService treatmentService;

    @org.springframework.beans.factory.annotation.Autowired
    public TreatmentsApiController(NativeWebRequest request, TreatmentService treatmentService) {
        this.request = request;
        this.treatmentService = treatmentService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    public ResponseEntity<List<TreatmentDTO>> getTreatments() {
        return ResponseEntity.status(HttpStatus.OK).body(treatmentService.getTreatments());
    }

    public ResponseEntity<TreatmentDTO> createTreatment(@Valid TreatmentDTO treatmentDTO) {
        TreatmentDTO savedTreatmentDto = treatmentService.createTreatment(treatmentDTO);
        return new ResponseEntity<>(savedTreatmentDto, HttpStatus.CREATED);
    }

}
