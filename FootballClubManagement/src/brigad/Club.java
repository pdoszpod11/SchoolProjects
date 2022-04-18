package brigad;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Klub oszt�lya
 */
public final class Club implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String name = "Brig�d Testnevel�si K�r"; //klub neve
	private static final int foundation = 2020; //alap�t�si �v
	
	private int win; //gy�zelmek sz�ma alap�rtelmezetten
	private int draw; //d�ntetlenek sz�ma alap�rtelmezetten
	private int lose; //veres�gek sz�ma alap�rtelmezetten
	private Statistics allTimeStats; //az �sszes meccsb�l ered� statisztikai mutat�k
	
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
	
	/*Meccsek eredm�nye szerinti m�dos�t�sok */
	
	public void addWin() { win += 1; }
	public void addDraw() { draw += 1; }
	public void addLose() { lose += 1; }
	
	/**
	 * �r�km�rleg
	 * @return az �r�km�rleg egy String-ben
	 */
	private String getBalance() { 
		return String.format("GY-%s : D-%s : V-%s", win, draw, lose);
	}
	/**
	 * a klub objektum ment�se
	 * @param b, egy Club oszt�ly� objektum
	 * @throws IOException
	 */
	public static void dataSave(Club b) throws IOException {	
		try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream("BTKClub.bin"))) {
			objout.writeObject(b);
		}
	}
	
	/**
	 * Club objektum beolvas�sa
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
	 * Klub n�vjegy�nek visszaad�sa
	 * @return klub n�vjegye, String-ben
	 */
	public String contact() {
		return String.format("%s\nAlap�tva: %s\nM�rleg: %s\n ", name, foundation, this.getBalance());	
	}
	

	/**
	 * r�szletes n�vjegy visszaad�sa
	 * @return klub n�vjegye, String-ben
	 */
	public String detailedContact() {
		return String.format(contact() + Statistics.statsHeaderAll() + this.allTimeStats.statsAlltoString()); 
	}
	
	

}
