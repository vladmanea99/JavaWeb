package com.web.PetCare.repositories;

import com.web.PetCare.models.Breed;
import com.web.PetCare.models.Owner;
import com.web.PetCare.models.Pet;
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
public class PetRepositoryTest {

    @Autowired
    PetRepository petRepository;

    @Test
    public void getAllPetsTest() {
        List<Pet> pets = petRepository.findAll();
        assertTrue(pets.size() > 0);
    }

    @Test
    public void createPetTest() {
        Pet pet = new Pet(null, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea"));
        Pet savedPet = petRepository.saveAndFlush(pet);
        assertNotNull(savedPet);
        assertNotNull(savedPet.getId());
        assertEquals(pet.getName(), savedPet.getName());
        assertEquals(pet.getBreed().getId(), savedPet.getBreed().getId());
        assertEquals(pet.getBreed().getName(), savedPet.getBreed().getName());
        assertEquals(pet.getOwner().getId(), savedPet.getOwner().getId());
        assertEquals(pet.getOwner().getFirstName(), savedPet.getOwner().getFirstName());
    }

}
