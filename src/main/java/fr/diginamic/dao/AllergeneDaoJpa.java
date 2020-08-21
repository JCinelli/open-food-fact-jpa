package fr.diginamic.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.diginamic.entities.Allergene;
import fr.diginamic.entities.Article;
import fr.diginamic.traitementFichier.LectureFichier;
import fr.diginamic.traitementFichier.Parser;

public class AllergeneDaoJpa {
	
	/**
	 * Liste des acticles à partir du Csv
	 */
	private List<Article> listArtCsv = Parser.parseFichier(LectureFichier.lireFichier());

	/**
	 * Liste des allergenes qui ne contiendra aucun doublons
	 */
	private List<Allergene> allNoDoublons = new ArrayList<>();

	public AllergeneDaoJpa() {
		noDoublons();
	}

	/**
	 * 
	 * Retourne un allergene de la base grace à son nom
	 * 
	 * @param nomAll
	 * @return Allergene || null
	 */
	public Allergene findByAllName(String nomAll) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		Allergene allRetour = null;

		if (em != null) {

			String querySelect = "SELECT aller FROM Allergene aller WHERE aller.nom = '" + nomAll + "'";

			TypedQuery<Allergene> listResultat = em.createQuery(querySelect, Allergene.class);

			if (!listResultat.getResultList().isEmpty()) {

				allRetour = listResultat.getResultList().get(0);

			}

		}

		em.close();
		emf.close();

		return allRetour;

	}

	/**
	 * 
	 * Retourne tous les allergenes de la base
	 * 
	 * @return List<Allergene>
	 */
	public List<Allergene> findAll() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		List<Allergene> listAllDb = new ArrayList<>();

		if (em != null) {

			String querySelect = "SELECT aller FROM Allergene aller";

			TypedQuery<Allergene> listResultat = em.createQuery(querySelect, Allergene.class);

			if (!listResultat.getResultList().isEmpty()) {

				listAllDb = listResultat.getResultList();

			}

		}

		em.close();
		emf.close();

		return listAllDb;

	}

	/**
	 * 
	 * Insère tous les allergenes du CSV en base
	 * 
	 * Si l'Id de l'allergene est à zero c'est qu'il n'est pas en base donc on
	 * l'y insère
	 * 
	 */
	public void insertAllCsv() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		for (Allergene all : allNoDoublons) {

			if (all.getId() == 0) {

					em.getTransaction().begin();

					em.persist(all);

					em.getTransaction().commit();
					
			}

		}

		em.close();
		emf.close();

	}

	/**
	 * 
	 * Suprimme les doublons pour remplir la liste de tous les allergenes
	 * 
	 * Si pas de resultat au findAll on part de zéro
	 * 
	 * Sinon la liste se remplit avec les resultats du findAll
	 * 
	 */
	private void noDoublons() {

		List<Allergene> listAllDb = findAll();

		Set<String> nomAll = new HashSet<>();

		if (listAllDb.isEmpty()) {

			for (Article article : listArtCsv) {

				List<Allergene> allDeArticle = article.getListAllergenes();
				
				for (Allergene all : allDeArticle) {
				
						nomAll.add(all.getNom());
						
				}

			}

			for (String nom : nomAll) {

				allNoDoublons.add(new Allergene(nom));

			}

		} else {

			for (Allergene allDb : listAllDb) {

				allNoDoublons.add(allDb);

			}

		}

	}

	/**
	 * @return the allNoDoublons
	 */
	public List<Allergene> getAllNoDoublons() {
		return allNoDoublons;
	}

	/**
	 * @param allNoDoublons the allNoDoublons to set
	 */
	public void setAllNoDoublons(List<Allergene> allNoDoublons) {
		this.allNoDoublons = allNoDoublons;
	}
}
