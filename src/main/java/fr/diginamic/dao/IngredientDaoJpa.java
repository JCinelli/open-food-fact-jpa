package fr.diginamic.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.diginamic.entities.Article;
import fr.diginamic.entities.Ingredient;
import fr.diginamic.traitementFichier.LectureFichier;
import fr.diginamic.traitementFichier.Parser;

public class IngredientDaoJpa {
	
	/**
	 * Liste des acticles à partir du Csv
	 */
	private List<Article> listArtCsv = Parser.parseFichier(LectureFichier.lireFichier());

	/**
	 * Liste des ingredients qui ne contiendra aucun doublons
	 */
	private List<Ingredient> ingNoDoublons = new ArrayList<>();

	public IngredientDaoJpa() {
		noDoublons();
	}

	/**
	 * 
	 * Retourne un ingredient de la base grace à son nom
	 * 
	 * @param nomIng
	 * @return Ingredient || null
	 */
	public Ingredient findByIngName(String nomIng) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		Ingredient ingRetour = null;

		if (em != null) {

			String querySelect = "SELECT ing FROM Ingredient ing WHERE ing.nom = '" + nomIng + "'";

			TypedQuery<Ingredient> listResultat = em.createQuery(querySelect, Ingredient.class);

			if (!listResultat.getResultList().isEmpty()) {

				ingRetour = listResultat.getResultList().get(0);

			}

		}

		em.close();
		emf.close();

		return ingRetour;

	}

	/**
	 * 
	 * Retourne tous les ingredients de la base
	 * 
	 * @return List<Ingredient>
	 */
	public List<Ingredient> findAll() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		List<Ingredient> listIngDb = new ArrayList<>();

		if (em != null) {

			String querySelect = "SELECT ing FROM Ingredient ing";

			TypedQuery<Ingredient> listResultat = em.createQuery(querySelect, Ingredient.class);

			if (!listResultat.getResultList().isEmpty()) {

				listIngDb = listResultat.getResultList();

			}

		}

		em.close();
		emf.close();

		return listIngDb;

	}

	/**
	 * 
	 * Insère tous les ingredients du CSV en base
	 * 
	 * Si l'Id de l'ingredient est à zero c'est qu'il n'est pas en base donc on
	 * l'y insère
	 * 
	 */
	public void insertAllCsv() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		for (Ingredient ing : ingNoDoublons) {

			if (ing.getId() == 0) {

					em.getTransaction().begin();

					em.persist(ing);

					em.getTransaction().commit();
					
			}

		}

		em.close();
		emf.close();

	}

	/**
	 * 
	 * Suprimme les doublons pour remplir la liste de tous les ingredients
	 * 
	 * Si pas de resultat au findAll on part de zéro
	 * 
	 * Sinon la liste se remplit avec les resultats du findAll
	 * 
	 */
	private void noDoublons() {

		List<Ingredient> listIngDb = findAll();

		Set<String> nomIng = new HashSet<>();

		if (listIngDb.isEmpty()) {

			for (Article article : listArtCsv) {

				List<Ingredient> ingDeArticle = article.getListIngredients();
				
				for (Ingredient ing : ingDeArticle) {
				
						nomIng.add(ing.getNom());
						
				}

			}

			for (String nom : nomIng) {

				ingNoDoublons.add(new Ingredient(nom));

			}

		} else {

			for (Ingredient ingDb : listIngDb) {

				ingNoDoublons.add(ingDb);

			}

		}

	}

	/**
	 * @return the ingNoDoublons
	 */
	public List<Ingredient> getIngNoDoublons() {
		return ingNoDoublons;
	}

	/**
	 * @param ingNoDoublons the ingNoDoublons to set
	 */
	public void setIngNoDoublons(List<Ingredient> ingNoDoublons) {
		this.ingNoDoublons = ingNoDoublons;
	}
}
