package brigad;


/**
 * Ha a szem�ly nem tal�lhat�, visszadobja a hiba�zenetet
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
		return "A szem�ly nem tal�lhat� a klubban";
	}

	
}
