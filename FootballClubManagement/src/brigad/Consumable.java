package brigad;

import java.io.IOException;
import java.util.ArrayList;
import java.io.Serializable;

/**
 *fogyóeszközök osztálya 
 */

public class Consumable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id; //egy azonosításra alkalmas szám
	private String type; //fogyóeszköz típusa
	private int quantity; //fogyóeszköz darabszáma
	private String brand; //fogyóeszköz márkája
	private String color; //fogyóeszköz színe

	
	private static ArrayList<Consumable> consumables = new ArrayList<Consumable>(); //fogyóeszközöket összesítõ lista
	public static IOCommand<Consumable> objload = new IOCommand<Consumable>();    //IO mûveleteket végzõ objektum
	
	/**
	 * 
	 * @param i, azonosítószám
	 * @param br, márkanév
	 * @param cl, szín
	 * @param ty, típus
	 * @param q, darabszám
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
		return String.format("Típus: %s\nSzín: %s\nMárka: %s\nDarabszám: %s\n", this.type, this.color, this.brand, this.quantity);
	}
	
	public static void dataSave() throws IOException {
	    objload.writeToFile(consumables, "Consumables.bin");
	}

    public static void dataLoad() throws IOException, ClassNotFoundException {
	    consumables = objload.readFile("Consumables.bin");
	    }
    
    /**
     * 
     * fogyóeszköz hozzáadása klubhoz
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
     * fogyóeszköz keresése egy megadott azonosítószám alapján
     * @param y, a keresett azonosító száma
     */
    
	public static Consumable searchByID(int y) throws ItemNotFoundException {
		if (!consumables.isEmpty()) {
			for (Consumable c : consumables) {
				if (c.id == y) {
					return c;
				}
			} throw new ItemNotFoundException();
			
		} else {
			System.out.println("A klubban nincs fogyóeszköz!");
			return null;
		}

	}
	
	/**
	 * fogyóeszköz törlése megadott azonosítószám alapján
	 * @param y, ha a keresett elem törlésre került
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
	 *összes fogyóeszköz listázása 
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
		/*ha megegyezik az egyedi azonosítójuk akkor ugyanarról az a két objektum*/
		if (o instanceof Consumable) {
			if (((Consumable) o).id == this.id) {
				return true;
			}
		} return false;
	}
    
    
	
	
	
	

	

}
