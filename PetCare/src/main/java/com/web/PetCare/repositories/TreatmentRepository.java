package com.web.PetCare.repositories;

import com.web.PetCare.models.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
}
