package brigad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

/**
* 
*j�t�kosok oszt�lya
*/
public class Player extends Person {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * az �sszes j�t�kost t�rol� lista
	 */
	private static ArrayList<Player> playerList = new ArrayList<Player>();   //j�t�kosokat tartalmaz� lista
	public static String[] positions = new String[] { "kapus", "v�d�", "k�z�pp�ly�s", "t�mad�" }; //posztok
	private static IOCommand<Player> objload = new IOCommand<>(); //IO m�veleteket v�gz� objektum
	private Statistics stats;  //statisztikai mutat�k objektuma
	private String status;     //el�rhet�s�g mutat�ja
	private String position;   //poszt
	private Jersey jers;       //mez objektum 
	
	/**
	 * 
	 * @param na, n�v
	 * @param nat, nemzetis�g
	 * @param bday, sz�let�snap
	 * @param pos, poszt
	 * @param j, mez objektum
	 */

	public Player(String na, String nat, GregorianCalendar bday, String pos, Jersey j) {
		super(na, nat, bday);
		stats = new Statistics();
		status = "fit";
		position = pos;
		jers = j; 
	}

	
	public static ArrayList<Player> getPlayerList() {return playerList;}
	public Statistics getStats() {return stats;}
	public String getStatus() {return status;}
	public String getPosition() { return position; } 
	public Jersey getJers() { return jers; }
	public String[] getPositions() { return positions; }

	public void setStatus(String status) {this.status = status;}
	public void setPosition(String pos) {this.position = pos; }
	
	/**
	 * 
	 *a szem�lyek fejl�c�t egy�b inform�ci�kkal kieg�sz�t� fejl�c
	 */
	private static String playerHeader() {
		return Person.header()+ String.format("Poszt:%-11s�llapot:"," ");
	}
	
	public String toString() {
		return playerHeader() + "\n" + String.format("%d ",this.jers.getJnumber()) + super.toString() + String.format("%-17s%s\n", this.position, this.status);
	}
	
	/**
	 * 
	 *Minden szem�lyes adat �s statisztikai mutat� ki�r�sa j�t�kosr�l
	 **/
	
	public String detailedBio() {
		if (this.position.equals("t�mad�") || this.position.equals("k�z�pp�ly�s")) {
			return this.toString() + Statistics.statsHeaderField() + this.getStats().statsFieldtoString();
		} else {
			return this.toString() + Statistics.statsHeaderAll() + this.getStats().statsAlltoString();
		}
		
	}
	
	/**
	 * 
	 *j�t�kosok list�z�sa r�szletes statisztikai mutat�kkal 
	 */
	public static void listAllDetailedBio() {
		for (Player p: playerList) {
			System.out.println(p.detailedBio());
		}
	}
	
	/**
	 * 
	 * 
	 * @throws IOException
	 *
	 *menti a j�t�kosok list�j�t
	 */
	public static void dataSave() throws IOException {
		objload.writeToFile(playerList, "Players.bin");
	}
	
	/**
	 * bet�lti a mentett j�t�kosok list�j�t a statikus list�ba, a mezeket hozz�adja a mezeket t�rol� list�hoz
	 * @throws IOException
	 * @throws ClassNotFoundException
	 *
	 *
	 */
	
	public static void dataLoad() throws IOException, ClassNotFoundException {
		playerList = objload.readFile("Players.bin");
		for (Player p : playerList) {
			p.getJers().addJersey();
	}
	
		
	}

	public void addPerson() throws AlreadyInClubException, JerseyAlreadyTakenException, InvalidPositionException {
		int check;
		for (check = 0; check < positions.length; check++) {
			if (positions[check].equals(this.position)) {
				break;
			} 
		} if (check == positions.length) {
			throw new InvalidPositionException(this.position);
		}
		for (Player p : playerList) {
			if (this.equals(p)) {
				throw new AlreadyInClubException(playerList);
			}
		} if (Jersey.availabilityCheck(jers.getJnumber())) {
			playerList.add(this);
			jers.setLabel(super.getSureName());
			jers.addJersey();
		} else if (Jersey.availabilityCheck(jers.getJnumber()) == false) {
			throw new JerseyAlreadyTakenException(playerList);
		}
		
	}	
	
	/**
	 * 
	 *g�ll�v�lista ki�r�sa
	 */
	public static void goldenBoot() {

		Collections.sort(playerList, new CompareByGoals());
		System.out.println(String.format("N�v:%-20sG�lok:\n"," ")); 
		for (Player p : playerList) {
			if (p.getStats().getGoals() > 0) {
			    System.out.println(String.format("%-24s%d", p.getName(), p.getStats().getGoals()));
			}
		}
	}
	
	/**
	 * 
	 *szerzett lapok rangsor�nak ki�r�sa
	 */
	public static void badBoyList() {
		Collections.sort(playerList, new CompareByCards());
		System.out.println(String.format("N�v:%-20sPiros lapok:%-5sS�rga lapok:\n"," ", " ")); 
		for (Player p : playerList) {
			if (p.getStats().getYellow() > 0 || p.getStats().getRed() > 0) {
			System.out.println(String.format("%-24s%-17d%d", p.getName(), p.getStats().getRed(), p.getStats().getYellow()));
			}
		}
			
 	}
	
	/**
	 * 
	 *g�lpassz-lista ki�r�sa
	 */
	public static void assistKing() {
		Collections.sort(playerList, new CompareByAssists());
		System.out.println(String.format("N�v:%-20sG�lpasszok:\n"," ")); 
		for (Player p : playerList) {
			if (p.getStats().getAssists() > 0) {
			    System.out.println(String.format("%-24s%d", p.getName(), p.getStats().getAssists()));
			}
		}
	}
	
	/**
	 * 
	 *kanadai pontok szerinti lisa (g�l+assziszt)
	 */
	public static void canadianTable() {
		Collections.sort(playerList, new CompareByCanadianPoints());
		System.out.println(String.format("N�v:%-20sG+A:\n"," ")); 
		for (Player p : playerList) {
			if (p.getStats().getGoals() + p.getStats().getAssists() > 0) {
			    System.out.println(String.format("%-24s%d", p.getName(), p.getStats().getGoals() + p.getStats().getAssists()));
			}
		}
	}
	
	/**
	 * 
	 *a g�l n�lk�l lehozott meccsek alapj�n lista, csak kapusok �s v�d�k szerepelhetnek benne
	 */
	public static void goalToZeroList() {
		Collections.sort(playerList, new CompareByCleanSheets());
		System.out.println(String.format("N�v:%-20sG�l n�lk�l lehozott meccsek:\n", " "));
		for (Player p : playerList) {
			if (p.getPosition().contains("v�d�") || p.getPosition().contains("kapus")) {
				if (p.getStats().getCleanSheets() > 0)
			        System.out.println(String.format("%-24s%d", p.getName(), p.getStats().getCleanSheets()));
			}
		}
	}
	
	/**
	 * mezsz�m alapj�n j�t�kos keres�se
	 * @param jernum, mezsz�m
	 * @return a Player objektum, ha sikeres
	 */
	
	public static Player searchByJersey(int jernum) throws PersonNotFoundException {
		for (Player p : playerList) {
			if (p.getJers().getJnumber() == jernum) {
				return p;
			} 
		} throw new PersonNotFoundException(); 
		
	}
	
	/**
	 * a meccsre k�sz �llapotban l�v�k list�ja
	 * @return fit j�t�kosok, egy list�ban
	 */
	
	public static ArrayList<Player> fitPlayers() {
		ArrayList<Player> fpl = new ArrayList<>();
		for (Player p : playerList) {
			if (p.status.equals("fit")) {
				fpl.add(p);
			}
		} return fpl;
	}
	
	/**
	 * eltiltott j�t�kosok list�ja
	 * @return eltiltott j�t�kosok, egy list�ban
	 */
	public static ArrayList<Player> bannedPlayers() {
		ArrayList<Player> bpl = new ArrayList<>();
		for (Player p : playerList) {
			if (p.status.equals("eltiltott")) {
				bpl.add(p);
			}
		} return bpl;
	}	



}
