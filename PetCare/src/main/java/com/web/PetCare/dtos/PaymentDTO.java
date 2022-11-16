package com.web.PetCare.dtos;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.dtos.SessionDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PaymentDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-11-16T20:20:03.615686600+02:00[Europe/Bucharest]")
public class PaymentDTO   {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("session")
  private SessionDTO session;

  @JsonProperty("owner")
  private OwnerDTO owner;

  @JsonProperty("paymentDate")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime paymentDate;

  public PaymentDTO id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(example = "1", required = true, value = "")
  @NotNull


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PaymentDTO session(SessionDTO session) {
    this.session = session;
    return this;
  }

  /**
   * Get session
   * @return session
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public SessionDTO getSession() {
    return session;
  }

  public void setSession(SessionDTO session) {
    this.session = session;
  }

  public PaymentDTO owner(OwnerDTO owner) {
    this.owner = owner;
    return this;
  }

  /**
   * Get owner
   * @return owner
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OwnerDTO getOwner() {
    return owner;
  }

  public void setOwner(OwnerDTO owner) {
    this.owner = owner;
  }

  public PaymentDTO paymentDate(OffsetDateTime paymentDate) {
    this.paymentDate = paymentDate;
    return this;
  }

  /**
   * Get paymentDate
   * @return paymentDate
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(OffsetDateTime paymentDate) {
    this.paymentDate = paymentDate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentDTO payment = (PaymentDTO) o;
    return Objects.equals(this.id, payment.id) &&
        Objects.equals(this.session, payment.session) &&
        Objects.equals(this.owner, payment.owner) &&
        Objects.equals(this.paymentDate, payment.paymentDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, session, owner, paymentDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    session: ").append(toIndentedString(session)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    paymentDate: ").append(toIndentedString(paymentDate)).append("\n");
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

