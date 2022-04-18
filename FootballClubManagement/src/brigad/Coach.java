package brigad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * edz�k oszt�lya
 */
public class Coach extends Person {

	private static final long serialVersionUID = 1L;
	
	private Statistics stats; //�sszes�tett statisztika
	private static ArrayList<Coach> coaches = new ArrayList<>(); //csapat mindenkori edz�inek list�ja
	private static IOCommand<Coach> objload = new IOCommand<>(); //I/O m�veletek�rt felel�s objektum
	
	private boolean active; //�ppen akt�v edz�t jelzi
	
	/**
	 * 
	 * @param na, n�v
	 * @param nat, nemzetis�g
	 * @param bday, sz�let�snap
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
	 * az �ppen aktu�lis edz� be�ll�t�sa
	 */
	private void setHeadCoach() {
		this.active = true;
		for (Coach c : coaches) {
			if (!(this.equals(c))) {
				if (c.isActive()) {
					c.active = false;
					c.setLeftClub(new GregorianCalendar());			//csak egy akt�v edz� van, ha beker�l egy �j, akkor a kor�bbi akt�v edz�nek be�ll�tja a t�voz�s�t
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
	 *Minden szem�lyes adat �s statisztikai mutat� ki�r�sa j�t�kosr�l
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
		this.setHeadCoach(); //egy �j edz� hozz�ad�sa automatikusan azt jelenti, hogy � lesz az akt�v edz�
	}

}
