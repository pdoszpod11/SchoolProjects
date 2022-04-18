package brigad;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConsumableTest {

	@Before
	public void setUp() throws Exception {
		Main.loadObjects();
	}

	@Test(expected=ItemAlreadyInClubException.class)
	public void testAddConsumable() throws ItemAlreadyInClubException {
		Consumable c = new Consumable(1111, "Teszt", "fehér", "labda", 12);
		c.addConsumable();
		Consumable c2 = new Consumable(1111, "Teszt", "fehér", "labda", 12);
		c2.addConsumable();
	}

	@Test
	public void testSearchByID() throws ItemNotFoundException, ItemAlreadyInClubException {
		Consumable c2 = new Consumable(11112, "Teszt", "fehér", "labda", 12);
		c2.addConsumable();
		Consumable c3 = Consumable.searchByID(11112);
		assertEquals("Nem ugyanaz a két fogyóeszköz.", c2, c3);

	}

	@Test(expected=ItemNotFoundException.class)
	public void testDelete() throws ItemAlreadyInClubException, ItemNotFoundException {
		Consumable c3 = new Consumable(11113, "Teszt", "fehér", "labda", 12);
		c3.addConsumable();
		Consumable.delete(11113);
		Consumable.searchByID(11113);
	}

}
