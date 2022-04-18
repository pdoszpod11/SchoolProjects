package brigad;

import java.util.Comparator;

/**
 * Gólpasszok szerinti rangsor, az összehasonlítás eredményét -1-es szorzóval el kell látni a rangsor jellege miatt
 * @author IOS9WL
 *
 */
public class CompareByAssists implements Comparator<Player> {
	
	public int compare(Player p1, Player p2) {
		return (-1)*Integer.compare(p1.getStats().getAssists(), p2.getStats().getAssists());
		
	}

	

}
