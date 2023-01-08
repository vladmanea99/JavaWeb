package com.web.PetCare.repositories;

import com.web.PetCare.models.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    void deleteById(Long id);

}
