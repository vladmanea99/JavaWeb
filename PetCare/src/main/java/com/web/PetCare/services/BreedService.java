package com.web.PetCare.services;

import com.web.PetCare.dtos.BreedDTO;
import com.web.PetCare.mappers.BreedMapper;
import com.web.PetCare.models.Breed;
import com.web.PetCare.repositories.BreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BreedService {

    private BreedRepository breedRepository;
    private BreedMapper breedMapper;

    @Autowired
    public BreedService(BreedRepository breedRepository, BreedMapper breedMapper) {
        this.breedRepository = breedRepository;
        this.breedMapper = breedMapper;
    }
    public List<BreedDTO> getBreeds() {
        return breedRepository.findAll()
                .stream()
                .map(breedMapper::breedToBreedDto)
                .collect(Collectors.toList());
    }

    public BreedDTO createBreed(BreedDTO breedDTO) {
        Breed savedBreed = breedRepository.saveAndFlush(breedMapper.breedDtoToBreed(breedDTO));
        return breedMapper.breedToBreedDto(savedBreed);
    }

    public void deleteBreed(Long id) {
        breedRepository.deleteById(id);
    }
}
