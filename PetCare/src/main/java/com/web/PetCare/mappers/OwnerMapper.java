package com.web.PetCare.mappers;

import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.models.Owner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OwnerMapper {
    Owner ownerDtoToOwner(OwnerDTO ownerDTO);
    OwnerDTO ownerToOwnerDto(Owner owner);
}
