package brigad;


/**
 * Ha a fogyóeszköz nem található, visszadobja a hibaüzenetet
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
		return "Nem található a fogyóeszköz!";
	}
	
	

}
