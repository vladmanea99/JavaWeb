package com.web.PetCare.controllers;

import com.web.PetCare.dtos.BreedDTO;
import com.web.PetCare.models.Breed;
import com.web.PetCare.services.BreedService;
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
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-11-26T10:58:47.748312900+02:00[Europe/Bucharest]")
@Controller
@RequestMapping("${openapi.petCareService.base-path:}")
public class BreedsApiController implements BreedsApi {

    private final NativeWebRequest request;

    private final BreedService breedService;

    @org.springframework.beans.factory.annotation.Autowired
    public BreedsApiController(NativeWebRequest request, BreedService breedService) {
        this.request = request;
        this.breedService = breedService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<BreedDTO>> getBreeds() {
        return ResponseEntity.status(HttpStatus.OK).body(breedService.getBreeds());
    }

    @Override
    public ResponseEntity<BreedDTO> createBreed(@Valid BreedDTO breedDto) {
        BreedDTO savedBreedDto = breedService.createBreed(breedDto);
        return new ResponseEntity<>(savedBreedDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteBreed(@PathVariable("id") Long id) {
        breedService.deleteBreed(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
