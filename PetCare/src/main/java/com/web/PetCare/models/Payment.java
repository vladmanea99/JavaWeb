package com.web.PetCare.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "PAYMENTS"
)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "PAYMENT_DATE")
    private LocalDateTime paymentDate;

    @Column(name = "AMOUNT")
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Owner owner;

}
