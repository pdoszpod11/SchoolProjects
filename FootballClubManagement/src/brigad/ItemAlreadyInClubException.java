package brigad;

/**
 * ha létezik már az eszköz, hibaüzenet
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
		return "A tárgy már benne van a klubban!";
	}
	

}
