package com.web.PetCare.repositories;

import com.web.PetCare.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    void deleteById(Long id);

}
