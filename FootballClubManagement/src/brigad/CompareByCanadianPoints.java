package brigad;

import java.util.Comparator;

/**
 * Kanadai pontok (G+A) szerinti rangsor, az �sszehasonl�t�s eredm�ny�t -1-es szorz�val el kell l�tni a rangsor jellege miatt
 * @author IOS9WL
 *
 */
public class CompareByCanadianPoints implements Comparator<Player> {

	public int compare(Player p1, Player p2) {
		int can1 = p1.getStats().getGoals() + p1.getStats().getAssists();
		int can2 = p2.getStats().getGoals() + p2.getStats().getAssists();
		return (-1)*Integer.compare(can1, can2);
		
	}

	

}
