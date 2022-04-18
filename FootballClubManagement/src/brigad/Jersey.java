package brigad;

import java.util.ArrayList;
import java.io.IOException;
import java.io.Serializable;

/**
 * mezek oszt�lya, m�ret �s mezsz�m azonos�tja 
 */
public class Jersey implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String size; //mez m�rete
    private int jnumber; //mezsz�m
    private String label; //mezen felirat
    private String brand; //mez m�rk�ja
    private String color; //mez sz�ne
    
    private static ArrayList<Jersey> jerseys = new ArrayList<>();    //mezeket �sszes�t� lista
    private static ArrayList<Integer> taken = new ArrayList<>();     //foglalt mezsz�mok list�ja ellen�rz�sre
    private static IOCommand<Jersey> objload = new IOCommand<>(); //IO m�veleteket v�gz� objektum
    
    /**
     * mezek eset�n sz�ks�ges �t�rni az alap�rtelmezett m�rk�ra �s sz�nre
     * @param jnum, mezsz�m
     * @param sz, m�ret
     */
    
	public Jersey(int jnum, String sz) {
		brand = "Givova";
		color = "fekete";
		size = sz;
		jnumber = jnum;
	}
	
	/**
	 * megvizsg�lja, hogy foglalt-e a k�rt mezsz�m
	 * @param numtry, sz�m alapj�n vizsg�lat
	 * @return
	 */
	
	public static boolean availabilityCheck(int numtry) {

		if (numtry < 1 || numtry > 99) {
			return false;    //csak 1 �s 99 k�z�tti sz�mok �rv�nyesek
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
	 * mez objektum feliratoz�sa a j�t�kos csal�dnev�vel
	 * @param s
	 */	
	public void setLabel(String s) {
		this.label = s;
	}
	
	public static String jerseyHeader() {
		return String.format("N�v: %20sMezsz�m: %3sM�ret:", " ", " ");
		
	}
	
	public String toString() {
		return String.format("\n%-25s%-12s%s", label, jnumber, size);
	}

    /**
     * a param�terk�nt megadottra v�ltoztatja az �sszes mez m�rk�j�t
     * @param s, m�rkan�v
     */
    public static void setAllBrand(String s) {

    	for (Jersey j : jerseys) {
    		j.brand = s;
    	}
    }
    
    /**
     * a param�terk�nt megadottra v�ltoztatja az �sszes mez sz�n�t
     * @param s, sz�n
     */
	
    public static void setAllColor(String s) {

    	for (Jersey j : jerseys) {
    		j.color = s;
    	}
    }
    
    /**
     * hozz�adjuk a l�trehozott mez objektumot a megfelel� list�khoz
     */
    
    public void addJersey() {

    	taken.add(jnumber);
		jerseys.add(this);
    }
    
    /**
     * �sszes mez kilist�z�sa
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
	 *menti a mezek list�j�t
	 */
	public static void dataSave() throws IOException {
		objload.writeToFile(jerseys, "Jerseys.bin");
	}
	
	/**
	 * bet�lti a mentett j�t�kosok list�j�t a statikus list�ba, a mezeket hozz�adja a mezeket t�rol� list�hoz
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
