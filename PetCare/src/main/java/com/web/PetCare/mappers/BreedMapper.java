package com.web.PetCare.mappers;

import com.web.PetCare.dtos.BreedDTO;
import com.web.PetCare.models.Breed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BreedMapper {
    Breed breedDtoToBreed(BreedDTO breedDTO);
    BreedDTO breedToBreedDto(Breed breed);
}
