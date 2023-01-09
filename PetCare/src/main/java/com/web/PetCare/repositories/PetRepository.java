package com.web.PetCare.repositories;

import com.web.PetCare.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    void deleteById(Long id);

    @Query(nativeQuery = true, value = "select distinct pets.id, pets.name, pets.breed_id, pets.owner_id from pets " +
            "join sessions on sessions.pet_id = pets.id;")
    List<Pet> getPetsWithTreatments();

}
