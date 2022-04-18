package brigad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * szurkolók osztálya
 * @author IOS9WL
 *
 */
public class Fan extends Person {

	private static final long serialVersionUID = 1L;

	private static ArrayList<Fan> fans = new ArrayList<>();    //szurkolókat tartalmazó lista
	private static IOCommand<Fan> objload = new IOCommand<>(); //I/O mûveleteket végzõ objektum
	
	/**
	 * 
	 * @param na, név
	 * @param nat, nemzetiség
	 * @param bday, születésnap
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
