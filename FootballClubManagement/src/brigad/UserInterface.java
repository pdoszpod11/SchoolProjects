package brigad;


/**
 * felhaszn�l�i fel�let oszt�lya
 * @author IOS9WL
 *
 */

public class UserInterface {
	
	/**
	 * �dv�zl� sz�veg
	 */
	public static void welcomeText() {
		System.out.println("�n a Brig�d TK nyilv�ntart�si rendszer�t haszn�lja! K�rem k�vesse az instrukci�kat a megfelel� eredm�ny �rdek�ben!\n"
				+ "A munkamenet manu�lis ment�s�hez �rjon 0-t, majd nyomjon ENTERT!(Az �llom�nyok automatikusan ment�sre ker�lnek szab�lyos kil�p�s eset�n is.)\n");
	}
	
	
	/**
	 * f�men�
	 */
	public static void mainMenu() {
		System.out.println(instruction(0));
		System.out.println(" 1. Csapat m�dos�t�sa\n 2. Eszk�zt�r m�dos�t�sa\n 3. List�z�si m�veletek\n 4. Kil�p�s\n");
		
	}
	
	private static String instruction(int i) {
		if (i == 0) {
		    return "K�rem a megfelel� sorsz�m lenyom�s�val, majd az ENTER megnyom�s�val v�lasszon megfelel� m�veletet!\n";
		} else {
			return "K�rem �rja be billenty�zeten a megfelel� karaktereket, majd nyomja meg az ENTER!\n";
		}
		
	}
	
	/**
	 * Csapat m�dos�t�sa
	 */
	
	public static void teamModify() {
		System.out.println(instruction(0));
		System.out.println(" 1.  J�t�kos hozz�ad�sa\n 2.  Edz� hozz�ad�sa\n 3.  Szurkol� hozz�ad�sa\n 4.  Szurkol� t�rl�se\n"
				+ " 5.  S�r�l�s adminisztr�l�sa\n 6.  S�r�l�sb�l visszat�r�s adminisztr�l�sa\n 7.  J�t�kos t�voz�s�nak regisztr�l�sa\n "
				+ "8.  Meccs adminisztr�l�sa\n 9.  Visszal�p�s\n 10. Kil�p�s\n");		
	}
	
	/**
	 * Fogy�eszk�z�k m�dos�t�sa
	 */
	
	public static void consumableModify() {
		System.out.println(instruction(0));
		System.out.println(" 1. Fogy�eszk�z hozz�ad�sa\n 2. Els�dleges mez v�ltoztat�sa(n�v, m�rka)\n 3. Fogy�eszk�z elt�vol�t�sa\n 4. Visszal�p�s\n 5. Kil�p�s\n");
	}
	
	/**
	 * list�z�si m�veletek
	 */
	
	public static void listingCommands() {
		System.out.println(instruction(0));
		System.out.println(" 1.  Csapat adatainak list�z�sa\n 2.  Foglalt mezsz�mok list�z�sa\n 3.  Szem�ly keres�se\n 4.  J�t�kosok list�z�sa\n "
				+ "5.  Edz�k list�z�sa\n 6.  Szurkol�k list�z�sa\n 7.  G�ll�v�lista \n 8.  G�lpasszlista\n 9.  Kanadai t�bl�zat(G+A) \n "
				+ "10. 'G�l n�lk�l lehozott' lista\n 11. Lapok szerinti lista\n 12. Meccsek list�z�sa\n 13. Visszal�p�s\n 14. Kil�p�s\n");
		
	}
	
	
	

}
