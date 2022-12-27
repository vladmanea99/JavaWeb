package com.web.PetCare.mappers;

import com.web.PetCare.dtos.BreedDTO;
import com.web.PetCare.models.Breed;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BreedMapperImpl.class})
public class BreedMapperTest {

    @Autowired
    BreedMapper breedMapper;

    @Test
    public void breedToBreedDtoTest() {
        final Breed breed = new Breed(1L, "rottweiler", "Very friendly breed");
        final BreedDTO actualBreedDto = breedMapper.breedToBreedDto(breed);

        assertEquals(breed.getId(), actualBreedDto.getId());
        assertEquals(breed.getName(), actualBreedDto.getName());
        assertEquals(breed.getDescription(), actualBreedDto.getDescription());
    }

    @Test
    public void breedDtoToBreedTest() {
        final BreedDTO breedDto = new BreedDTO()
                .id(1L)
                .name("rottweiler")
                .description("Very friendly breed");
        final Breed actualBreed = breedMapper.breedDtoToBreed(breedDto);

        assertEquals(breedDto.getId(), actualBreed.getId());
        assertEquals(breedDto.getName(), actualBreed.getName());
        assertEquals(breedDto.getDescription(), actualBreed.getDescription());
    }

}
