package brigad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * edzõk osztálya
 */
public class Coach extends Person {

	private static final long serialVersionUID = 1L;
	
	private Statistics stats; //összesített statisztika
	private static ArrayList<Coach> coaches = new ArrayList<>(); //csapat mindenkori edzõinek listája
	private static IOCommand<Coach> objload = new IOCommand<>(); //I/O mûveletekért felelõs objektum
	
	private boolean active; //éppen aktív edzõt jelzi
	
	/**
	 * 
	 * @param na, név
	 * @param nat, nemzetiség
	 * @param bday, születésnap
	 */

	public Coach(String na, String nat, GregorianCalendar bday) {
		super(na, nat, bday);
		stats = new Statistics();
	}

	public static ArrayList<Coach> getCoaches() { return coaches; }
	public static Coach getActiveCoach() {
		for (Coach c : coaches) {
			if (c.isActive()) {
				return c;
			}
		} return null; 
	}
	public Statistics getStats() { return stats; } 
	public boolean isActive() {return active;}
	
	public void setActive(boolean active) {this.active = active;}
	
	/**
	 * az éppen aktuális edzõ beállítása
	 */
	private void setHeadCoach() {
		this.active = true;
		for (Coach c : coaches) {
			if (!(this.equals(c))) {
				if (c.isActive()) {
					c.active = false;
					c.setLeftClub(new GregorianCalendar());			//csak egy aktív edzõ van, ha bekerül egy új, akkor a korábbi aktív edzõnek beállítja a távozását
				}
				
			}
		}
	}
	
	public static void dataSave() throws IOException {
		objload.writeToFile(coaches,"Coaches.bin");
		
	}
	
	public static void dataLoad() throws IOException, ClassNotFoundException {
		coaches = objload.readFile("Coaches.bin");
	}
	
	public String toString() {
		return super.header() + "\n" + super.toString();
	}
	
	/**
	 * 
	 *Minden személyes adat és statisztikai mutató kiírása játékosról
	 **/
	public String detailedBio() {
		return this.toString() + "\n" + Statistics.statsHeaderAll() + this.stats.statsAlltoString();
	}
	
	public static void listAllDetailedBio() {
		for (Coach c : coaches) {
			System.out.println(c.detailedBio());
		}
		
	}
	
	public void addPerson() throws AlreadyInClubException {
		for (Coach c : coaches) {
			if (this.equals(c)) {
				throw new AlreadyInClubException(coaches);
			}
		} coaches.add(this);
		this.setHeadCoach(); //egy új edzõ hozzáadása automatikusan azt jelenti, hogy õ lesz az aktív edzõ
	}

}
