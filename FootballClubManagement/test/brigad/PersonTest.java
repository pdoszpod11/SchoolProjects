package brigad;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class PersonTest {

	@Before
	public void setUp() throws Exception {
		Main.loadObjects();
	}

	@Test
	public void testGetSureName() {
		Person ftest = new Fan("Teszt Szurkoló", "angol", new GregorianCalendar(1967,4,5));
		assertEquals("Nem mûködik a vezetéknevet visszaadó függvény.", "Teszt", ftest.getSureName());
	}

	@Test
	public void testDelete() throws Exception {
		Person ftest2 = new Fan("Teszt Supporter", "német", new GregorianCalendar(1967,5,1));
		ftest2.addPerson();
		Person.delete(Fan.getFans(), "Teszt Supporter");
	}

	@Test
	public void testSearchByName() throws Exception {
		Person ftest3 = new Fan("Teszt Supporter", "német", new GregorianCalendar(1967,5,1));
		ftest3.addPerson();
		Person.searchByName(Fan.getFans(), "Teszt Supporter");
	}

}
