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
 *meccsek adatait tartalmazó osztály
 */
public class Match implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String opponent; //ellenfél csapatának nevével tárolja egy Stringben
	private int goalsFor; //lõtt gólok
	private int goalsAgainst; //kapott gólok
	private HashMap<Player, Integer> goalScorers; //góllövõk
	private HashMap<Player, Integer> assists; //gólpasszadók
	private ArrayList<Player> redCards; //piros lapot kapó játékosok
	private ArrayList<Player> yellowCards; //sárga lapot kapó játékosok
	private GregorianCalendar matchDay; //meccsnap dátuma
	private Coach headCoach; //aktív edzõ
	
	private static ArrayList<Match> matches = new ArrayList<Match>();    //lejátszott meccsek listája
	private static IOCommand<Match> objload = new IOCommand<Match>();    //IO mûveleteket végzõ objektum
	
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
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");    //dátumok formázásához

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
	 * osztályon belül használt fejléc
	 * @return
	 */
	
	private static String matchHeader() {
		return String.format("Dátum:%-12sEllenfél:%-20sVégeredmény:%7sGólszerzõk:\n", " ", " ", " ");
	}
	
	/**
	 * végeredmény
	 * @return végeredmény String-ben
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
	 * gólszerzõk visszaadása egy String-ben
	 * @return gólszerzõk 
	 */
	
	private String getScorers() {
		String scorers = ""; 
		if (!goalScorers.isEmpty()) {
			for (Map.Entry<Player, Integer> entry : goalScorers.entrySet()) {
				scorers += String.format("%s(%d) %s", entry.getKey().getSureName(), entry.getValue(), " ");	
				}
		} else {
			return "Nincs gólszerzõ";
		}
		return scorers;
		
	}

	
	public String toString() {
		String matchinfo = String.format("%-18s%-29s%-19s%s", formatter.format(this.matchDay.getTime()), this.opponent, this.getResult(), this.getScorers());
		return matchinfo;
		
	}
	
	/**
	 * összes lejátszott meccs listázása
	 */
	
	public static void listAllMatches() {
		System.out.println(matchHeader());
		for (Match m : matches) {
			System.out.println(m.toString());
		}
		
	}
	
	/**
	 * meccs adminisztrálása, minden fit játékos, eltiltott játékos, és aktív edzõ adatait frissíti 
	 * @param pl, fit játékosok
	 * @param ban, eltiltott játékosok listája
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
				if (p.getPosition().equals("védõ") || p.getPosition().equals("kapus")) {
					p.getStats().addCleanSheets(1);
				}
			}
		} else {
			this.headCoach.getStats().addConceded(goalsAgainst);
			b.getAllTimeStats().addConceded(goalsAgainst);
			for (Player p : pl) {
				if (p.getPosition().equals("védõ") || p.getPosition().equals("kapus")) {
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
					System.out.println("A meccs már korábban hozzá lett adva!\n");
				}
			} 
		} matches.add(this);
		
	}
	
	
	
	
	/**
	 * amennyiben a két meccs dátuma és az ellenfél megegyezik, akkor azonosként vannak kezelve
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
