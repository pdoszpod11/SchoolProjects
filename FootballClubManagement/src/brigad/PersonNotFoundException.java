package brigad;


/**
 * Ha a személy nem található, visszadobja a hibaüzenetet
 * @author IOS9WL
 *
 */

public class PersonNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PersonNotFoundException() {
	}
	
	public String getMessage() {
		return "A személy nem található a klubban";
	}

	
}
