import fr.diginamic.dao.DaoManager;

public class MainApp {

	public static void main(String[] args) {
		
		long debut = System.currentTimeMillis();
		
		DaoManager.insertAllCsvInDb();
		
		long fin = System.currentTimeMillis();
		
		System.out.println("Temps d'execution : " + (fin - debut) / 1000 + " sec");
		
//		17 min. 31 sec. pour 248 414 insert
		
	}

}
