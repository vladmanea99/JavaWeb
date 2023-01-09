package com.web.PetCare.repositories;

import com.web.PetCare.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    void deleteById(Long id);

    @Query(nativeQuery = true, value = "select distinct owners.id, owners.last_name, owners.first_name from owners " +
            "join payments on payments.owner_id = owners.id")
    List<Owner> getOwnersThatPaid();
}
