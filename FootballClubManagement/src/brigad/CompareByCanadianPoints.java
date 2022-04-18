package brigad;

import java.util.Comparator;

/**
 * Kanadai pontok (G+A) szerinti rangsor, az összehasonlítás eredményét -1-es szorzóval el kell látni a rangsor jellege miatt
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
