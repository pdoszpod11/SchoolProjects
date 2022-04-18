package brigad;

import java.util.Comparator;

/**
 * lapok szerinti rangsor, az �sszehasonl�t�s eredm�ny�t -1-es szorz�val el kell l�tni a rangsor jellege miatt
 * @author IOS9WL
 *
 */
public class CompareByCards implements Comparator<Player>{
	
	public int compare(Player p1, Player p2) {
		/*a piros lap nagyobb s�llyal b�r, �gy sorrend szerint a piros laposok lesznek el�r�bb*/
		int redComp = Integer.compare(p1.getStats().getRed(), p2.getStats().getRed());
		
		if (redComp != 0) {
			return (-1)*redComp;
		}
		
		/*ha a piros lapok sz�ma egyezik, a s�rgalapok sz�ma a d�nt�*/
		return (-1)*Integer.compare(p1.getStats().getYellow(), p2.getStats().getYellow());
		
	}

	

}
