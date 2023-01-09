package com.web.PetCare.repositories;

import com.web.PetCare.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    void deleteById(Long id);

    @Query(nativeQuery = true, value = "select sum(payments.amount) from pets " +
            "join sessions on sessions.pet_id = pets.id " +
            "join payments on payments.session_id = sessions.id " +
            "where pets.id = :petId ")
    Integer getTotalAmountByPetId(@Param("petId") Long petId);

}
