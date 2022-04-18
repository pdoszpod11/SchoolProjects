package brigad;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

	@Before
	public void setUp() throws Exception {
		Main.loadObjects();
		
	}
	

	@Test(expected=AlreadyInClubException.class)
	public void testAddPerson() throws AlreadyInClubException, JerseyAlreadyTakenException, InvalidPositionException {
		Player p3 = new Player("Teszt Elek", "magyar", new GregorianCalendar(1911, 1, 1), "támadó", new Jersey(78, "S"));
		p3.addPerson();
		p3.addPerson();

		
}

	@Test(expected=PersonNotFoundException.class)
	public void testSearchByJersey() throws PersonNotFoundException {
		Player p = Player.searchByJersey(70);
		Player p2 = Player.searchByJersey(670);

		
	}

	@Test
	public void testFitPlayers() {
		for (Player p : Player.fitPlayers()) {
			assertTrue("A játékos nem fit.", p.getStatus().equals("fit"));
		}
		
	}

	@Test
	public void testBannedPlayers() {
		for (Player p : Player.bannedPlayers()) {
			assertTrue("A játékos nincs eltiltva.", p.getStatus().equals("eltiltott"));
		}
	}

}
