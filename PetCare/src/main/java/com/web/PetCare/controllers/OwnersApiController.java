package com.web.PetCare.controllers;

import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.services.OwnerService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-11-24T11:53:28.645693600+02:00[Europe/Bucharest]")
@Controller
@RequestMapping("${openapi.petCareService.base-path:}")
public class OwnersApiController implements OwnersApi {

    private final NativeWebRequest request;

    private OwnerService ownerService;

    @org.springframework.beans.factory.annotation.Autowired
    public OwnersApiController(NativeWebRequest request, OwnerService ownerService) {
        this.request = request;
        this.ownerService = ownerService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<OwnerDTO>> getOwners() {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.getOwners());
    }

    @Override
    public ResponseEntity<OwnerDTO> createOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        OwnerDTO savedOwnerDto = ownerService.createOwner(ownerDTO);
        return new ResponseEntity<>(savedOwnerDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteOwner(@PathVariable("id") Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<OwnerDTO>> getOwnersThatPaid() {
        List<OwnerDTO> ownerDTOList = ownerService.getOwnersThatPaid();
        return new ResponseEntity<>(ownerDTOList, HttpStatus.OK);
    }
}
