package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {
	private final Long ownerId = 1L;
	private final String lastName = "Smith";
	private Owner mockOwner;

	@Mock
	private OwnerRepository ownerRepository;

	@InjectMocks
	private OwnerSDJpaService service;

	@BeforeEach
	void setUp() {
		mockOwner = Owner.builder().id(ownerId).lastName(lastName).build();
	}

	@Test
	void findByLastName() {
		when(service.findByLastName(any())).thenReturn(Owner.builder().id(ownerId).lastName(lastName).build());

		Owner owner = service.findByLastName(lastName);
		assertNotNull(owner);
		assertEquals(ownerId, owner.getId());
		assertEquals(lastName, owner.getLastName());
	}

	@Test
	void findAll() {
		Set<Owner> mockOwners = new HashSet<>();
		mockOwners.add(Owner.builder().id(1L).build());
		mockOwners.add(Owner.builder().id(2L).build());

		when(ownerRepository.findAll()).thenReturn(mockOwners);

		Set<Owner> owners = service.findAll();
		assertNotNull(owners);
		assertEquals(mockOwners.size(), owners.size());
	}

	@Test
	void findById() {
		when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(mockOwner));

		Owner owner = service.findById(ownerId);
		assertNotNull(owner);
	}

	@Test
	void save() {
		Owner ownerToSave = Owner.builder().id(ownerId).build();
		when(ownerRepository.save(any())).thenReturn(mockOwner);

		Owner savedOwner = service.save(ownerToSave);
		assertNotNull(savedOwner);
	}

	@Test
	void delete() {
		service.delete(mockOwner);

		verify(ownerRepository, times(1)).delete(any());
		Owner owner = service.findById(ownerId);
		assertNull(owner);
	}

	@Test
	void deleteById() {
		service.deleteById(ownerId);

		verify(ownerRepository).deleteById(anyLong());
		Owner owner = service.findById(ownerId);
		assertNull(owner);
	}
}