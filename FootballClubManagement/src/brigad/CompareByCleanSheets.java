package brigad;

import java.util.Comparator;

/**
 * G�l n�lk�l lehozott meccsek szerinti rangsor
 * @author IOS9WL
 *
 */
public class CompareByCleanSheets implements Comparator<Player> {
	
	public int compare(Player p1, Player p2) {
		return Integer.compare(p1.getStats().getCleanSheets(), p2.getStats().getCleanSheets());
		
	}

	

}
