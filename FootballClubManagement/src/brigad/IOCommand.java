package brigad;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * I/O mûveletek generikus osztálya
 * 
 * @author IOS9WL
 *
 * @param <T>
 */

public class IOCommand<T> {
	
	/**
	 * egy objektumokat tároló listát kiír egy paraméterként megadott file-ba
	 * @param objlist
	 * @param filename
	 * @throws IOException
	 */

	public void writeToFile(ArrayList<T> objlist, String filename) throws IOException {
		try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(filename))) {
			objout.writeObject(objlist);
		}
		
	}
	
	/**
	 * egy file beolvasása aminek visszatérési értéke egy lista
	 * @param filename
	 * @return ArrayList, ami tartalmazza az objektumokat
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	@SuppressWarnings("unchecked")
		
	public ArrayList<T> readFile(String filename) throws IOException, ClassNotFoundException {
			try (ObjectInputStream objin = new ObjectInputStream(new FileInputStream(filename))) {
				ArrayList<T> objlist = (ArrayList<T>) objin.readObject();
				return objlist;
			}
		}

}

