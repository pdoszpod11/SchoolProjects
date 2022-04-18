package brigad;


/**
 * felhasználói felület osztálya
 * @author IOS9WL
 *
 */

public class UserInterface {
	
	/**
	 * üdvözlõ szöveg
	 */
	public static void welcomeText() {
		System.out.println("Ön a Brigád TK nyilvántartási rendszerét használja! Kérem kövesse az instrukciókat a megfelelõ eredmény érdekében!\n"
				+ "A munkamenet manuális mentéséhez írjon 0-t, majd nyomjon ENTERT!(Az állományok automatikusan mentésre kerülnek szabályos kilépés esetén is.)\n");
	}
	
	
	/**
	 * fõmenü
	 */
	public static void mainMenu() {
		System.out.println(instruction(0));
		System.out.println(" 1. Csapat módosítása\n 2. Eszköztár módosítása\n 3. Listázási mûveletek\n 4. Kilépés\n");
		
	}
	
	private static String instruction(int i) {
		if (i == 0) {
		    return "Kérem a megfelelõ sorszám lenyomásával, majd az ENTER megnyomásával válasszon megfelelõ mûveletet!\n";
		} else {
			return "Kérem írja be billentyûzeten a megfelelõ karaktereket, majd nyomja meg az ENTER!\n";
		}
		
	}
	
	/**
	 * Csapat módosítása
	 */
	
	public static void teamModify() {
		System.out.println(instruction(0));
		System.out.println(" 1.  Játékos hozzáadása\n 2.  Edzõ hozzáadása\n 3.  Szurkoló hozzáadása\n 4.  Szurkoló törlése\n"
				+ " 5.  Sérülés adminisztrálása\n 6.  Sérülésbõl visszatérés adminisztrálása\n 7.  Játékos távozásának regisztrálása\n "
				+ "8.  Meccs adminisztrálása\n 9.  Visszalépés\n 10. Kilépés\n");		
	}
	
	/**
	 * Fogyóeszközök módosítása
	 */
	
	public static void consumableModify() {
		System.out.println(instruction(0));
		System.out.println(" 1. Fogyóeszköz hozzáadása\n 2. Elsõdleges mez változtatása(név, márka)\n 3. Fogyóeszköz eltávolítása\n 4. Visszalépés\n 5. Kilépés\n");
	}
	
	/**
	 * listázási mûveletek
	 */
	
	public static void listingCommands() {
		System.out.println(instruction(0));
		System.out.println(" 1.  Csapat adatainak listázása\n 2.  Foglalt mezszámok listázása\n 3.  Személy keresése\n 4.  Játékosok listázása\n "
				+ "5.  Edzõk listázása\n 6.  Szurkolók listázása\n 7.  Góllövõlista \n 8.  Gólpasszlista\n 9.  Kanadai táblázat(G+A) \n "
				+ "10. 'Gól nélkül lehozott' lista\n 11. Lapok szerinti lista\n 12. Meccsek listázása\n 13. Visszalépés\n 14. Kilépés\n");
		
	}
	
	
	

}
