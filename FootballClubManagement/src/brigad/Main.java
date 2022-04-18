package brigad;


import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;

/**
 * fõprogram
 * @author IOS9WL
 *
 */
public class Main {
	
	/**
	 * adatok manuális mentése
	 * @param b, klub objektum
	 */
	public static Club brigad = new Club();
	public static Scanner sc = new Scanner(System.in);
	
	/**
	 * Adatok betöltése
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
	 * objektumok mentése
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
	 * egyénileg pontokat elérõk adatainak bevitele
	 * @param points, csapat által lõtt gólok, korlátként funkcionál
	 * @return egy, az összes pontot elérõ játékost tartalmazó HashMap
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
			    	System.out.println("A játékos nem vett részt a meccsen! A következõk közül válasszon:");
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
	 * a lapot szerzettek adminisztrálása
	 * @return ArrayList a lapot szerzett játákosokból
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
	 * meccs adminisztrációja, ha sikeres menti a friss a fájlokat
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
		
		System.out.println("Kérem kövesse a megfelelõ instrukciókat!\n");
		System.out.println("Kérem írja be a mérkõzés alapvetõ adatait a következõ formátumban: "
				+ "ÉÉÉÉ-HH-NN-LÕTTGÓLOK-KAPOTTGÓLOK-ELLENFÉL-FC*\n "
				+ "(ellenfél nevét egyben, '-' jellel elválasztva, FC/TC/SE stb. a csapatnak megfelelõen)");
		try {
				
			String mday = sc.next();
			String[] mdaysplit = mday.split("-");
			md.set(Integer.parseInt(mdaysplit[0]), Integer.parseInt(mdaysplit[1]) - 1, Integer.parseInt(mdaysplit[2]));
			gf = Integer.parseInt(mdaysplit[3]);
			ga = Integer.parseInt(mdaysplit[4]);
			opnt += mdaysplit[5] + " " + mdaysplit[6];
		} catch(Exception e) {
			System.err.println("Nem megfelelõ formátum! Kövesse az instrukciókat!");
			return;
			}

		if (gf > 0) {				//amennyiben van lõtt gól, akkor hozzá kell adni a gólszerzõket, a góllövõk nem haladhatják meg a gólszámot
			System.out.println("Írja be a gólszerzõket! 'mezszám,gólszám' formátumban, soronként egyet! Ha végzett, írja be a -1 számot!");
			scrs.putAll(scorersAdmin(gf));
			
		}
		if (gf > 0) {
			System.out.println("Írja be a gólpasszt adókat 'mezszám,gólpassz' formátumban, soronként egyet! Ha végzett, írja be a -1 számot!");
			ast.putAll(scorersAdmin(gf));
		}
		System.out.println("Írja be a sárga lapot szerzett játékosok mezszámát, soronként egyet! Ha végzett írja be a -1 számot!");
		yc.addAll(cardsAdmin());
		System.out.println("Írja be a piros lapot szerzett játékosok mezszámát, soronként egyet! Ha végzett írja be a -1 számot!");
		rc.addAll(cardsAdmin());
		
		Match match = new Match(opnt, gf, ga, scrs, ast, yc, rc, md);
		match.adminMatch(Player.fitPlayers(), Player.bannedPlayers(), brigad);
		saveObjects();
		System.out.println("Sikeres adminisztráció.");
	}
	
	/**
	 * személy klubhoz adása, a paraméterként megadott String alapján esetszétválasztás
	 * @param cmd, eset kódja, String-ben
	 */
	
	public static void addPersonToClub(String cmd) {
		String [] details;
		String person;
		System.out.println("Írja be a személy adatait 'VEZETÉKNÉV-KERESZTNÉV-NEMZETISÉG-SZÜLETÉSIÉV(ÉÉÉÉ)-HÓNAP(HH)-NAP(NN)' formátumban!");
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
					System.out.println("Adminisztráció sikeres.");
					break;
				case "fan":
					Fan newf = new Fan(details[0]+" "+details[1], details[2].toLowerCase(), bday);
					newf.addPerson();
					System.out.println("Adminisztráció sikeres.");
					break;
				case "player":
					System.out.println("Írja be a játékos további adatait az elõzõ formátumnak megfelelõen: POSZT-MEZSZÁM-MÉRET");
					String playerData = sc.next();
					String[] playerDetails = playerData.split("-");
					Player newp = new Player(details[0]+" "+details[1], details[2].toLowerCase(), bday, 
							playerDetails[0].toLowerCase(), new Jersey(Integer.parseInt(playerDetails[1]), playerDetails[2].toUpperCase()));
					newp.addPerson();
					System.out.println("Adminisztráció sikeres.");
					break;
			}
			
			} catch (Exception e) {
				System.err.println(e.getMessage());
		}
		saveObjects();
	}

	/**
	 * név alapján törlés
	 * @param c, eset számkódja
	 */

	
	public static void deleteByName() {
		System.out.println("Kérem írja be a törölni kívánt személy nevét!");
		String name = sc.next();
		Person.delete(Fan.getFans(), name);
		saveObjects();
	}
	
	/**
	 * játékos távozásának adminisztrálása az inputon megadott név alapján
	 */
	
	public static void playerLeft() {
		System.out.println("Írja be a távozó játékos mezszámát, majd nyomjon ENTERT!");
		try {
			Player left = Player.searchByJersey(Integer.parseInt(sc.next()));
			left.setLeftClub(new GregorianCalendar());
			left.setStatus("távozott");
			Jersey.getJerseys().remove(left.getJers());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}  saveObjects();
	}
	/**
	 * Játékos sérülésének adminisztrálása az inputon megadott név alapján
	 */
	
	public static void playerInjuryAdmin(int cmd) {
		
		switch (cmd) {
			case 5:
				System.out.println("Írja be a sérült játékos mezszámát, majd nyomjon ENTERT!");
				try {
					Player injured = Player.searchByJersey(Integer.parseInt(sc.next()));
					injured.setStatus("sérült");
					System.out.println("Adminisztráció sikeres.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			case 6:
				System.out.println("Írja be a sérülésbõl visszatérõ játékos mezszámát, majd nyomjon ENTERT!");
				try {
					Player injured = Player.searchByJersey(Integer.parseInt(sc.next()));
					injured.setStatus("fit");
					System.out.println("Adminisztráció sikeres.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

		} saveObjects();
		
	}
	
	/**
	 * csapat adminisztrációjával kapcsolatos mûveletek
	 * @param cmd, parancs
	 */
	public static void manageClub(int cmd) {
		
		switch(cmd) {
			case 1:				//Játékos hozzáadása
				addPersonToClub("player");
				return;
			case 2:				//Edzõ hozzáadása
				addPersonToClub("coach");
				return;
			case 3:				//Szurkoló hozzáadása
				addPersonToClub("fan");
				return;
			case 4:				//Szurkoló törlése
				deleteByName();
				return;
			case 5:				//Sérülés adminisztrálása
				playerInjuryAdmin(5);
				return;
			case 6:				//Sérülésbõl visszatérés adminisztrálása
				playerInjuryAdmin(6);
				return;
			case 7:				//Játékos távozásának regisztrálása
				playerLeft();
				return;
			case 8:				//Meccs adminisztráció
				matchAdmin();
				return;
			case 9:			//Visszalépés
				return;
			case 10:			//Kilépés
				saveObjects();
				sc.close();
				System.exit(0);
			}
			return;
		
	}
	
	public static void addItem() {
		System.out.println("Írja be a termék adatait 'AZONOSÍTÓSZÁM-MÁRKANÉV-SZÍN-TÍPUS-DARABSZÁM' formátumban");
		String item = sc.next();
		String[] itemsplit = item.split("-");
		try {
			Consumable newcons = new Consumable(Integer.parseInt(itemsplit[0]), itemsplit[1], itemsplit[2], itemsplit[3], Integer.parseInt(itemsplit[4]));
			newcons.addConsumable();
			System.out.println("Adminisztráció sikeres.");
		} catch (ItemAlreadyInClubException e) {
			System.err.print(e.getMessage());
		}
		saveObjects();
		
	}
	/**
	 * fogyóeszközök módosítása
	 * @param cmd, parancs
	 */
	
	public static void manageItems(int cmd) {
		
		switch(cmd) {
			case 1:				//Foggyóeszköz hozzáadása
				addItem();
				return;
			case 2:				//Elsõdleges mez átállítása
				System.out.println("Írja be a márka nevét, majd nyomjon ENTERT!");
				String newbrand = sc.next();
				System.out.println("Írja be a mez színét, majd nyomjon ENTERT!");
				String newcolor = sc.next();
				Jersey.setAllBrand(newbrand);
				Jersey.setAllColor(newcolor);
				saveObjects();
				return;
			case 3:				//Fogyóeszköz eltávolítása
				System.out.println("Írja be az eszköz azonosítóját!");
				int id = sc.nextInt();
				Consumable.delete(id);
				saveObjects();
				return;
			case 4:				//Visszalépés
				return;
			case 5:				//Kilépés
				saveObjects();
				sc.close();
				System.exit(0);
		}
		
	}
	
	/**
	 * ha szükséges összesíthetjük a klub személyeit egyetlen listában
	 * @return, személyek listája
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
	 * összes személy között keres név alapján
	 * @return a személy objektumát, ha létezik
	 */
	
	public static Person searchPerson() {
		
		System.out.println("Írja be a keresett nevet, majd nyomjon ENTERT!");
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
	 * listázási mûveletek
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
					System.out.println("Nincs edzõ a klubban!");
					return;
				}
				Coach.listAllDetailedBio();
				return;
			case 6:					//Fans
				if (Fan.getFans().isEmpty()) {
					System.out.println("Nincs szurkoló a klubban!");
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
			case 13:				//Visszalépés
				return;
			case 14:				//Kilépés
				sc.close();
				System.exit(0);
		}

		
	}
	public static void main(String[] args) {
		
		/**
		 * üdvözlõ üzenet kiírása standard outputra
		 */
		UserInterface.welcomeText();
		
		/**
		 * program futása egy végtelen ciklusban, felhasználói interakciók által vezérelve
		 */
		
		while (true) {
			loadObjects();
			String c = "99"; //alapértelmezett érték belsõ funkció beolvasáshoz
			UserInterface.mainMenu();
			String cmd = sc.next();
			
			try {
				Integer.parseInt(cmd);
			
			    if (Integer.parseInt(cmd) == 1) {
				    while (true) {				//visszalépés hatására lépünk újra a fõmenübe
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
				    /*adatok mentése majd kilépés*/
			    	System.out.println("Viszontlátásra!");
				    saveObjects();
				    sc.close();
				    System.exit(0);
				
			    } else if (Integer.parseInt(cmd) == 0) {
			    	saveObjects();
			    	System.out.println("\nSikeres mentés!\n");
			    } else {
			    	System.out.println("Érvénytelen parancs! Használja a megadott sorszámokat!");
			    }
			
		    } catch (NumberFormatException e) {
			    System.err.println("Nem megfelelõ parancs!");
			    }
			}
		
		}
	

}
