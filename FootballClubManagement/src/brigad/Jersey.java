package brigad;

import java.util.ArrayList;
import java.io.IOException;
import java.io.Serializable;

/**
 * mezek osztálya, méret és mezszám azonosítja 
 */
public class Jersey implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String size; //mez mérete
    private int jnumber; //mezszám
    private String label; //mezen felirat
    private String brand; //mez márkája
    private String color; //mez színe
    
    private static ArrayList<Jersey> jerseys = new ArrayList<>();    //mezeket összesítõ lista
    private static ArrayList<Integer> taken = new ArrayList<>();     //foglalt mezszámok listája ellenõrzésre
    private static IOCommand<Jersey> objload = new IOCommand<>(); //IO mûveleteket végzõ objektum
    
    /**
     * mezek esetén szükséges átírni az alapértelmezett márkára és színre
     * @param jnum, mezszám
     * @param sz, méret
     */
    
	public Jersey(int jnum, String sz) {
		brand = "Givova";
		color = "fekete";
		size = sz;
		jnumber = jnum;
	}
	
	/**
	 * megvizsgálja, hogy foglalt-e a kért mezszám
	 * @param numtry, szám alapján vizsgálat
	 * @return
	 */
	
	public static boolean availabilityCheck(int numtry) {

		if (numtry < 1 || numtry > 99) {
			return false;    //csak 1 és 99 közötti számok érvényesek
		}
		
		for (int i : taken) {
			if (numtry == i) {
				return false;
			}
		} return true;
		
		
	}

	public String getSize() {return size;}
	public int getJnumber() {return jnumber;}
	public String getLabel() {return label;}
	public String getBrand() { return this.brand; }
	public String getColor() { return this.color; }
	public static ArrayList<Jersey> getJerseys() { return jerseys; }
	
	public void setBrand(String b) { this.brand = b; }
	public void setColor(String c) { this.color = c; }
	public void setSize(String size) {this.size = size;}
	public void setJnumber(int jnumber) {this.jnumber = jnumber;}
	
	/**
	 * mez objektum feliratozása a játékos családnevével
	 * @param s
	 */	
	public void setLabel(String s) {
		this.label = s;
	}
	
	public static String jerseyHeader() {
		return String.format("Név: %20sMezszám: %3sMéret:", " ", " ");
		
	}
	
	public String toString() {
		return String.format("\n%-25s%-12s%s", label, jnumber, size);
	}

    /**
     * a paraméterként megadottra változtatja az összes mez márkáját
     * @param s, márkanév
     */
    public static void setAllBrand(String s) {

    	for (Jersey j : jerseys) {
    		j.brand = s;
    	}
    }
    
    /**
     * a paraméterként megadottra változtatja az összes mez színét
     * @param s, szín
     */
	
    public static void setAllColor(String s) {

    	for (Jersey j : jerseys) {
    		j.color = s;
    	}
    }
    
    /**
     * hozzáadjuk a létrehozott mez objektumot a megfelelõ listákhoz
     */
    
    public void addJersey() {

    	taken.add(jnumber);
		jerseys.add(this);
    }
    
    /**
     * összes mez kilistázása
     */
    public static void listJerseys() {

    	System.out.println(jerseyHeader());
    	for (Jersey j : jerseys) {
    		System.out.println(j.toString());
    	}
    }
    
	/**
	 * 
	 * 
	 * @throws IOException
	 *
	 *menti a mezek listáját
	 */
	public static void dataSave() throws IOException {
		objload.writeToFile(jerseys, "Jerseys.bin");
	}
	
	/**
	 * betölti a mentett játékosok listáját a statikus listába, a mezeket hozzáadja a mezeket tároló listához
	 * @throws IOException
	 * @throws ClassNotFoundException
	 *
	 *
	 */
	
	public static void dataLoad() throws IOException, ClassNotFoundException {
		jerseys = objload.readFile("Jerseys.bin");
		for (Jersey j : jerseys) {
			taken.add(j.jnumber);
	}
	
		
	} 
    
    

}
