package brigad;

import java.io.IOException;
import java.util.ArrayList;
import java.io.Serializable;

/**
 *fogy�eszk�z�k oszt�lya 
 */

public class Consumable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id; //egy azonos�t�sra alkalmas sz�m
	private String type; //fogy�eszk�z t�pusa
	private int quantity; //fogy�eszk�z darabsz�ma
	private String brand; //fogy�eszk�z m�rk�ja
	private String color; //fogy�eszk�z sz�ne

	
	private static ArrayList<Consumable> consumables = new ArrayList<Consumable>(); //fogy�eszk�z�ket �sszes�t� lista
	public static IOCommand<Consumable> objload = new IOCommand<Consumable>();    //IO m�veleteket v�gz� objektum
	
	/**
	 * 
	 * @param i, azonos�t�sz�m
	 * @param br, m�rkan�v
	 * @param cl, sz�n
	 * @param ty, t�pus
	 * @param q, darabsz�m
	 */
	public Consumable(int i, String br, String cl, String ty, int q) {
		id = i;
		brand = br;
		color = cl;
		type = ty;
		quantity = q;
		
	}

	public String getType() {return type;}
	public int getQuantity() {return quantity;}
	public String getBrand() { return brand; }
	public String getColor() { return color; }
	public int getID() { return id; }
	public static ArrayList<Consumable> getConsumables() { return consumables; }
	
	public void setBrand(String b) { brand = b; }
	public void setColor(String c) { color = c; }
	public void setType(String type) {this.type = type;}
	public void setQuantity(int quantity) {this.quantity = quantity;}
	
	public String toString() {
		return String.format("T�pus: %s\nSz�n: %s\nM�rka: %s\nDarabsz�m: %s\n", this.type, this.color, this.brand, this.quantity);
	}
	
	public static void dataSave() throws IOException {
	    objload.writeToFile(consumables, "Consumables.bin");
	}

    public static void dataLoad() throws IOException, ClassNotFoundException {
	    consumables = objload.readFile("Consumables.bin");
	    }
    
    /**
     * 
     * fogy�eszk�z hozz�ad�sa klubhoz
     * @return logikai igaz, ha sikeres
     */ 
    public void addConsumable() throws ItemAlreadyInClubException {
    	if (!consumables.isEmpty()) {
        	for (Consumable c : consumables) {
        		if (this.equals(c)) {
        			throw new ItemAlreadyInClubException(c);
        		}
        	} consumables.add(this);

    	} else {
    		consumables.add(this);
    	}
    }
    
    /**
     * 
     * fogy�eszk�z keres�se egy megadott azonos�t�sz�m alapj�n
     * @param y, a keresett azonos�t� sz�ma
     */
    
	public static Consumable searchByID(int y) throws ItemNotFoundException {
		if (!consumables.isEmpty()) {
			for (Consumable c : consumables) {
				if (c.id == y) {
					return c;
				}
			} throw new ItemNotFoundException();
			
		} else {
			System.out.println("A klubban nincs fogy�eszk�z!");
			return null;
		}

	}
	
	/**
	 * fogy�eszk�z t�rl�se megadott azonos�t�sz�m alapj�n
	 * @param y, ha a keresett elem t�rl�sre ker�lt
	 */
	public static void delete(int y) {
		try {
			Consumable delc = searchByID(y);
			consumables.remove(delc);
		} catch (ItemNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 *�sszes fogy�eszk�z list�z�sa 
	 */
	public static void listConsumables() {
		if (!consumables.isEmpty()) {
			for (Consumable c : consumables) {
				System.out.println(c.toString());
			}
			
		}

	}
	
	@Override
	public boolean equals(Object o) {
		/*ha megegyezik az egyedi azonos�t�juk akkor ugyanarr�l az a k�t objektum*/
		if (o instanceof Consumable) {
			if (((Consumable) o).id == this.id) {
				return true;
			}
		} return false;
	}
    
    
	
	
	
	

	

}
