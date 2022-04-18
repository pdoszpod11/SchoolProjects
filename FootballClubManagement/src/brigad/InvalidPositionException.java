package brigad;

/**
 * nem megfelel� posztra vonatkoz� hiba
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
		return "Nem megfelel� poszt! K�rem az al�bbiakb�l �rjon be egyet: kapus, v�d�, k�z�pp�ly�s, t�mad�!";
	}

}
