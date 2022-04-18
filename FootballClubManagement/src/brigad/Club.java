package brigad;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Klub osztálya
 */
public final class Club implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String name = "Brigád Testnevelési Kör"; //klub neve
	private static final int foundation = 2020; //alapítási év
	
	private int win; //gyõzelmek száma alapértelmezetten
	private int draw; //döntetlenek száma alapértelmezetten
	private int lose; //vereségek száma alapértelmezetten
	private Statistics allTimeStats; //az összes meccsbõl eredõ statisztikai mutatók
	
	public Club() {
		win = 0; draw = 0; lose = 0; 
		allTimeStats = new Statistics();
		
	}
	
	public static String getClubName() { return name; }
	public static int getFoundation() { return foundation; }
	public int getWins() { return win; }
	public int getDraws() { return draw; }
	public int getLosses() { return lose; }
	public Statistics getAllTimeStats() { return allTimeStats; }
	public void setAllTimeStats(Statistics stats) { allTimeStats = stats; }
	
	/*Meccsek eredménye szerinti módosítások */
	
	public void addWin() { win += 1; }
	public void addDraw() { draw += 1; }
	public void addLose() { lose += 1; }
	
	/**
	 * Örökmérleg
	 * @return az örökmérleg egy String-ben
	 */
	private String getBalance() { 
		return String.format("GY-%s : D-%s : V-%s", win, draw, lose);
	}
	/**
	 * a klub objektum mentése
	 * @param b, egy Club osztályú objektum
	 * @throws IOException
	 */
	public static void dataSave(Club b) throws IOException {	
		try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream("BTKClub.bin"))) {
			objout.writeObject(b);
		}
	}
	
	/**
	 * Club objektum beolvasása
	 * @return Club objektum
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Club dataLoad() throws IOException, ClassNotFoundException {
		try (ObjectInputStream objin = new ObjectInputStream(new FileInputStream("BTKClub.bin"))) {
			Club btk = (Club) objin.readObject();
			return btk;
		}
	}
	
	/**
	 * Klub névjegyének visszaadása
	 * @return klub névjegye, String-ben
	 */
	public String contact() {
		return String.format("%s\nAlapítva: %s\nMérleg: %s\n ", name, foundation, this.getBalance());	
	}
	

	/**
	 * részletes névjegy visszaadása
	 * @return klub névjegye, String-ben
	 */
	public String detailedContact() {
		return String.format(contact() + Statistics.statsHeaderAll() + this.allTimeStats.statsAlltoString()); 
	}
	
	

}
