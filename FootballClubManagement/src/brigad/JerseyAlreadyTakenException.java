package brigad;

import java.util.ArrayList;

/**
 * foglalt mezszámokra vonatkozó hiba
 * @author IOS9WL
 *
 */

public class JerseyAlreadyTakenException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Player> players;

	public JerseyAlreadyTakenException(ArrayList<Player> pl) {
		players = pl;

	}
	
	public String getMessage() {
		return "A mezszám foglalt!";
	}

}
