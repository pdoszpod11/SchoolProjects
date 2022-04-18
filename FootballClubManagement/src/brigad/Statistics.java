package brigad;

import java.io.Serializable;

/**
 *statisztikai mutat�kat �sszes�t� oszt�ly 
 */
public class Statistics implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int goals;  //g�lok
	private int assists; //g�lpasszok
	private int cleanSheets; //g�l n�lk�l lehozott meccsek
	private int yellow;  //s�rgalapok
	private int red;  //piros lapok
	private int played;  //lej�tszott meccsek
	private int conceded; //kapott g�lok sz�ma
	
	public Statistics() {
		goals = 0; assists = 0; cleanSheets = 0; yellow = 0; red = 0; played = 0; conceded = 0;
		
	}

	public int getGoals() {return goals;}
	public int getAssists() {return assists;}
	public int getCleanSheets() {return cleanSheets;}
	public int getYellow() {return yellow;}
	public int getRed() {return red;}
	public int getPlayed() {return played;}
	public int getConceded() {return conceded;}

	public void setGoals(int goals) {this.goals = goals;}
	public void setAssists(int assists) {this.assists = assists;}
	public void setCleanSheets(int cleanSheets) {this.cleanSheets = cleanSheets;}
	public void setYellow(int yellow) {this.yellow = yellow;}
	public void setRed(int red) {this.red = red;}
	public void setPlayed(int played) {this.played = played;}
	public void setConceded(int cnd) {this.conceded = cnd;}
	
	public void addGoals(int g) { this.goals += g; }
	public void addAssists(int a) { this.assists += a; }
	public void addCleanSheets(int cs) { this.cleanSheets += cs; }
	public void addYellow(int y) { this.yellow += y; }
	public void addRed(int r) { this.red += r; }
	public void addPlayed(int p) {this.played += p;}
	public void addConceded(int c) {this.conceded += c;}
	
	/**
	 * k�z�pp�ly�sok �s t�mad�k sz�m�ra fejl�c a statisztika ki�r�shoz
	 * @return fejl�c String-ben
	 */
	
	public static String statsHeaderField() {
		return String.format("Lej�tszott meccsek: %-5sG�lok: %-5sG�lpasszok: %-5sS�rga lapok: %-5sPiros lapok:\n", " ", " ", " ", " ");
	}
	
	/**
	 * fejl�c minden statisztikai mutat� ki�r�s�hoz (v�d�k, kapusok, edz�k sz�m�ra)
	 * @return fejl�c String-ben
	 */
	
	public static String statsHeaderAll() {
		return String.format("Lej�tszott meccsek: %-5sG�lok: %-5sG�lpasszok: %-5sG�l n�lk�l lehozott meccsek: %-5sS�rga lapok: %-5sPiros lapok: %-5sKapott G�lok:\n", " ", " ", " ", " ", " ", " ");
	}
	
	public String statsAlltoString() {
		
		return String.format("%-25d%-12d%-17d%-34d%-18d%-18d%d\n",this.played, this.goals, this.assists, this.cleanSheets, this.yellow, this.red, this.conceded);
	}
	
	public String statsFieldtoString() {
		
		return String.format("%-25d%-12d%-17d%-18d%d\n",this.played, this.goals, this.assists, this.cleanSheets, this.yellow, this.red);
	}
	
	
	
	
	
	
	
	
	

}
