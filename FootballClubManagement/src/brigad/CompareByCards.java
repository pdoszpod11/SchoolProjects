package brigad;

import java.util.Comparator;

/**
 * lapok szerinti rangsor, az összehasonlítás eredményét -1-es szorzóval el kell látni a rangsor jellege miatt
 * @author IOS9WL
 *
 */
public class CompareByCards implements Comparator<Player>{
	
	public int compare(Player p1, Player p2) {
		/*a piros lap nagyobb súllyal bír, így sorrend szerint a piros laposok lesznek elõrébb*/
		int redComp = Integer.compare(p1.getStats().getRed(), p2.getStats().getRed());
		
		if (redComp != 0) {
			return (-1)*redComp;
		}
		
		/*ha a piros lapok száma egyezik, a sárgalapok száma a döntõ*/
		return (-1)*Integer.compare(p1.getStats().getYellow(), p2.getStats().getYellow());
		
	}

	

}
