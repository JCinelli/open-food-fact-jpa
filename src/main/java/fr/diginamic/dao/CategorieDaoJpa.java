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
import fr.diginamic.entities.Categorie;
import fr.diginamic.traitementFichier.LectureFichier;
import fr.diginamic.traitementFichier.Parser;

public class CategorieDaoJpa {

	/**
	 * Liste des acticles à partir du Csv
	 */
	private List<Article> listArtCsv = Parser.parseFichier(LectureFichier.lireFichier());

	/**
	 * Liste des catégories qui ne contiendra aucun doublons
	 */
	private List<Categorie> catNoDoublons = new ArrayList<>();

	public CategorieDaoJpa() {
		noDoublons();
	}

	/**
	 * 
	 * Retourne une categorie de la base grace à son nom
	 * 
	 * @param nomCat
	 * @return Categorie || null
	 */
	public Categorie findByCatName(String nomCat) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		Categorie catRetour = null;

		if (em != null) {

			String querySelect = "SELECT cat FROM Categorie cat WHERE cat.nom = '" + nomCat + "'";

			TypedQuery<Categorie> listResultat = em.createQuery(querySelect, Categorie.class);

			if (!listResultat.getResultList().isEmpty()) {

				catRetour = listResultat.getResultList().get(0);

			}

		}

		em.close();
		emf.close();

		return catRetour;

	}

	/**
	 * 
	 * Retourne toutes les categories de la base
	 *
	 * @return List<Categorie>
	 */
	public List<Categorie> findAll() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		List<Categorie> listCatDb = new ArrayList<>();

		if (em != null) {

			String querySelect = "SELECT cat FROM Categorie cat";

			TypedQuery<Categorie> listResultat = em.createQuery(querySelect, Categorie.class);

			if (!listResultat.getResultList().isEmpty()) {

				listCatDb = listResultat.getResultList();

			}

		}

		em.close();
		emf.close();

		return listCatDb;

	}

	/**
	 * 
	 * Insère toutes les catégories du CSV en base
	 * 
	 * Si l'Id de la catégorie est à zero c'est qu'elle n'est pas en base donc on
	 * l'y insère
	 * 
	 */
	public void insertAllCsv() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		for (Categorie cat : catNoDoublons) {

			if (cat.getId() == 0) {

				em.getTransaction().begin();

				em.persist(cat);

				em.getTransaction().commit();

			}

		}

		em.close();
		emf.close();

	}

	/**
	 * 
	 * Suprimme les doublons pour remplir la liste de toutes les catégories
	 * 
	 * Si pas de resultats au findAll on part de zéro
	 * 
	 * Sinon la liste se remplit avec les resultats du findAll
	 * 
	 */
	private void noDoublons() {

		List<Categorie> listCatDb = findAll();

		Set<String> nomCat = new HashSet<>();

		if (listCatDb.isEmpty()) {

			for (Article article : listArtCsv) {

				nomCat.add(article.getCategorie().getNom());

			}

			for (String string : nomCat) {

				catNoDoublons.add(new Categorie(string));

			}

		} else {

			for (Categorie catDb : listCatDb) {

				catNoDoublons.add(catDb);

			}

		}

	}

	

	/**
	 * @return the catNoDoublons
	 */
	public List<Categorie> getCatNoDoublons() {
		return catNoDoublons;
	}

	/**
	 * @param catNoDoublons the catNoDoublons to set
	 */
	public void setCatNoDoublons(List<Categorie> catNoDoublons) {
		this.catNoDoublons = catNoDoublons;
	}

}
