package com.web.PetCare.repositories;

import com.web.PetCare.models.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    void deleteById(Long id);

    @Query(nativeQuery = true, value = "select distinct treatments.id, treatments.name, treatments.description from pets " +
            "join sessions on sessions.pet_id = pets.id " +
            "join treatments on treatments.id = sessions.treatment_id " +
            "where pets.id = :petId")
    List<Treatment> getTreatmentsPerPet(@Param("petId") Long petId);

}
