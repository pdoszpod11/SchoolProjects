package brigad;


/**
 * Ha a fogy�eszk�z nem tal�lhat�, visszadobja a hiba�zenetet
 * @author IOS9WL
 *
 */

public class ItemNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ItemNotFoundException() {

	}
	
	public String getMessage() {
		return "Nem tal�lhat� a fogy�eszk�z!";
	}
	
	

}
