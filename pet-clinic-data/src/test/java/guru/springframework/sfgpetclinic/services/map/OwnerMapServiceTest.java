package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

	private OwnerMapService ownerMapService;

	private final Long ownerId = 1L;
	private final String lastName = "Smith";

	@BeforeEach
	void setUp() {
		ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());

		ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
	}

	@Test
	void findAll() {
		Set<Owner> owners = ownerMapService.findAll();
		assertEquals(1, owners.size());
	}

	@Test
	void findById() {
		Owner owner = ownerMapService.findById(ownerId);
		assertEquals(ownerId, owner.getId());
	}

	@Test
	void saveExistingId() {
		Long id = 2L;
		Owner owner = ownerMapService.save(Owner.builder().id(id).build());
		assertEquals(id, owner.getId());
	}

	@Test
	void saveNoId() {
		Owner owner = ownerMapService.save(Owner.builder().build());
		assertNotNull(owner);
		assertNotNull(owner.getId());
	}

	@Test
	void delete() {
		ownerMapService.delete(ownerMapService.findById(ownerId));
		assertEquals(0, ownerMapService.findAll().size());
		assertNull(ownerMapService.findById(ownerId));
	}

	@Test
	void deleteById() {
		ownerMapService.deleteById(ownerId);
		assertEquals(0, ownerMapService.findAll().size());
		assertNull(ownerMapService.findById(ownerId));
	}

	@Test
	void findByLastName() {
		Owner owner = ownerMapService.findByLastName(lastName);
		assertNotNull(owner);
		assertEquals(ownerId, owner.getId());
		assertEquals(lastName, owner.getLastName());
	}

	@Test
	void findByLastNameNotFound() {
		Owner owner = ownerMapService.findByLastName("notExisted");
		assertNull(owner);
	}
}