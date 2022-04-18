package brigad;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * szem�lyek oszt�lya
 */

public abstract class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name; //szem�ly neve
	private GregorianCalendar bday;    //sz�let�si �v
	private String nation; //szem�ly nemzetis�g
	private GregorianCalendar atClub; //klubhoz csatlakoz�s d�tuma
	private GregorianCalendar leftClub; //klubt�l t�voz�s d�tuma
	
	/**
	 * 
	 * @param na, n�v
	 * @param nat, nemzetis�g
	 * @param bd, sz�let�snap
	 */
	public Person(String na, String nat, GregorianCalendar bd) {
		name = na;
		nation = nat;
		bday = bd;
		atClub = new GregorianCalendar();
	}
	
	protected static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");    //sz�let�si d�tumokhoz haszn�lt �ltal�nos form�tum
	
	public GregorianCalendar getAtClub() { return atClub; }
	public GregorianCalendar getLeftClub() { return leftClub; }
	public String getName() { return name; }
	public GregorianCalendar getBday() { return bday; }
	public String getNation() { return nation; }
	
	/**
	 * 
	 * @return a szem�ly csal�dneve
	 */
	public String getSureName() {
		String[] fullname = this.name.split(" ");
		return fullname[0];
	}
	
	public void setLeftClub(GregorianCalendar leftClub) { this.leftClub = leftClub; }
	public void setAtClub(GregorianCalendar atClub) { this.atClub = atClub; }
	public void setName(String name) { this.name = name; }
	public void setBday(GregorianCalendar bday) { this.bday = bday; }
	public void setNation(String nation) { this.nation = nation; }
	
	public String toString() {
		return String.format("%-25s%-22s%-28s%-15s", this.name, this.nation, formatter.format(bday.getTime()), formatter.format(this.atClub.getTime()));
	}
	
	/**
	 * szem�lyek ki�rat�s�ra alkalmas alapvet� fejl�c
	 * @return fejl�c egy String-ben
	 */
	protected static String header() {
		return String.format("N�v: %-20sNemzetis�g: %-10sSz�let�si d�tum: %-11sKlubn�l:%-7s", " ", " ", " ", " ");
	}
	
	/**
	 * 
	 * szem�lyek list�z�sa
	 * @param persons, egy szem�lyeket t�rol� lista
	 */
	public static void listPersons(ArrayList<? extends Person> persons) {
		System.out.println(header());
		for(Person p : persons) {
			System.out.println(p.toString());
		}
	}
	
	/**
	 * n�v alapj�n t�rli a szem�lyt, ha benne van a klubban
	 * @param perlist, egy szem�lyeket t�rol� lista
	 * @param delnam, egy felhaszn�l� �ltal megadott n�v
	 */
	
	public static void delete(ArrayList<? extends Person> perlist, String delname) {
		try {
			Person p = searchByName(perlist, delname);
			perlist.remove(p);
		
		} catch (PersonNotFoundException e) {
			System.err.println(e.getMessage());
			return;
		}
		
	}
	
	/**
	 * n�v alapj�n keresi a szem�lyt a klubban
	 * @param perlist, egy szem�lyeket t�rol� lista
	 * @param nam, egy felhaszn�l� �ltal megadott n�v
	 * @return logikai igaz, ha sikeres
	 */
	
	public static Person searchByName(ArrayList<? extends Person> perlist, String nam) throws PersonNotFoundException {
		
		for (Person p : perlist) {
			if (p.getName().toLowerCase().contains(nam.toLowerCase())) {
				return p;
				} 
		} throw new PersonNotFoundException();
	}
	
	/**
	 * 
	 *szem�ly hozz�ad�sa klubhoz
	 */
	public abstract void addPerson() throws Exception;
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Person) {
			if (((Person) o).name.equals(this.name) && ((Person) o).bday.equals(this.bday)) {
				return true;
			}
		} return false;
	}

}
