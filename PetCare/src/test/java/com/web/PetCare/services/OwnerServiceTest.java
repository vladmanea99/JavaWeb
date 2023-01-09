package com.web.PetCare.services;

import com.google.common.collect.ImmutableList;
import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.mappers.OwnerMapper;
import com.web.PetCare.models.Owner;
import com.web.PetCare.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class OwnerServiceTest {

    @MockBean
    private OwnerRepository ownerRepository;

    @MockBean
    private OwnerMapper ownerMapper;

    private OwnerService ownerService;

    @BeforeEach
    public void setUp() {
        ownerService = new OwnerService(ownerRepository, ownerMapper);
    }

    @Test
    public void getAllOwnersTest() {
        final List<OwnerDTO> ownerDTOList = OWNER_DTO_LIST;
        final List<Owner> ownerList = OWNER_LIST;

        when(ownerRepository.findAll()).thenReturn(ownerList);
        when(ownerMapper.ownerToOwnerDto(eq(ownerList.get(0)))).thenReturn(ownerDTOList.get(0));
        when(ownerMapper.ownerToOwnerDto(eq(ownerList.get(1)))).thenReturn(ownerDTOList.get(1));

        List<OwnerDTO> actualOwnerDtoList = ownerService.getOwners();

        assertNotNull(actualOwnerDtoList);
        assertEquals(2, actualOwnerDtoList.size());
        assertEquals(ownerDTOList.get(0).getId(), actualOwnerDtoList.get(0).getId());
        assertEquals(ownerDTOList.get(1).getId(), actualOwnerDtoList.get(1).getId());

    }

    @Test
    public void createOwnerTest() {
        final OwnerDTO entryOwnerDto = defaultEntryOwnerDto();
        final Owner entryOwner = defaultEntryOwner();

        final OwnerDTO exitOwnerDto = defaultExitOwnerDto();
        final Owner exitOwner = defaultExitOwner();

        when(ownerMapper.ownerDtoToOwner(any())).thenReturn(entryOwner);
        when(ownerRepository.saveAndFlush(any())).thenReturn(exitOwner);
        when(ownerMapper.ownerToOwnerDto(eq(exitOwner))).thenReturn(exitOwnerDto);

        final OwnerDTO ownerDTO = ownerService.createOwner(entryOwnerDto);

        assertNotNull(ownerDTO);
        assertEquals(exitOwnerDto.getId(), ownerDTO.getId());
        assertEquals(exitOwnerDto.getFirstName(), ownerDTO.getFirstName());
        assertEquals(exitOwnerDto.getLastName(), ownerDTO.getLastName());
    }

    @Test
    public void deleteOwner() {
        doNothing().when(ownerRepository).deleteById(any());
        ownerService.deleteOwner(1L);
        verify(ownerRepository, times(1)).deleteById(any());
    }

    @Test
    public void getOwnersThatPaid() {
        final List<OwnerDTO> ownerDTOList = OWNER_DTO_LIST;
        final List<Owner> ownerList = OWNER_LIST;

        when(ownerRepository.getOwnersThatPaid()).thenReturn(ownerList);
        when(ownerMapper.ownerToOwnerDto(eq(ownerList.get(0)))).thenReturn(ownerDTOList.get(0));
        when(ownerMapper.ownerToOwnerDto(eq(ownerList.get(1)))).thenReturn(ownerDTOList.get(1));

        List<OwnerDTO> actualOwnerDtoList = ownerService.getOwnersThatPaid();

        assertNotNull(actualOwnerDtoList);
        assertEquals(2, actualOwnerDtoList.size());
        assertEquals(ownerDTOList.get(0).getId(), actualOwnerDtoList.get(0).getId());
        assertEquals(ownerDTOList.get(1).getId(), actualOwnerDtoList.get(1).getId());
    }

    private OwnerDTO defaultEntryOwnerDto() {
        return new OwnerDTO().firstName("John").lastName("Doe");
    }

    private OwnerDTO defaultExitOwnerDto() {
        return new OwnerDTO().id(1L).firstName("John").lastName("Doe");
    }

    private Owner defaultEntryOwner() {
        return new Owner(null, "John", "Doe");
    }

    private Owner defaultExitOwner() {
        return new Owner(1L, "John", "Doe");
    }

    private static final List<OwnerDTO> OWNER_DTO_LIST = ImmutableList.of(
            new OwnerDTO().id(1L).firstName("John").lastName("Doe"),
            new OwnerDTO().id(2L).firstName("Alex").lastName("Smith")
    );
    private static final List<Owner> OWNER_LIST = ImmutableList.of(
            new Owner(1L, "John", "Doe"),
            new Owner(2L, "Alex", "Smith")
    );
}
