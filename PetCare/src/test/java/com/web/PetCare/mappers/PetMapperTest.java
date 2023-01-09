package com.web.PetCare.mappers;

import com.web.PetCare.dtos.BreedDTO;
import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.dtos.PetDTO;
import com.web.PetCare.models.Breed;
import com.web.PetCare.models.Owner;
import com.web.PetCare.models.Pet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PetMapperImpl.class, BreedMapperImpl.class, OwnerMapperImpl.class})
public class PetMapperTest {

    @Autowired
    PetMapper petMapper;

    @Test
    public void petToPetDtoTest() {
        final Pet pet = new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea"));
        final PetDTO actualPetDto = petMapper.petToPetDto(pet);

        assertEquals(pet.getId(), actualPetDto.getId());
        assertEquals(pet.getName(), actualPetDto.getName());
        assertEquals(pet.getBreed().getId(), actualPetDto.getBreed().getId());
        assertEquals(pet.getBreed().getName(), actualPetDto.getBreed().getName());
        assertEquals(pet.getBreed().getDescription(), actualPetDto.getBreed().getDescription());
        assertEquals(pet.getOwner().getId(), actualPetDto.getOwner().getId());
        assertEquals(pet.getOwner().getFirstName(), actualPetDto.getOwner().getFirstName());
        assertEquals(pet.getOwner().getLastName(), actualPetDto.getOwner().getLastName());
    }

    @Test
    public void petDtoToPetTest() {
        final PetDTO petDTO = new PetDTO()
                .id(1L)
                .name("Aron")
                .breed(new BreedDTO().id(1L).name("rottweiler").description("best breed"))
                .owner(new OwnerDTO().id(2L).firstName("Vlad").lastName("Manea"));

        final Pet actualPet = petMapper.petDtoToPet(petDTO);

        assertEquals(petDTO.getId(), actualPet.getId());
        assertEquals(petDTO.getName(), actualPet.getName());
        assertEquals(petDTO.getBreed().getId(), actualPet.getBreed().getId());
        assertEquals(petDTO.getBreed().getName(), actualPet.getBreed().getName());
        assertEquals(petDTO.getBreed().getDescription(), actualPet.getBreed().getDescription());
        assertEquals(petDTO.getOwner().getId(), actualPet.getOwner().getId());
        assertEquals(petDTO.getOwner().getFirstName(), actualPet.getOwner().getFirstName());
        assertEquals(petDTO.getOwner().getLastName(), actualPet.getOwner().getLastName());
    }

}
