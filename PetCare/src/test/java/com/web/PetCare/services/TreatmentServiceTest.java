package com.web.PetCare.services;

import com.google.common.collect.ImmutableList;
import com.web.PetCare.dtos.TreatmentDTO;
import com.web.PetCare.mappers.TreatmentMapper;
import com.web.PetCare.models.Treatment;
import com.web.PetCare.repositories.TreatmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class TreatmentServiceTest {

    @MockBean
    private TreatmentRepository treatmentRepository;

    @MockBean
    private TreatmentMapper treatmentMapper;

    private TreatmentService treatmentService;

    @BeforeEach
    public void setUp() {
        treatmentService = new TreatmentService(treatmentRepository, treatmentMapper);
    }

    @Test
    public void getAllTreatmentsTest() {
        final List<TreatmentDTO> treatmentDTOList = TREATMENT_DTO_LIST;
        final List<Treatment> treatmentList = TREATMENT_LIST;

        when(treatmentRepository.findAll()).thenReturn(treatmentList);
        when(treatmentMapper.treatmentToTreatmentDto(eq(treatmentList.get(0)))).thenReturn(treatmentDTOList.get(0));
        when(treatmentMapper.treatmentToTreatmentDto(eq(treatmentList.get(1)))).thenReturn(treatmentDTOList.get(1));

        List<TreatmentDTO> actualTreatmentDtoList = treatmentService.getTreatments();

        assertNotNull(actualTreatmentDtoList);
        assertEquals(2, actualTreatmentDtoList.size());

        assertEquals(treatmentDTOList.get(0).getId(), actualTreatmentDtoList.get(0).getId());
        assertEquals(treatmentDTOList.get(0).getName(), actualTreatmentDtoList.get(0).getName());
        assertEquals(treatmentDTOList.get(0).getDescription(), actualTreatmentDtoList.get(0).getDescription());

        assertEquals(treatmentDTOList.get(1).getId(), actualTreatmentDtoList.get(1).getId());
        assertEquals(treatmentDTOList.get(1).getName(), actualTreatmentDtoList.get(1).getName());
        assertEquals(treatmentDTOList.get(1).getDescription(), actualTreatmentDtoList.get(1).getDescription());
    }

    @Test
    public void createTreatmentTest() {
        final TreatmentDTO entryTreatmentDto = defaultEntryTreatmentDto();
        final Treatment entryTreatment = defaultEntryTreatment();

        final TreatmentDTO exitTreatmentDto = defaultExitTreatmentDto();
        final Treatment exitTreatment = defaultExitTreatment();

        when(treatmentMapper.treatmentDtoToTreatment(any())).thenReturn(entryTreatment);
        when(treatmentRepository.saveAndFlush((any()))).thenReturn(exitTreatment);
        when(treatmentMapper.treatmentToTreatmentDto(any())).thenReturn(exitTreatmentDto);

        final TreatmentDTO actualTreatmentDto = treatmentService.createTreatment(entryTreatmentDto);

        assertNotNull(actualTreatmentDto);
        assertEquals(exitTreatmentDto.getId(), actualTreatmentDto.getId());
        assertEquals(exitTreatmentDto.getName(), actualTreatmentDto.getName());
        assertEquals(exitTreatmentDto.getDescription(), actualTreatmentDto.getDescription());
    }

    @Test
    public void deleteTreatment() {
        doNothing().when(treatmentRepository).deleteById(any());
        treatmentService.deleteTreatment(1L);
        verify(treatmentRepository, times(1)).deleteById(any());
    }

    private static final List<TreatmentDTO> TREATMENT_DTO_LIST = ImmutableList.of(
            new TreatmentDTO().id(1L).name("massage").description("bath + massage of the fur and body"),
            new TreatmentDTO().id(2L).name("maggots removal").description(null)
    );

    private static final List<Treatment> TREATMENT_LIST = ImmutableList.of(
            new Treatment(1L, "massage", "bath + massage of the fur and body"),
            new Treatment(2L, "maggots removal", null)
    );

    private TreatmentDTO defaultEntryTreatmentDto() {
        return new TreatmentDTO().name("massage").description("bath + massage of the fur and body");
    }

    private TreatmentDTO defaultExitTreatmentDto() {
        return new TreatmentDTO().id(1L).name("massage").description("bath + massage of the fur and body");
    }

    private Treatment defaultEntryTreatment() {
        return new Treatment(null, "massage", "bath + massage of the fur and body");
    }

    private Treatment defaultExitTreatment() {
        return new Treatment(1L, "massage", "bath + massage of the fur and body");
    }
}

