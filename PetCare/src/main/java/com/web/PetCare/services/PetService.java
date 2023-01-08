package com.web.PetCare.services;

import com.web.PetCare.dtos.PetDTO;
import com.web.PetCare.mappers.PetMapper;
import com.web.PetCare.models.Pet;
import com.web.PetCare.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    private PetRepository petRepository;

    private PetMapper petMapper;

    @Autowired
    public PetService(PetRepository petRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    public List<PetDTO> getAllPets() {
        return petRepository.findAll()
                .stream()
                .map(petMapper::petToPetDto)
                .collect(Collectors.toList());
    }

    public PetDTO createPet(PetDTO petDTO) {
        Pet pet = petMapper.petDtoToPet(petDTO);
        return petMapper.petToPetDto(petRepository.saveAndFlush(pet));
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}
