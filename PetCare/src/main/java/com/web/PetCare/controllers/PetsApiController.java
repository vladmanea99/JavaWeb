package com.web.PetCare.controllers;

import com.web.PetCare.dtos.PetDTO;
import com.web.PetCare.services.PetService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-12-28T13:47:20.683681200+02:00[Europe/Bucharest]")
@Controller
@RequestMapping("${openapi.petCareService.base-path:}")
public class PetsApiController implements PetsApi {

    private final NativeWebRequest request;

    private final PetService petService;

    @org.springframework.beans.factory.annotation.Autowired
    public PetsApiController(NativeWebRequest request, PetService petSerivce) {
        this.request = request;
        this.petService = petSerivce;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<PetDTO>> getPets() {
        List<PetDTO> petDTOS = petService.getAllPets();
        return ResponseEntity.status(HttpStatus.OK).body(petDTOS);
    }

    @Override
    public ResponseEntity<PetDTO> createPet(@Valid PetDTO petDTO) {
        PetDTO savedPetDto = petService.createPet(petDTO);
        return new ResponseEntity<>(savedPetDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletePet(@PathVariable("id") Long id) {
        petService.deletePet(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
