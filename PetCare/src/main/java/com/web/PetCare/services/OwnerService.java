package com.web.PetCare.services;

import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.mappers.OwnerMapper;
import com.web.PetCare.models.Owner;
import com.web.PetCare.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    OwnerRepository ownerRepository;
    OwnerMapper ownerMapper;

    @Autowired
    public OwnerService(final OwnerRepository ownerRepository, final OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }

    public List<OwnerDTO> getOwners() {
        return ownerRepository.findAll().stream().map(ownerMapper::ownerToOwnerDto).collect(Collectors.toList());
    }

    public OwnerDTO createOwner(OwnerDTO ownerDTO) {
        Owner savedOwner = ownerRepository.saveAndFlush(ownerMapper.ownerDtoToOwner(ownerDTO));
        return ownerMapper.ownerToOwnerDto(savedOwner);
    }

    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }

}
