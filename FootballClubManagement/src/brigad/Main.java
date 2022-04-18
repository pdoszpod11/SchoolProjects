package brigad;


import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;

/**
 * f�program
 * @author IOS9WL
 *
 */
public class Main {
	
	/**
	 * adatok manu�lis ment�se
	 * @param b, klub objektum
	 */
	public static Club brigad = new Club();
	public static Scanner sc = new Scanner(System.in);
	
	/**
	 * Adatok bet�lt�se
	 */
	public static void loadObjects() {
	    try {
		    brigad = Club.dataLoad();
		    Match.dataLoad();
		    Player.dataLoad();
		    Coach.dataLoad();
		    Fan.dataLoad();
		    Consumable.dataLoad();
		    Jersey.dataLoad();
	    } catch (IOException e) {
		    e.printStackTrace();
	    } catch (ClassNotFoundException e) {
		    e.printStackTrace();
	    }
	}

	
	
	
	/**
	 * objektumok ment�se
	 */
	public static void saveObjects() {
		try {
			Club.dataSave(brigad);
			Match.dataSave();
			Player.dataSave();
			Coach.dataSave();
			Fan.dataSave();
			Consumable.dataSave();
			Jersey.dataSave();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * egy�nileg pontokat el�r�k adatainak bevitele
	 * @param points, csapat �ltal l�tt g�lok, korl�tk�nt funkcion�l
	 * @return egy, az �sszes pontot el�r� j�t�kost tartalmaz� HashMap
	 */
	
	public static HashMap<Player, Integer> scorersAdmin(int points) {
		int goalcounter = 0;
		HashMap<Player,Integer> players = new HashMap<>();
		while (true) {
			try {
			    String scorersStr = sc.next();
			    
			    if (scorersStr.equals("-1")) {
			    	break;
			    } scorersStr.replaceAll("\\s", "");
			    String[] scorersLs = scorersStr.split(",");
			    Player scorer = Player.searchByJersey(Integer.parseInt(scorersLs[0]));
			    if (!scorer.getStatus().equals("fit")) {
			    	System.out.println("A j�t�kos nem vett r�szt a meccsen! A k�vetkez�k k�z�l v�lasszon:");
			    	for (Player p : Player.fitPlayers()) {
			    		System.out.println(p.toString());
			    	}
			    	continue;
			    }
			    goalcounter += Integer.parseInt(scorersLs[1]);
			    if (goalcounter > points) {
			    	 goalcounter -= Integer.parseInt(scorersLs[1]);
			    	 players.put(scorer, points - goalcounter);
			    	 return players;
			    } else if (goalcounter == points) {
			    	if (players.isEmpty()) {
			    		players.put(scorer, points);
			    	}
			    	else {
			    		goalcounter -= Integer.parseInt(scorersLs[1]);
			    		players.put(Player.searchByJersey(Integer.parseInt(scorersLs[0])), points - goalcounter);
			    	}
			    	return players;
			    }
			    players.put(Player.searchByJersey(Integer.parseInt(scorersLs[0])), Integer.parseInt(scorersLs[1]));
			    
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} return players;
		
		
	}
	
	/**
	 * a lapot szerzettek adminisztr�l�sa
	 * @return ArrayList a lapot szerzett j�t�kosokb�l
	 */
	
	public static ArrayList<Player> cardsAdmin() {
		ArrayList<Player> cards = new ArrayList<>();
		while (true) {
			try {
				int jnumber = Integer.parseInt(sc.next());
				if (jnumber == -1) {
					break;
				}
				cards.add(Player.searchByJersey(jnumber));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} return cards;
		
	}
	
	/**
	 * meccs adminisztr�ci�ja, ha sikeres menti a friss a f�jlokat
	 */
	
	public static void matchAdmin() {
		GregorianCalendar md = new GregorianCalendar();
		String opnt = "";
		int gf = 0;
		int ga = 0; 
		HashMap<Player, Integer> scrs = new HashMap<>();
		HashMap<Player, Integer> ast = new HashMap<>();
		ArrayList<Player> yc = new ArrayList<>();
		ArrayList<Player> rc = new ArrayList<>();
		
		System.out.println("K�rem k�vesse a megfelel� instrukci�kat!\n");
		System.out.println("K�rem �rja be a m�rk�z�s alapvet� adatait a k�vetkez� form�tumban: "
				+ "����-HH-NN-L�TTG�LOK-KAPOTTG�LOK-ELLENF�L-FC*\n "
				+ "(ellenf�l nev�t egyben, '-' jellel elv�lasztva, FC/TC/SE stb. a csapatnak megfelel�en)");
		try {
				
			String mday = sc.next();
			String[] mdaysplit = mday.split("-");
			md.set(Integer.parseInt(mdaysplit[0]), Integer.parseInt(mdaysplit[1]) - 1, Integer.parseInt(mdaysplit[2]));
			gf = Integer.parseInt(mdaysplit[3]);
			ga = Integer.parseInt(mdaysplit[4]);
			opnt += mdaysplit[5] + " " + mdaysplit[6];
		} catch(Exception e) {
			System.err.println("Nem megfelel� form�tum! K�vesse az instrukci�kat!");
			return;
			}

		if (gf > 0) {				//amennyiben van l�tt g�l, akkor hozz� kell adni a g�lszerz�ket, a g�ll�v�k nem haladhatj�k meg a g�lsz�mot
			System.out.println("�rja be a g�lszerz�ket! 'mezsz�m,g�lsz�m' form�tumban, soronk�nt egyet! Ha v�gzett, �rja be a -1 sz�mot!");
			scrs.putAll(scorersAdmin(gf));
			
		}
		if (gf > 0) {
			System.out.println("�rja be a g�lpasszt ad�kat 'mezsz�m,g�lpassz' form�tumban, soronk�nt egyet! Ha v�gzett, �rja be a -1 sz�mot!");
			ast.putAll(scorersAdmin(gf));
		}
		System.out.println("�rja be a s�rga lapot szerzett j�t�kosok mezsz�m�t, soronk�nt egyet! Ha v�gzett �rja be a -1 sz�mot!");
		yc.addAll(cardsAdmin());
		System.out.println("�rja be a piros lapot szerzett j�t�kosok mezsz�m�t, soronk�nt egyet! Ha v�gzett �rja be a -1 sz�mot!");
		rc.addAll(cardsAdmin());
		
		Match match = new Match(opnt, gf, ga, scrs, ast, yc, rc, md);
		match.adminMatch(Player.fitPlayers(), Player.bannedPlayers(), brigad);
		saveObjects();
		System.out.println("Sikeres adminisztr�ci�.");
	}
	
	/**
	 * szem�ly klubhoz ad�sa, a param�terk�nt megadott String alapj�n esetsz�tv�laszt�s
	 * @param cmd, eset k�dja, String-ben
	 */
	
	public static void addPersonToClub(String cmd) {
		String [] details;
		String person;
		System.out.println("�rja be a szem�ly adatait 'VEZET�KN�V-KERESZTN�V-NEMZETIS�G-SZ�LET�SI�V(����)-H�NAP(HH)-NAP(NN)' form�tumban!");
		try {
			person = sc.next();
			details = person.split("-");
			int year = Integer.parseInt(details[3]);
			int month = Integer.parseInt(details[4]);
			int day = Integer.parseInt(details[5]);
			GregorianCalendar bday = new GregorianCalendar(year, month, day);
			
			switch(cmd) {
				case "coach":
					Coach newc = new Coach(details[0]+" "+details[1], details[2].toLowerCase(), bday);
					newc.addPerson();
					System.out.println("Adminisztr�ci� sikeres.");
					break;
				case "fan":
					Fan newf = new Fan(details[0]+" "+details[1], details[2].toLowerCase(), bday);
					newf.addPerson();
					System.out.println("Adminisztr�ci� sikeres.");
					break;
				case "player":
					System.out.println("�rja be a j�t�kos tov�bbi adatait az el�z� form�tumnak megfelel�en: POSZT-MEZSZ�M-M�RET");
					String playerData = sc.next();
					String[] playerDetails = playerData.split("-");
					Player newp = new Player(details[0]+" "+details[1], details[2].toLowerCase(), bday, 
							playerDetails[0].toLowerCase(), new Jersey(Integer.parseInt(playerDetails[1]), playerDetails[2].toUpperCase()));
					newp.addPerson();
					System.out.println("Adminisztr�ci� sikeres.");
					break;
			}
			
			} catch (Exception e) {
				System.err.println(e.getMessage());
		}
		saveObjects();
	}

	/**
	 * n�v alapj�n t�rl�s
	 * @param c, eset sz�mk�dja
	 */

	
	public static void deleteByName() {
		System.out.println("K�rem �rja be a t�r�lni k�v�nt szem�ly nev�t!");
		String name = sc.next();
		Person.delete(Fan.getFans(), name);
		saveObjects();
	}
	
	/**
	 * j�t�kos t�voz�s�nak adminisztr�l�sa az inputon megadott n�v alapj�n
	 */
	
	public static void playerLeft() {
		System.out.println("�rja be a t�voz� j�t�kos mezsz�m�t, majd nyomjon ENTERT!");
		try {
			Player left = Player.searchByJersey(Integer.parseInt(sc.next()));
			left.setLeftClub(new GregorianCalendar());
			left.setStatus("t�vozott");
			Jersey.getJerseys().remove(left.getJers());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}  saveObjects();
	}
	/**
	 * J�t�kos s�r�l�s�nek adminisztr�l�sa az inputon megadott n�v alapj�n
	 */
	
	public static void playerInjuryAdmin(int cmd) {
		
		switch (cmd) {
			case 5:
				System.out.println("�rja be a s�r�lt j�t�kos mezsz�m�t, majd nyomjon ENTERT!");
				try {
					Player injured = Player.searchByJersey(Integer.parseInt(sc.next()));
					injured.setStatus("s�r�lt");
					System.out.println("Adminisztr�ci� sikeres.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			case 6:
				System.out.println("�rja be a s�r�l�sb�l visszat�r� j�t�kos mezsz�m�t, majd nyomjon ENTERT!");
				try {
					Player injured = Player.searchByJersey(Integer.parseInt(sc.next()));
					injured.setStatus("fit");
					System.out.println("Adminisztr�ci� sikeres.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

		} saveObjects();
		
	}
	
	/**
	 * csapat adminisztr�ci�j�val kapcsolatos m�veletek
	 * @param cmd, parancs
	 */
	public static void manageClub(int cmd) {
		
		switch(cmd) {
			case 1:				//J�t�kos hozz�ad�sa
				addPersonToClub("player");
				return;
			case 2:				//Edz� hozz�ad�sa
				addPersonToClub("coach");
				return;
			case 3:				//Szurkol� hozz�ad�sa
				addPersonToClub("fan");
				return;
			case 4:				//Szurkol� t�rl�se
				deleteByName();
				return;
			case 5:				//S�r�l�s adminisztr�l�sa
				playerInjuryAdmin(5);
				return;
			case 6:				//S�r�l�sb�l visszat�r�s adminisztr�l�sa
				playerInjuryAdmin(6);
				return;
			case 7:				//J�t�kos t�voz�s�nak regisztr�l�sa
				playerLeft();
				return;
			case 8:				//Meccs adminisztr�ci�
				matchAdmin();
				return;
			case 9:			//Visszal�p�s
				return;
			case 10:			//Kil�p�s
				saveObjects();
				sc.close();
				System.exit(0);
			}
			return;
		
	}
	
	public static void addItem() {
		System.out.println("�rja be a term�k adatait 'AZONOS�T�SZ�M-M�RKAN�V-SZ�N-T�PUS-DARABSZ�M' form�tumban");
		String item = sc.next();
		String[] itemsplit = item.split("-");
		try {
			Consumable newcons = new Consumable(Integer.parseInt(itemsplit[0]), itemsplit[1], itemsplit[2], itemsplit[3], Integer.parseInt(itemsplit[4]));
			newcons.addConsumable();
			System.out.println("Adminisztr�ci� sikeres.");
		} catch (ItemAlreadyInClubException e) {
			System.err.print(e.getMessage());
		}
		saveObjects();
		
	}
	/**
	 * fogy�eszk�z�k m�dos�t�sa
	 * @param cmd, parancs
	 */
	
	public static void manageItems(int cmd) {
		
		switch(cmd) {
			case 1:				//Foggy�eszk�z hozz�ad�sa
				addItem();
				return;
			case 2:				//Els�dleges mez �t�ll�t�sa
				System.out.println("�rja be a m�rka nev�t, majd nyomjon ENTERT!");
				String newbrand = sc.next();
				System.out.println("�rja be a mez sz�n�t, majd nyomjon ENTERT!");
				String newcolor = sc.next();
				Jersey.setAllBrand(newbrand);
				Jersey.setAllColor(newcolor);
				saveObjects();
				return;
			case 3:				//Fogy�eszk�z elt�vol�t�sa
				System.out.println("�rja be az eszk�z azonos�t�j�t!");
				int id = sc.nextInt();
				Consumable.delete(id);
				saveObjects();
				return;
			case 4:				//Visszal�p�s
				return;
			case 5:				//Kil�p�s
				saveObjects();
				sc.close();
				System.exit(0);
		}
		
	}
	
	/**
	 * ha sz�ks�ges �sszes�thetj�k a klub szem�lyeit egyetlen list�ban
	 * @return, szem�lyek list�ja
	 */
	
	public static ArrayList<Person> sumPersons() {
		ArrayList<Person> allPersons = new ArrayList<>();
		if (!Player.getPlayerList().isEmpty()) {
			allPersons.addAll(Player.getPlayerList());
		}
		
		if (!Fan.getFans().isEmpty()) {
			allPersons.addAll(Fan.getFans());
		}
		if (!Coach.getCoaches().isEmpty()) {
			allPersons.addAll(Coach.getCoaches());
		}
		
		return allPersons;
		
	}
	
	/**
	 * �sszes szem�ly k�z�tt keres n�v alapj�n
	 * @return a szem�ly objektum�t, ha l�tezik
	 */
	
	public static Person searchPerson() {
		
		System.out.println("�rja be a keresett nevet, majd nyomjon ENTERT!");
		String searchName = sc.next();
		try {
			Person searched = Person.searchByName(sumPersons(), searchName);
			return searched;
		} catch (PersonNotFoundException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	/**
	 * list�z�si m�veletek
	 * @param cmd, parancs
	 */
	
	public static void leaderboards(int cmd) {
		
			switch(cmd) {
			case 1:					//Contact
				System.out.println(brigad.detailedContact());
				return;
			case 2:					//Jerseys
				Jersey.listJerseys();
				return;
			case 3:					//Search
				Person searchedPerson = searchPerson();
				System.out.println(searchedPerson.toString());
				return;

			case 4:					//Players
				Player.listAllDetailedBio();
				return;
			case 5:					//Coaches
				if (Coach.getCoaches().isEmpty()) {
					System.out.println("Nincs edz� a klubban!");
					return;
				}
				Coach.listAllDetailedBio();
				return;
			case 6:					//Fans
				if (Fan.getFans().isEmpty()) {
					System.out.println("Nincs szurkol� a klubban!");
					return;
					}
				Fan.listPersons(Fan.getFans());
				return;
			case 7:					//Goal
				Player.goldenBoot();
				return;
			case 8:					//Assist
				Player.assistKing();
				return;
			case 9:					//G+A
				Player.canadianTable();
				return;
			case 10:				//Cleansheet
				Player.goalToZeroList();
				return;
			case 11:				//Lapok
				Player.badBoyList();
				return;
			case 12:				//Matches
				Match.listAllMatches();
				return;
			case 13:				//Visszal�p�s
				return;
			case 14:				//Kil�p�s
				sc.close();
				System.exit(0);
		}

		
	}
	public static void main(String[] args) {
		
		/**
		 * �dv�zl� �zenet ki�r�sa standard outputra
		 */
		UserInterface.welcomeText();
		
		/**
		 * program fut�sa egy v�gtelen ciklusban, felhaszn�l�i interakci�k �ltal vez�relve
		 */
		
		while (true) {
			loadObjects();
			String c = "99"; //alap�rtelmezett �rt�k bels� funkci� beolvas�shoz
			UserInterface.mainMenu();
			String cmd = sc.next();
			
			try {
				Integer.parseInt(cmd);
			
			    if (Integer.parseInt(cmd) == 1) {
				    while (true) {				//visszal�p�s hat�s�ra l�p�nk �jra a f�men�be
				    	UserInterface.teamModify();
				    	c = sc.next();
				    	if (Integer.parseInt(c) == 9) {
				    		break;
				    	} manageClub(Integer.parseInt(c));
				    }
				
			    } else if (Integer.parseInt(cmd) == 2) {
			    	while (true) {
			    		UserInterface.consumableModify();
			    		c = sc.next();
			    		if (Integer.parseInt(c) == 4) {
			    			break;
			    		} manageItems(Integer.parseInt(c));
			    	}
				
				
			    } else if (Integer.parseInt(cmd) == 3) {
			    	while (true) {
			    		UserInterface.listingCommands();
			    		c = sc.next();
			    		if (Integer.parseInt(c) == 13) {
			    			break;
			    		} leaderboards(Integer.parseInt(c));
			    	}

			    } else if (Integer.parseInt(cmd) == 4) {
				    /*adatok ment�se majd kil�p�s*/
			    	System.out.println("Viszontl�t�sra!");
				    saveObjects();
				    sc.close();
				    System.exit(0);
				
			    } else if (Integer.parseInt(cmd) == 0) {
			    	saveObjects();
			    	System.out.println("\nSikeres ment�s!\n");
			    } else {
			    	System.out.println("�rv�nytelen parancs! Haszn�lja a megadott sorsz�mokat!");
			    }
			
		    } catch (NumberFormatException e) {
			    System.err.println("Nem megfelel� parancs!");
			    }
			}
		
		}
	

}
