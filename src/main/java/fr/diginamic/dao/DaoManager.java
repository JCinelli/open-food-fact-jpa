package fr.diginamic.dao;

public class DaoManager {
	
	private static CategorieDaoJpa catDao;
	private static MarqueDaoJpa marDao;
	private static AllergeneDaoJpa allDao;
	private static AdditifDaoJpa addDao;
	private static IngredientDaoJpa ingDao;
	private static ArticleDaoJpa artDao;

	public static void insertAllCsvInDb() {
		
		catDao = new CategorieDaoJpa();
		catDao.insertAllCsv();
		
		marDao = new MarqueDaoJpa();
		marDao.insertAllCsv();
		
		allDao = new AllergeneDaoJpa();
		allDao.insertAllCsv();
		
		addDao = new AdditifDaoJpa();
		addDao.insertAllCsv();
		
		ingDao = new IngredientDaoJpa();
		ingDao.insertAllCsv();
		
		artDao = new ArticleDaoJpa(catDao, marDao, addDao, allDao, ingDao);
		artDao.insertAllCsv();
	}

	/**
	 * @return the catDao
	 */
	public static CategorieDaoJpa getCatDao() {
		return catDao;
	}

	/**
	 * @param catDao the catDao to set
	 */
	public static void setCatDao(CategorieDaoJpa catDao) {
		DaoManager.catDao = catDao;
	}

	/**
	 * @return the marDao
	 */
	public static MarqueDaoJpa getMarDao() {
		return marDao;
	}

	/**
	 * @param marDao the marDao to set
	 */
	public static void setMarDao(MarqueDaoJpa marDao) {
		DaoManager.marDao = marDao;
	}

	/**
	 * @return the allDao
	 */
	public static AllergeneDaoJpa getAllDao() {
		return allDao;
	}

	/**
	 * @param allDao the allDao to set
	 */
	public static void setAllDao(AllergeneDaoJpa allDao) {
		DaoManager.allDao = allDao;
	}

	/**
	 * @return the addDao
	 */
	public static AdditifDaoJpa getAddDao() {
		return addDao;
	}

	/**
	 * @param addDao the addDao to set
	 */
	public static void setAddDao(AdditifDaoJpa addDao) {
		DaoManager.addDao = addDao;
	}

	/**
	 * @return the ingDao
	 */
	public static IngredientDaoJpa getIngDao() {
		return ingDao;
	}

	/**
	 * @param ingDao the ingDao to set
	 */
	public static void setIngDao(IngredientDaoJpa ingDao) {
		DaoManager.ingDao = ingDao;
	}

	/**
	 * @return the artDao
	 */
	public static ArticleDaoJpa getArtDao() {
		return artDao;
	}

	/**
	 * @param artDao the artDao to set
	 */
	public static void setArtDao(ArticleDaoJpa artDao) {
		DaoManager.artDao = artDao;
	}


}
