package com.web.PetCare.services;

import com.google.common.collect.ImmutableList;
import com.web.PetCare.dtos.BreedDTO;
import com.web.PetCare.mappers.BreedMapper;
import com.web.PetCare.models.Breed;
import com.web.PetCare.repositories.BreedRepository;
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
public class BreedServiceTest {

    @MockBean
    private BreedRepository breedRepository;

    @MockBean
    private BreedMapper breedMapper;

    private BreedService breedService;

    @BeforeEach
    public void setUp() {
        breedService = new BreedService(breedRepository, breedMapper);
    }

    @Test
    public void getAllBreedsTest() {
        final List<BreedDTO> breedDTOList = BREED_DTO_LIST;
        final List<Breed> breedList = BREED_LIST;

        when(breedRepository.findAll()).thenReturn(breedList);
        when(breedMapper.breedToBreedDto(eq(breedList.get(0)))).thenReturn(breedDTOList.get(0));
        when(breedMapper.breedToBreedDto(eq(breedList.get(1)))).thenReturn(breedDTOList.get(1));

        List<BreedDTO> actualBreedDtoList = breedService.getBreeds();

        assertNotNull(actualBreedDtoList);
        assertEquals(2, actualBreedDtoList.size());
        assertEquals(breedDTOList.get(0).getId(), actualBreedDtoList.get(0).getId());
        assertEquals(breedDTOList.get(0).getName(), actualBreedDtoList.get(0).getName());
        assertEquals(breedDTOList.get(0).getDescription(), actualBreedDtoList.get(0).getDescription());

        assertEquals(breedDTOList.get(1).getId(), actualBreedDtoList.get(1).getId());
        assertEquals(breedDTOList.get(1).getName(), actualBreedDtoList.get(1).getName());
        assertEquals(breedDTOList.get(1).getDescription(), actualBreedDtoList.get(1).getDescription());

    }

    @Test
    public void createBreedTest() {
        final BreedDTO entryBreedDto = defaultEntryBreedDto();
        final Breed entryBreed = defaultEntryBreed();

        final BreedDTO exitBreedDto = defaultExitBreedDto();
        final Breed exitBreed = defaultExitBreed();

        when(breedMapper.breedDtoToBreed(any())).thenReturn(entryBreed);
        when(breedRepository.saveAndFlush((any()))).thenReturn(exitBreed);
        when(breedMapper.breedToBreedDto(any())).thenReturn(exitBreedDto);

        final BreedDTO actualBreedDto = breedService.createBreed(entryBreedDto);

        assertNotNull(actualBreedDto);
        assertEquals(exitBreedDto.getId(), actualBreedDto.getId());
        assertEquals(exitBreedDto.getName(), actualBreedDto.getName());
        assertEquals(exitBreedDto.getDescription(), actualBreedDto.getDescription());
    }

    private BreedDTO defaultEntryBreedDto() {
        return new BreedDTO().name("husky");
    }

    private BreedDTO defaultExitBreedDto() {
        return new BreedDTO().id(1L).name("husky");
    }

    private Breed defaultEntryBreed() {
        return new Breed(null, "husky", null);
    }

    private Breed defaultExitBreed() {
        return new Breed(1L, "husky", null);
    }

    private static final List<BreedDTO> BREED_DTO_LIST = ImmutableList.of(
            new BreedDTO().id(1L).name("rottweiler").description("Very playful breed"),
            new BreedDTO().id(2L).name("labrador").description(null)
    );

    private static final List<Breed> BREED_LIST = ImmutableList.of(
            new Breed(1L, "rottweiler", "Very playful breed"),
            new Breed(2L, "labrador", null)
    );

}
