package fr.diginamic.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.diginamic.entities.Additif;
import fr.diginamic.entities.Article;
import fr.diginamic.traitementFichier.LectureFichier;
import fr.diginamic.traitementFichier.Parser;

public class AdditifDaoJpa {
	
	/**
	 * Liste des acticles à partir du Csv
	 */
	private List<Article> listArtCsv = Parser.parseFichier(LectureFichier.lireFichier());

	/**
	 * Liste des additifs qui ne contiendra aucun doublons
	 */
	private List<Additif> addNoDoublons = new ArrayList<>();

	public AdditifDaoJpa() {
		noDoublons();
	}

	/**
	 * 
	 * Retourne un additif de la base grace à son nom
	 * 
	 * @param nomAdd
	 * @return Additif || null
	 */
	public Additif findByAddName(String nomAdd) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		Additif addRetour = null;

		if (em != null) {

			String querySelect = "SELECT add FROM Additif add WHERE add.nom = '" + nomAdd + "'";

			TypedQuery<Additif> listResultat = em.createQuery(querySelect, Additif.class);

			if (!listResultat.getResultList().isEmpty()) {

				addRetour = listResultat.getResultList().get(0);

			}

		}

		em.close();
		emf.close();

		return addRetour;

	}

	/**
	 * 
	 * Retourne tous les additifs de la base
	 * 
	 * @return List<Additif>
	 */
	public List<Additif> findAll() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		List<Additif> listAddDb = new ArrayList<>();

		if (em != null) {

			String querySelect = "SELECT add FROM Additif add";

			TypedQuery<Additif> listResultat = em.createQuery(querySelect, Additif.class);

			if (!listResultat.getResultList().isEmpty()) {

				listAddDb = listResultat.getResultList();

			}

		}

		em.close();
		emf.close();

		return listAddDb;

	}

	/**
	 * 
	 * Insère tous les additifs du CSV en base
	 * 
	 * Si l'Id de l'additif est à zero c'est qu'il n'est pas en base donc on
	 * l'y insère
	 * 
	 */
	public void insertAllCsv() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		for (Additif add : addNoDoublons) {

			if (add.getId() == 0) {

					em.getTransaction().begin();

					em.persist(add);

					em.getTransaction().commit();
					
			}

		}

		em.close();
		emf.close();

	}

	/**
	 * 
	 * Suprimme les doublons pour remplir la liste de tous les additifs
	 * 
	 * Si pas de resultat au findAll on part de zéro
	 * 
	 * Sinon la liste se remplit avec les resultats du findAll
	 * 
	 */
	private void noDoublons() {

		List<Additif> listAddDb = findAll();

		Set<String> nomAdd = new HashSet<>();

		if (listAddDb.isEmpty()) {

			for (Article article : listArtCsv) {

				List<Additif> addDeArticle = article.getListAdditifs();
				
				for (Additif add : addDeArticle) {
				
						nomAdd.add(add.getNom());
						
				}

			}

			for (String nom : nomAdd) {

				addNoDoublons.add(new Additif(nom));

			}

		} else {

			for (Additif addDb : listAddDb) {

				addNoDoublons.add(addDb);

			}

		}

	}

	/**
	 * @return the addNoDoublons
	 */
	public List<Additif> getAddNoDoublons() {
		return addNoDoublons;
	}

	/**
	 * @param addNoDoublons the addNoDoublons to set
	 */
	public void setAddNoDoublons(List<Additif> addNoDoublons) {
		this.addNoDoublons = addNoDoublons;
	}
	
}
