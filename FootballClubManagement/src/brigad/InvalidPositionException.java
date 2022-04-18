package brigad;

/**
 * nem megfelelõ posztra vonatkozó hiba
 * @author IOS9WL
 *
 */

public class InvalidPositionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String post;
	
	public InvalidPositionException(String po) {
		post = po;
	}
	
	public String getMessage() {
		return "Nem megfelelõ poszt! Kérem az alábbiakból írjon be egyet: kapus, védõ, középpályás, támadó!";
	}

}
