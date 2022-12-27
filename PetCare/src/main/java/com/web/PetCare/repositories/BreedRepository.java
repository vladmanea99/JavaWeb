package com.web.PetCare.repositories;

import com.web.PetCare.models.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Long> {
}
