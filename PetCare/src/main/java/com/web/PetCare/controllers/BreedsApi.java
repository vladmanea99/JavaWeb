/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.web.PetCare.controllers;

import com.web.PetCare.dtos.BreedDTO;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-01-08T11:52:17.105346100+02:00[Europe/Bucharest]")
@Validated
@Api(value = "breeds", description = "the breeds API")
public interface BreedsApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /breeds/breed : Create a breed
     *
     * @param breedDTO Create a breed (required)
     * @return Successful operation (status code 201)
     *         or Bad Request (status code 400)
     *         or Not Found (status code 404)
     */
    @ApiOperation(value = "Create a breed", nickname = "createBreed", notes = "", response = BreedDTO.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Successful operation", response = BreedDTO.class),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found") })
    @PostMapping(
        value = "/breeds/breed",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<BreedDTO> createBreed(@ApiParam(value = "Create a breed" ,required=true )  @Valid @RequestBody BreedDTO breedDTO) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"rottweiler\", \"description\" : \"Dog breed black and brown, seems vicious but are the best cuddlers\", \"id\" : 1 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /breeds/breed/{id} : Delete a breed
     *
     * @param id  (required)
     * @return Successful operation (status code 204)
     *         or Bad Request (status code 400)
     *         or Not Found (status code 404)
     */
    @ApiOperation(value = "Delete a breed", nickname = "deleteBreed", notes = "", tags={ "Breeds", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Successful operation"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found") })
    @DeleteMapping(
        value = "/breeds/breed/{id}"
    )
    default ResponseEntity<Void> deleteBreed(@ApiParam(value = "",required=true) @PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /breeds/breed : List all breeds
     *
     * @return Successful operation (status code 200)
     *         or Bad Request (status code 400)
     *         or Not Found (status code 404)
     */
    @ApiOperation(value = "List all breeds", nickname = "getBreeds", notes = "", response = BreedDTO.class, responseContainer = "List", tags={ "Breeds", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = BreedDTO.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found") })
    @GetMapping(
        value = "/breeds/breed",
        produces = { "application/json" }
    )
    default ResponseEntity<List<BreedDTO>> getBreeds() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"rottweiler\", \"description\" : \"Dog breed black and brown, seems vicious but are the best cuddlers\", \"id\" : 1 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
