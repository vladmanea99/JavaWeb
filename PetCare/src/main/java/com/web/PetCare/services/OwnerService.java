package com.web.PetCare.services;

import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.mappers.OwnerMapper;
import com.web.PetCare.models.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    @Autowired
    OwnerMapper ownerMapper;

    public List<OwnerDTO> getOwners() {
        OwnerDTO ownerDTO1 = new OwnerDTO();
        ownerDTO1.setId(1L);
        ownerDTO1.setFirstName("Vlad");
        ownerDTO1.setLastName("Manea");

        OwnerDTO ownerDTO2 = new OwnerDTO();
        ownerDTO2.setId(2L);
        ownerDTO2.setFirstName("John");
        ownerDTO2.setLastName("Doe");

        return List.of(ownerDTO1, ownerDTO2);
    }

    public OwnerDTO createOwner(OwnerDTO ownerDTO) {
        Owner owner = ownerMapper.ownerDtoToOwner(ownerDTO);
        return ownerMapper.ownerToOwnerDto(owner);
    }

}
