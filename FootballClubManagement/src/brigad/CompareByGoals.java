package brigad;

import java.util.Comparator;

/**
 * 
 * l�tt g�lok szerinti rangsor, az �sszehasonl�t�s eredm�ny�t -1-es szorz�val el kell l�tni a rangsor jellege miatt
 * @author IOS9WL
 *
 */

public class CompareByGoals implements Comparator<Player> {

	public int compare(Player p1, Player p2) {
		return (-1)*Integer.compare(p1.getStats().getGoals(), p2.getStats().getGoals());
		
	}

	
	

}
