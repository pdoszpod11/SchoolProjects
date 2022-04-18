package brigad;

/**
 * ha l�tezik m�r az eszk�z, hiba�zenet
 * @author IOS9WL
 *
 */

public class ItemAlreadyInClubException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Consumable con;
	
	public ItemAlreadyInClubException(Consumable c) {
		con = c;
	}
	
	public String getMessage() {
		return "A t�rgy m�r benne van a klubban!";
	}
	

}
