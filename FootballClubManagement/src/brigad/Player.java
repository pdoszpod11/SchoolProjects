package brigad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

/**
* 
*játékosok osztálya
*/
public class Player extends Person {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * az összes játékost tároló lista
	 */
	private static ArrayList<Player> playerList = new ArrayList<Player>();   //játékosokat tartalmazó lista
	public static String[] positions = new String[] { "kapus", "védõ", "középpályás", "támadó" }; //posztok
	private static IOCommand<Player> objload = new IOCommand<>(); //IO mûveleteket végzõ objektum
	private Statistics stats;  //statisztikai mutatók objektuma
	private String status;     //elérhetõség mutatója
	private String position;   //poszt
	private Jersey jers;       //mez objektum 
	
	/**
	 * 
	 * @param na, név
	 * @param nat, nemzetiség
	 * @param bday, születésnap
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
	 *a személyek fejlécét egyéb információkkal kiegészítõ fejléc
	 */
	private static String playerHeader() {
		return Person.header()+ String.format("Poszt:%-11sÁllapot:"," ");
	}
	
	public String toString() {
		return playerHeader() + "\n" + String.format("%d ",this.jers.getJnumber()) + super.toString() + String.format("%-17s%s\n", this.position, this.status);
	}
	
	/**
	 * 
	 *Minden személyes adat és statisztikai mutató kiírása játékosról
	 **/
	
	public String detailedBio() {
		if (this.position.equals("támadó") || this.position.equals("középpályás")) {
			return this.toString() + Statistics.statsHeaderField() + this.getStats().statsFieldtoString();
		} else {
			return this.toString() + Statistics.statsHeaderAll() + this.getStats().statsAlltoString();
		}
		
	}
	
	/**
	 * 
	 *játékosok listázása részletes statisztikai mutatókkal 
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
	 *menti a játékosok listáját
	 */
	public static void dataSave() throws IOException {
		objload.writeToFile(playerList, "Players.bin");
	}
	
	/**
	 * betölti a mentett játékosok listáját a statikus listába, a mezeket hozzáadja a mezeket tároló listához
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
	 *góllövõlista kiírása
	 */
	public static void goldenBoot() {

		Collections.sort(playerList, new CompareByGoals());
		System.out.println(String.format("Név:%-20sGólok:\n"," ")); 
		for (Player p : playerList) {
			if (p.getStats().getGoals() > 0) {
			    System.out.println(String.format("%-24s%d", p.getName(), p.getStats().getGoals()));
			}
		}
	}
	
	/**
	 * 
	 *szerzett lapok rangsorának kiírása
	 */
	public static void badBoyList() {
		Collections.sort(playerList, new CompareByCards());
		System.out.println(String.format("Név:%-20sPiros lapok:%-5sSárga lapok:\n"," ", " ")); 
		for (Player p : playerList) {
			if (p.getStats().getYellow() > 0 || p.getStats().getRed() > 0) {
			System.out.println(String.format("%-24s%-17d%d", p.getName(), p.getStats().getRed(), p.getStats().getYellow()));
			}
		}
			
 	}
	
	/**
	 * 
	 *gólpassz-lista kiírása
	 */
	public static void assistKing() {
		Collections.sort(playerList, new CompareByAssists());
		System.out.println(String.format("Név:%-20sGólpasszok:\n"," ")); 
		for (Player p : playerList) {
			if (p.getStats().getAssists() > 0) {
			    System.out.println(String.format("%-24s%d", p.getName(), p.getStats().getAssists()));
			}
		}
	}
	
	/**
	 * 
	 *kanadai pontok szerinti lisa (gól+assziszt)
	 */
	public static void canadianTable() {
		Collections.sort(playerList, new CompareByCanadianPoints());
		System.out.println(String.format("Név:%-20sG+A:\n"," ")); 
		for (Player p : playerList) {
			if (p.getStats().getGoals() + p.getStats().getAssists() > 0) {
			    System.out.println(String.format("%-24s%d", p.getName(), p.getStats().getGoals() + p.getStats().getAssists()));
			}
		}
	}
	
	/**
	 * 
	 *a gól nélkül lehozott meccsek alapján lista, csak kapusok és védõk szerepelhetnek benne
	 */
	public static void goalToZeroList() {
		Collections.sort(playerList, new CompareByCleanSheets());
		System.out.println(String.format("Név:%-20sGól nélkül lehozott meccsek:\n", " "));
		for (Player p : playerList) {
			if (p.getPosition().contains("védõ") || p.getPosition().contains("kapus")) {
				if (p.getStats().getCleanSheets() > 0)
			        System.out.println(String.format("%-24s%d", p.getName(), p.getStats().getCleanSheets()));
			}
		}
	}
	
	/**
	 * mezszám alapján játékos keresése
	 * @param jernum, mezszám
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
	 * a meccsre kész állapotban lévõk listája
	 * @return fit játékosok, egy listában
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
	 * eltiltott játékosok listája
	 * @return eltiltott játékosok, egy listában
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
