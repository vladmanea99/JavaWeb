package com.web.PetCare.mappers;

import com.web.PetCare.dtos.PetDTO;
import com.web.PetCare.models.Pet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BreedMapper.class, OwnerMapper.class})
public interface PetMapper {
    Pet petDtoToPet(PetDTO petDTO);
    PetDTO petToPetDto(Pet pet);
}
