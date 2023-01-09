package com.web.PetCare.mappers;

import com.web.PetCare.dtos.TreatmentDTO;
import com.web.PetCare.models.Treatment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreatmentMapper {

    Treatment treatmentDtoToTreatment(TreatmentDTO treatmentDTO);

    TreatmentDTO treatmentToTreatmentDto(Treatment treatment);
}
