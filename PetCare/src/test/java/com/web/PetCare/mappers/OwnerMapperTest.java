package com.web.PetCare.mappers;

import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.models.Owner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OwnerMapperImpl.class})
public class OwnerMapperTest {

    @Autowired
    OwnerMapper ownerMapper;

    @Test
    public void ownerToOwnerDtoTest() {
        final Owner owner = new Owner(1L, "John", "Doe");
        final OwnerDTO actualOwnerDto = ownerMapper.ownerToOwnerDto(owner);

        assertEquals(owner.getId(), actualOwnerDto.getId());
        assertEquals(owner.getFirstName(), actualOwnerDto.getFirstName());
        assertEquals(owner.getLastName(), actualOwnerDto.getLastName());
    }

    @Test
    public void ownerDtoToOwnerTest() {
        final OwnerDTO ownerDto = new OwnerDTO()
                .id(1L)
                .firstName("John")
                .lastName("Doe");
        final Owner actualOwner = ownerMapper.ownerDtoToOwner(ownerDto);

        assertEquals(ownerDto.getId(), actualOwner.getId());
        assertEquals(ownerDto.getFirstName(), actualOwner.getFirstName());
        assertEquals(ownerDto.getLastName(), actualOwner.getLastName());
    }

}
