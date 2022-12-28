package com.web.PetCare.repositories;

import com.web.PetCare.models.Treatment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TreatmentRepositoryTest {

    @Autowired
    TreatmentRepository treatmentRepository;

    @Test
    public void getAllTreatmentsTest() {
        List<Treatment> treatments = treatmentRepository.findAll();
        assertTrue(treatments.size() > 0);
    }

    @Test
    public void createTreatmentTest() {
        Treatment treatment = new Treatment(null, "maggots removal", null);
        Treatment savedTreatment = treatmentRepository.saveAndFlush(treatment);
        assertNotNull(savedTreatment);
        assertNotNull(savedTreatment.getId());
        assertEquals(treatment.getName(), savedTreatment.getName());
    }

}
