package fr.diginamic.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.diginamic.entities.Additif;
import fr.diginamic.entities.Allergene;
import fr.diginamic.entities.Article;
import fr.diginamic.entities.Categorie;
import fr.diginamic.entities.Ingredient;
import fr.diginamic.entities.Marque;
import fr.diginamic.traitementFichier.LectureFichier;
import fr.diginamic.traitementFichier.Parser;

public class ArticleDaoJpa {
	
	/**
	 * Liste des acticles à partir du Csv
	 */
	private List<Article> listArtCsv = Parser.parseFichier(LectureFichier.lireFichier());
	
	private List<Article> listArtDb = findAll();
	
	private CategorieDaoJpa catDao;
	
	private MarqueDaoJpa marDao;
	
	private AdditifDaoJpa addDao;
	
	private AllergeneDaoJpa allDao;
	
	private IngredientDaoJpa ingDao;
	
	public ArticleDaoJpa() {
		
	}

	public ArticleDaoJpa(CategorieDaoJpa catDao, MarqueDaoJpa marDao, AdditifDaoJpa addDao, AllergeneDaoJpa allDao, IngredientDaoJpa ingDao) {

		this.catDao = catDao;
		this.marDao = marDao;
		this.addDao = addDao;
		this.allDao = allDao;
		this.ingDao = ingDao;
		
		hydratlistArtCsv();
		
	}
	
	/**
	 * 
	 * Retourne un produit de la base grace à son nom
	 * 
	 * @param nomPro
	 * @return Article || null
	 */
	public Article findByArtName(String nomArt) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		Article artRetour = null;

		if (em != null) {

			String querySelect = "SELECT art FROM Article art WHERE art.nom = '" + nomArt.toLowerCase() + "'";

			TypedQuery<Article> listResultat = em.createQuery(querySelect, Article.class);

			if (!listResultat.getResultList().isEmpty()) {

				artRetour = listResultat.getResultList().get(0);

			}

		}

		em.close();
		emf.close();

		return artRetour;

	}

	/**
	 * 
	 * Retourne tous les articles de la base
	 *
	 * @return List<Article>
	 */
	public List<Article> findAll() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		List<Article> listArtDb = new ArrayList<>();

		if (em != null) {

			String querySelect = "SELECT art FROM Article art";

			TypedQuery<Article> listResultat = em.createQuery(querySelect, Article.class);

			if (!listResultat.getResultList().isEmpty()) {

				listArtDb = listResultat.getResultList();

			}

		}

		em.close();
		emf.close();

		return listArtDb;

	}

	/**
	 * 
	 * Insère tous les articles du CSV en base
	 * 
	 * Si mon FindAll est vide, j'insere tout
	 * 
	 */
	public void insertAllCsv() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		for (Article art : listArtCsv) {

			if (listArtDb.isEmpty()) {

				em.getTransaction().begin();
				
				em.persist(art);
				
				em.getTransaction().commit();

			}
			
		}

		em.close();
		emf.close();

	}
	
	/**
	 * 
	 * Recherche si l'article a inséré est déja présent en base
	 * Si il n'y est pas, insert l'article
	 * 
	 */
	public void insert(Article newArt) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		Article verifNewArt = findByArtName(newArt.getNom());

			if (verifNewArt == null) {

				em.getTransaction().begin();
				
				em.persist(newArt);
				
				em.getTransaction().commit();
			
		}

		em.close();
		emf.close();

	}


	public void hydratlistArtCsv() {
		
		if (listArtDb.isEmpty()) {
			
			for (Article article : listArtCsv) {
				
				for (Categorie cat : catDao.getCatNoDoublons()) {
				
					if (article.getCategorie().getNom().equals(cat.getNom())) {
						
						article.setCategorie(cat);
						
					}
					
				}
				
				for (Marque mar : marDao.getMarNoDoublons()) {
					
					if (article.getMarque().getNom().equals(mar.getNom())) {
						
						article.setMarque(mar);
						
					}
					
				}
				
				for (Ingredient ing : ingDao.getIngNoDoublons()) {
					
					for (Ingredient ingArt : article.getListIngredients()) {
						
						if (ingArt.getNom().equals(ing.getNom())) {
							
							ingArt.setId(ing.getId());
							
						} 
						
					}
					
				}
				
				for (Allergene all : allDao.getAllNoDoublons()) {
					
					for (Allergene allArt : article.getListAllergenes()) {
						
						if (allArt.getNom().equals(all.getNom())) {
							
							allArt.setId(all.getId());
							
						} 
						
					}
					
				}
				
				for (Additif add : addDao.getAddNoDoublons()) {
					
					for (Additif addArt : article.getListAdditifs()) {
						
						if (addArt.getNom().equals(add.getNom())) {
							
							addArt.setId(add.getId());
							
						} 
						
					}
					
				}
				
			} 
			
		} else {
			
			listArtCsv = listArtDb;
			
		}
		
	}

}
