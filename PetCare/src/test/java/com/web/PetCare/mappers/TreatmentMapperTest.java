package com.web.PetCare.mappers;

import com.web.PetCare.dtos.TreatmentDTO;
import com.web.PetCare.models.Treatment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TreatmentMapperImpl.class})
public class TreatmentMapperTest {

    @Autowired
    TreatmentMapper treatmentMapper;

    @Test
    public void treatmentToTreatmentDtoTest() {
        final Treatment treatment = new Treatment(1L, "massage", null);
        final TreatmentDTO actualTreatmentDTO = treatmentMapper.treatmentToTreatmentDto(treatment);

        assertEquals(treatment.getId(), actualTreatmentDTO.getId());
        assertEquals(treatment.getName(), actualTreatmentDTO.getName());
        assertEquals(treatment.getDescription(), actualTreatmentDTO.getDescription());
    }

    @Test
    public void treatmentDtoToTreatmentTest() {
        final TreatmentDTO treatmentDTO = new TreatmentDTO()
                .id(1L)
                .name("massage")
                .description(null);
        final Treatment actualTreatment = treatmentMapper.treatmentDtoToTreatment(treatmentDTO);

        assertEquals(treatmentDTO.getId(), actualTreatment.getId());
        assertEquals(treatmentDTO.getName(), actualTreatment.getName());
        assertEquals(treatmentDTO.getDescription(), actualTreatment.getDescription());
    }
}
