package brigad;

import java.util.Comparator;

/**
 * G�lpasszok szerinti rangsor, az �sszehasonl�t�s eredm�ny�t -1-es szorz�val el kell l�tni a rangsor jellege miatt
 * @author IOS9WL
 *
 */
public class CompareByAssists implements Comparator<Player> {
	
	public int compare(Player p1, Player p2) {
		return (-1)*Integer.compare(p1.getStats().getAssists(), p2.getStats().getAssists());
		
	}

	

}
