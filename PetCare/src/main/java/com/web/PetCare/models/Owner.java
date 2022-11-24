package com.web.PetCare.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    name = "OWNERS"
)
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

}
