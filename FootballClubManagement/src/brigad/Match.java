package brigad;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *meccsek adatait tartalmaz� oszt�ly
 */
public class Match implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String opponent; //ellenf�l csapat�nak nev�vel t�rolja egy Stringben
	private int goalsFor; //l�tt g�lok
	private int goalsAgainst; //kapott g�lok
	private HashMap<Player, Integer> goalScorers; //g�ll�v�k
	private HashMap<Player, Integer> assists; //g�lpasszad�k
	private ArrayList<Player> redCards; //piros lapot kap� j�t�kosok
	private ArrayList<Player> yellowCards; //s�rga lapot kap� j�t�kosok
	private GregorianCalendar matchDay; //meccsnap d�tuma
	private Coach headCoach; //akt�v edz�
	
	private static ArrayList<Match> matches = new ArrayList<Match>();    //lej�tszott meccsek list�ja
	private static IOCommand<Match> objload = new IOCommand<Match>();    //IO m�veleteket v�gz� objektum
	
	public Match(String op, int scored, int rcvd, HashMap<Player, Integer> scorers, HashMap<Player, Integer> ast,
			ArrayList<Player> yel, ArrayList<Player> red, GregorianCalendar date) {
		opponent = op;
		goalsFor = scored;
		goalsAgainst = rcvd;
		goalScorers = scorers;
		matchDay = date;
		assists = ast;
		yellowCards = yel;
		redCards = red;
		headCoach = Coach.getActiveCoach();
	}
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");    //d�tumok form�z�s�hoz

	public String getOpponent() {return opponent;}
	public int getGoalsFor() {return goalsFor;}
	public int getGoalsAgainst() {return goalsAgainst;}
	public HashMap<Player, Integer> getGoalScorers() {return goalScorers;}
	public GregorianCalendar getMatchDay() {return matchDay;}
	public static ArrayList<Match> getMatches() {return matches;}

	public void setOpponent(String opponent) {this.opponent = opponent;}
	public void setGoalsFor(int goalsFor) {this.goalsFor = goalsFor;}
	public void setGoalsAgainst(int goalsAgainst) {this.goalsAgainst = goalsAgainst;}
	public void setGoalScorers(HashMap<Player, Integer> goalScorers) {this.goalScorers = goalScorers;}
	public void setMatchDay(GregorianCalendar matchDay) {this.matchDay = matchDay;}
	
	public static void dataSave() throws IOException {
		objload.writeToFile(matches, "Matches.bin");
	}
	
	public static void dataLoad() throws IOException, ClassNotFoundException {
		matches = objload.readFile("Matches.bin");
	}
	
	/**
	 * oszt�lyon bel�l haszn�lt fejl�c
	 * @return
	 */
	
	private static String matchHeader() {
		return String.format("D�tum:%-12sEllenf�l:%-20sV�geredm�ny:%7sG�lszerz�k:\n", " ", " ", " ");
	}
	
	/**
	 * v�geredm�ny
	 * @return v�geredm�ny String-ben
	 */
	private String getResult() {
		if (this.goalsFor > this.goalsAgainst) {
			return String.format("%d - %d (GY)", this.goalsFor, this.goalsAgainst);
			
		} else if (this.goalsFor == this.goalsAgainst) {
			return String.format("%d - %d (D)", this.goalsFor, this.goalsAgainst);
			
		} else {
			return String.format("%d - %d (V)", this.goalsFor, this.goalsAgainst);
		}
		
		
	}
	/**
	 * g�lszerz�k visszaad�sa egy String-ben
	 * @return g�lszerz�k 
	 */
	
	private String getScorers() {
		String scorers = ""; 
		if (!goalScorers.isEmpty()) {
			for (Map.Entry<Player, Integer> entry : goalScorers.entrySet()) {
				scorers += String.format("%s(%d) %s", entry.getKey().getSureName(), entry.getValue(), " ");	
				}
		} else {
			return "Nincs g�lszerz�";
		}
		return scorers;
		
	}

	
	public String toString() {
		String matchinfo = String.format("%-18s%-29s%-19s%s", formatter.format(this.matchDay.getTime()), this.opponent, this.getResult(), this.getScorers());
		return matchinfo;
		
	}
	
	/**
	 * �sszes lej�tszott meccs list�z�sa
	 */
	
	public static void listAllMatches() {
		System.out.println(matchHeader());
		for (Match m : matches) {
			System.out.println(m.toString());
		}
		
	}
	
	/**
	 * meccs adminisztr�l�sa, minden fit j�t�kos, eltiltott j�t�kos, �s akt�v edz� adatait friss�ti 
	 * @param pl, fit j�t�kosok
	 * @param ban, eltiltott j�t�kosok list�ja
	 */
	
	public void adminMatch(ArrayList<Player> pl, ArrayList<Player> ban, Club b) {
		this.addMatch();
		int sumAst = 0;
		if (this.goalScorers.size() > 0) {
			for (Map.Entry<Player, Integer> entry : goalScorers.entrySet()) {
				entry.getKey().getStats().addGoals(entry.getValue());
			}
		} if (assists.size() > 0) {	
			for (Map.Entry<Player, Integer> entry : assists.entrySet()) {
				entry.getKey().getStats().addAssists(entry.getValue());
				sumAst += entry.getValue();
			}
		} if (yellowCards.size() > 0) {
			for (Player p : yellowCards) {
				p.getStats().addYellow(1);
			}
		} if (redCards.size() > 0) {
			for (Player p : redCards) {
				p.getStats().addRed(1);
				p.setStatus("eltiltott");
			}
			
		}
		
		for (Player p : pl) {
			p.getStats().addPlayed(1);
		}
		
		this.headCoach.getStats().addGoals(goalsFor);
		this.headCoach.getStats().addAssists(sumAst);
		this.headCoach.getStats().addYellow(yellowCards.size());
		this.headCoach.getStats().addRed(redCards.size());
		this.headCoach.getStats().addPlayed(1);
		
		if (this.goalsAgainst == 0) {
			this.headCoach.getStats().addCleanSheets(1);
			b.getAllTimeStats().addCleanSheets(1);
			for (Player p : pl) {
				if (p.getPosition().equals("v�d�") || p.getPosition().equals("kapus")) {
					p.getStats().addCleanSheets(1);
				}
			}
		} else {
			this.headCoach.getStats().addConceded(goalsAgainst);
			b.getAllTimeStats().addConceded(goalsAgainst);
			for (Player p : pl) {
				if (p.getPosition().equals("v�d�") || p.getPosition().equals("kapus")) {
					p.getStats().addConceded(this.goalsAgainst);
				}
			}
		}
		for (Player p : ban) {
			p.setStatus("fit");
		}
		
		if (this.goalsFor > this.goalsAgainst) {
			b.addWin();
		} else if (this.goalsFor == this.goalsAgainst) {
			b.addDraw();
		} else {
			b.addLose();
		}
		b.getAllTimeStats().addPlayed(1);
		b.getAllTimeStats().addGoals(goalsFor);
		b.getAllTimeStats().addYellow(yellowCards.size());
		b.getAllTimeStats().addRed(redCards.size());
		b.getAllTimeStats().addAssists(sumAst);
	}
	
	public void addMatch() {
		if (matches.size() > 0) {
			for (Match m : matches) {
				if (this.equals(m)) {
					System.out.println("A meccs m�r kor�bban hozz� lett adva!\n");
				}
			} 
		} matches.add(this);
		
	}
	
	
	
	
	/**
	 * amennyiben a k�t meccs d�tuma �s az ellenf�l megegyezik, akkor azonosk�nt vannak kezelve
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Match) {
			if (this.matchDay.equals(((Match) o).matchDay) && this.opponent.equals(((Match) o).opponent)) {
				return true;
			}
		} return false;
	}
	
	
	
	
	
	
	
	
	
	

}
