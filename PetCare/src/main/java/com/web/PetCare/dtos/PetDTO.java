package com.web.PetCare.dtos;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PetDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-11-16T19:27:11.936966+02:00[Europe/Bucharest]")
public class PetDTO   {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("breedId")
  private Long breedId;

  @JsonProperty("ownerId")
  private Long ownerId;

  public PetDTO id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PetDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PetDTO breedId(Long breedId) {
    this.breedId = breedId;
    return this;
  }

  /**
   * Get breedId
   * @return breedId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getBreedId() {
    return breedId;
  }

  public void setBreedId(Long breedId) {
    this.breedId = breedId;
  }

  public PetDTO ownerId(Long ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  /**
   * Get ownerId
   * @return ownerId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PetDTO pet = (PetDTO) o;
    return Objects.equals(this.id, pet.id) &&
        Objects.equals(this.name, pet.name) &&
        Objects.equals(this.breedId, pet.breedId) &&
        Objects.equals(this.ownerId, pet.ownerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, breedId, ownerId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PetDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    breedId: ").append(toIndentedString(breedId)).append("\n");
    sb.append("    ownerId: ").append(toIndentedString(ownerId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

