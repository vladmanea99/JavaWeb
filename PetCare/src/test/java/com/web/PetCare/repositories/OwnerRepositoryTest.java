package com.web.PetCare.repositories;

import com.web.PetCare.models.Owner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    public void getAllOwnersTest() {
        List<Owner> owners = ownerRepository.findAll();
        assertTrue(owners.size() > 0);
    }

    @Test
    public void createOwnerTest() {
        Owner owner = new Owner(null, "Bob", "Ross");
        Owner savedOwner = ownerRepository.saveAndFlush(owner);
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
        assertEquals(owner.getFirstName(), savedOwner.getFirstName());
        assertEquals(owner.getLastName(), savedOwner.getLastName());
    }

}
