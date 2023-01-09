package com.web.PetCare.dtos;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.web.PetCare.dtos.PetDTO;
import com.web.PetCare.dtos.TreatmentDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SessionDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-12-28T00:31:49.697529800+02:00[Europe/Bucharest]")
public class SessionDTO   {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("sessionDate")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime sessionDate;

  @JsonProperty("pet")
  private PetDTO pet;

  @JsonProperty("treatment")
  private TreatmentDTO treatment;

  public SessionDTO id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(example = "1", value = "")


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SessionDTO sessionDate(OffsetDateTime sessionDate) {
    this.sessionDate = sessionDate;
    return this;
  }

  /**
   * Get sessionDate
   * @return sessionDate
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getSessionDate() {
    return sessionDate;
  }

  public void setSessionDate(OffsetDateTime sessionDate) {
    this.sessionDate = sessionDate;
  }

  public SessionDTO pet(PetDTO pet) {
    this.pet = pet;
    return this;
  }

  /**
   * Get pet
   * @return pet
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public PetDTO getPet() {
    return pet;
  }

  public void setPet(PetDTO pet) {
    this.pet = pet;
  }

  public SessionDTO treatment(TreatmentDTO treatment) {
    this.treatment = treatment;
    return this;
  }

  /**
   * Get treatment
   * @return treatment
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TreatmentDTO getTreatment() {
    return treatment;
  }

  public void setTreatment(TreatmentDTO treatment) {
    this.treatment = treatment;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SessionDTO session = (SessionDTO) o;
    return Objects.equals(this.id, session.id) &&
        Objects.equals(this.sessionDate, session.sessionDate) &&
        Objects.equals(this.pet, session.pet) &&
        Objects.equals(this.treatment, session.treatment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, sessionDate, pet, treatment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SessionDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    sessionDate: ").append(toIndentedString(sessionDate)).append("\n");
    sb.append("    pet: ").append(toIndentedString(pet)).append("\n");
    sb.append("    treatment: ").append(toIndentedString(treatment)).append("\n");
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

