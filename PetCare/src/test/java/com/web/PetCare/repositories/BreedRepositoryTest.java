package com.web.PetCare.repositories;

import com.web.PetCare.models.Breed;
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
public class BreedRepositoryTest {

    @Autowired
    BreedRepository breedRepository;

    @Test
    public void getAllBreedsTest() {
        List<Breed> breeds = breedRepository.findAll();
        assertTrue(breeds.size() > 0);
    }

    @Test
    public void createBreedTest() {
        Breed breed = new Breed(null, "rottweiler", "Very friendly breed");
        Breed savedBreed = breedRepository.saveAndFlush(breed);
        assertNotNull(savedBreed);
        assertNotNull(savedBreed.getId());
        assertEquals(breed.getName(), savedBreed.getName());
        assertEquals(breed.getDescription(), savedBreed.getDescription());
    }

}
