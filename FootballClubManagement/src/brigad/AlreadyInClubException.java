package brigad;

import java.util.ArrayList;

public class AlreadyInClubException extends Exception {
	private ArrayList<? extends Person> persons;
	
	public AlreadyInClubException(ArrayList<? extends Person> per) {
		persons = per;
	}
	
	public String getMessage() {
		return "A szem�ly m�r szerepel a klub nyilv�ntart�s�ban!";
	}

}
