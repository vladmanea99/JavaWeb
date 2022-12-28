package com.web.PetCare.services;

import com.google.common.collect.ImmutableList;
import com.web.PetCare.dtos.BreedDTO;
import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.dtos.PetDTO;
import com.web.PetCare.mappers.PetMapper;
import com.web.PetCare.mappers.PetMapperTest;
import com.web.PetCare.models.Breed;
import com.web.PetCare.models.Owner;
import com.web.PetCare.models.Pet;
import com.web.PetCare.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PetServiceTest {

    @MockBean
    private PetRepository petRepository;

    @MockBean
    private PetMapper petMapper;

    private PetService petService;

    @BeforeEach
    public void setUp() {
        petService = new PetService(petRepository, petMapper);
    }

    @Test
    public void getAllPets() {
        final List<PetDTO> petDTOList = PET_DTO_LIST;
        final List<Pet> petList = PET_LIST;

        when(petRepository.findAll()).thenReturn(petList);
        when(petMapper.petToPetDto(eq(petList.get(0)))).thenReturn(petDTOList.get(0));

        List<PetDTO> actualPetDtoList = petService.getAllPets();

        assertNotNull(actualPetDtoList);
        assertEquals(1, actualPetDtoList.size());
        assertEquals(petDTOList.get(0).getId(), actualPetDtoList.get(0).getId());
        assertEquals(petDTOList.get(0).getName(), actualPetDtoList.get(0).getName());
        assertEquals(petDTOList.get(0).getBreed().getId(), actualPetDtoList.get(0).getBreed().getId());
        assertEquals(petDTOList.get(0).getBreed().getName(), actualPetDtoList.get(0).getBreed().getName());
        assertEquals(petDTOList.get(0).getOwner().getId(), actualPetDtoList.get(0).getOwner().getId());
        assertEquals(petDTOList.get(0).getOwner().getFirstName(), actualPetDtoList.get(0).getOwner().getFirstName());
    }

    @Test
    public void createPetTest() {
        final PetDTO entryPetDto = defaultEntryPetDto();
        final Pet entryPet = defaultEntryPet();

        final PetDTO exitPetDto = defaultExitPetDto();
        final Pet exitPet = defaultExitPet();

        when(petMapper.petDtoToPet(any())).thenReturn(entryPet);
        when(petRepository.saveAndFlush((any()))).thenReturn(exitPet);
        when(petMapper.petToPetDto(any())).thenReturn(exitPetDto);

        final PetDTO actualPetDto = petService.createPet(entryPetDto);

        assertNotNull(actualPetDto);
        assertEquals(exitPetDto.getId(), actualPetDto.getId());
        assertEquals(exitPetDto.getName(), actualPetDto.getName());
        assertEquals(exitPetDto.getBreed().getId(), actualPetDto.getBreed().getId());
        assertEquals(exitPetDto.getBreed().getName(), actualPetDto.getBreed().getName());
        assertEquals(exitPetDto.getOwner().getId(), actualPetDto.getOwner().getId());
        assertEquals(exitPetDto.getOwner().getFirstName(), actualPetDto.getOwner().getFirstName());
    }

    private PetDTO defaultEntryPetDto() {
        return new PetDTO()
                .name("Aron")
                .breed(new BreedDTO().id(1L).name("rottweiler").description("best breed"))
                .owner(new OwnerDTO().id(2L).firstName("Vlad").lastName("Manea"));
    }

    private PetDTO defaultExitPetDto() {
        return new PetDTO()
                .id(1L)
                .name("Aron")
                .breed(new BreedDTO().id(1L).name("rottweiler").description("best breed"))
                .owner(new OwnerDTO().id(2L).firstName("Vlad").lastName("Manea"));
    }

    private Pet defaultEntryPet() {
        return new Pet(null, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea"));
    }

    private Pet defaultExitPet() {
        return new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea"));
    }

    private static final List<PetDTO> PET_DTO_LIST = ImmutableList.of(
    new PetDTO()
            .id(1L)
            .name("Aron")
            .breed(new BreedDTO().id(1L).name("rottweiler").description("best breed"))
            .owner(new OwnerDTO().id(2L).firstName("Vlad").lastName("Manea"))
    );

    private static final List<Pet> PET_LIST = ImmutableList.of(
    new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea"))
    );
}
