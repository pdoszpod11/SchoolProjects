package brigad;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * személyek osztálya
 */

public abstract class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name; //személy neve
	private GregorianCalendar bday;    //születési év
	private String nation; //személy nemzetiség
	private GregorianCalendar atClub; //klubhoz csatlakozás dátuma
	private GregorianCalendar leftClub; //klubtól távozás dátuma
	
	/**
	 * 
	 * @param na, név
	 * @param nat, nemzetiség
	 * @param bd, születésnap
	 */
	public Person(String na, String nat, GregorianCalendar bd) {
		name = na;
		nation = nat;
		bday = bd;
		atClub = new GregorianCalendar();
	}
	
	protected static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");    //születési dátumokhoz használt általános formátum
	
	public GregorianCalendar getAtClub() { return atClub; }
	public GregorianCalendar getLeftClub() { return leftClub; }
	public String getName() { return name; }
	public GregorianCalendar getBday() { return bday; }
	public String getNation() { return nation; }
	
	/**
	 * 
	 * @return a személy családneve
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
	 * személyek kiíratására alkalmas alapvetõ fejléc
	 * @return fejléc egy String-ben
	 */
	protected static String header() {
		return String.format("Név: %-20sNemzetiség: %-10sSzületési dátum: %-11sKlubnál:%-7s", " ", " ", " ", " ");
	}
	
	/**
	 * 
	 * személyek listázása
	 * @param persons, egy személyeket tároló lista
	 */
	public static void listPersons(ArrayList<? extends Person> persons) {
		System.out.println(header());
		for(Person p : persons) {
			System.out.println(p.toString());
		}
	}
	
	/**
	 * név alapján törli a személyt, ha benne van a klubban
	 * @param perlist, egy személyeket tároló lista
	 * @param delnam, egy felhasználó által megadott név
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
	 * név alapján keresi a személyt a klubban
	 * @param perlist, egy személyeket tároló lista
	 * @param nam, egy felhasználó által megadott név
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
	 *személy hozzáadása klubhoz
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
