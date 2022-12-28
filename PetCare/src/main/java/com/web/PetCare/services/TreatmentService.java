package com.web.PetCare.services;

import com.web.PetCare.dtos.TreatmentDTO;
import com.web.PetCare.mappers.TreatmentMapper;
import com.web.PetCare.models.Treatment;
import com.web.PetCare.repositories.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentService {

    TreatmentRepository treatmentRepository;

    TreatmentMapper treatmentMapper;

    @Autowired
    public TreatmentService(TreatmentRepository treatmentRepository, TreatmentMapper treatmentMapper) {
        this.treatmentRepository = treatmentRepository;
        this.treatmentMapper = treatmentMapper;
    }

    public List<TreatmentDTO> getTreatments() {
        return treatmentRepository.findAll()
                .stream()
                .map(treatmentMapper::treatmentToTreatmentDto)
                .collect(Collectors.toList());
    }

    public TreatmentDTO createTreatment(TreatmentDTO treatmentDTO) {
        Treatment savedTreatment = treatmentRepository.saveAndFlush(treatmentMapper.treatmentDtoToTreatment(treatmentDTO));
        return treatmentMapper.treatmentToTreatmentDto(savedTreatment);
    }
}
