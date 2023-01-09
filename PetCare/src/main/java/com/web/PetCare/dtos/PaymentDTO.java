package com.web.PetCare.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * PaymentDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-01-07T11:56:57.209576500+02:00[Europe/Bucharest]")
public class PaymentDTO {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("amount")
  private Integer amount;

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
  @ApiModelProperty(example = "1", value = "")


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PaymentDTO amount(Integer amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  */
  @ApiModelProperty(example = "1", required = true, value = "")
  @NotNull


  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
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
        Objects.equals(this.amount, payment.amount) &&
        Objects.equals(this.session, payment.session) &&
        Objects.equals(this.owner, payment.owner) &&
        Objects.equals(this.paymentDate, payment.paymentDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, amount, session, owner, paymentDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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

