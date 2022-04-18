package brigad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * szurkol�k oszt�lya
 * @author IOS9WL
 *
 */
public class Fan extends Person {

	private static final long serialVersionUID = 1L;

	private static ArrayList<Fan> fans = new ArrayList<>();    //szurkol�kat tartalmaz� lista
	private static IOCommand<Fan> objload = new IOCommand<>(); //I/O m�veleteket v�gz� objektum
	
	/**
	 * 
	 * @param na, n�v
	 * @param nat, nemzetis�g
	 * @param bday, sz�let�snap
	 */

	public Fan(String na, String nat, GregorianCalendar bday) {
		super(na, nat, bday);
	}
	
	public static ArrayList<Fan> getFans() {return fans;}
	
	public String toString() {
		return super.toString();
	}
	
	public static void dataSave() throws IOException {
		objload.writeToFile(fans, "Fans.bin");
	}
	
	public static void dataLoad() throws IOException, ClassNotFoundException {
		fans = objload.readFile("Fans.bin");
	}
	
	public void addPerson() throws AlreadyInClubException {
		for (Fan f : fans) {
			if (this.equals(f)) {
				throw new AlreadyInClubException(fans);
			}
		} fans.add(this);
		
	}



}
