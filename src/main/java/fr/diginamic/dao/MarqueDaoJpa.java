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
import fr.diginamic.entities.Marque;
import fr.diginamic.traitementFichier.LectureFichier;
import fr.diginamic.traitementFichier.Parser;

public class MarqueDaoJpa {

	/**
	 * Liste des acticles à partir du Csv
	 */
	private List<Article> listArtCsv = Parser.parseFichier(LectureFichier.lireFichier());

	/**
	 * Liste des marques qui ne contiendra aucun doublons
	 */
	private List<Marque> marNoDoublons = new ArrayList<>();

	public MarqueDaoJpa() {
		noDoublons();
	}

	/**
	 * 
	 * Retourne une maque de la base grace à son nom
	 * 
	 * @param nomMar
	 * @return Marque || null
	 */
	public Marque findByMarName(String nomMar) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		Marque marRetour = null;

		if (em != null) {

			String querySelect = "SELECT mar FROM Marque mar WHERE mar.nom = '" + nomMar + "'";

			TypedQuery<Marque> listResultat = em.createQuery(querySelect, Marque.class);

			if (!listResultat.getResultList().isEmpty()) {

				marRetour = listResultat.getResultList().get(0);

			}

		}

		em.close();
		emf.close();

		return marRetour;

	}

	/**
	 * 
	 * Retourne toutes les marques de la base
	 * 
	 * @return List<Marque>
	 */
	public List<Marque> findAll() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		List<Marque> listMarDb = new ArrayList<>();

		if (em != null) {

			String querySelect = "SELECT mar FROM Marque mar";

			TypedQuery<Marque> listResultat = em.createQuery(querySelect, Marque.class);

			if (!listResultat.getResultList().isEmpty()) {

				listMarDb = listResultat.getResultList();

			}

		}

		em.close();
		emf.close();

		return listMarDb;

	}

	/**
	 * 
	 * Insère toutes les marques du CSV en base
	 * 
	 * Si l'Id de la marque est à zero c'est qu'elle n'est pas en base donc on
	 * l'y insère
	 * 
	 */
	public void insertAllCsv() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("base_open-food-facts");
		EntityManager em = emf.createEntityManager();

		for (Marque mar : marNoDoublons) {

			if (mar.getId() == 0) {

				em.getTransaction().begin();

				em.persist(mar);

				em.getTransaction().commit();

			}

		}

		em.close();
		emf.close();

	}

	/**
	 * 
	 * Suprimme les doublons pour remplir la liste de toutes les marques
	 * 
	 * Si pas de resultat au findAll on part de zéro
	 * 
	 * Sinon la liste se remplit avec les resultats du findAll
	 * 
	 */
	private void noDoublons() {

		List<Marque> listMarDb = findAll();

		Set<String> nomMar = new HashSet<>();

		if (listMarDb.isEmpty()) {

			for (Article article : listArtCsv) {

				nomMar.add(article.getMarque().getNom());

			}

			for (String string : nomMar) {

				marNoDoublons.add(new Marque(string));

			}

		} else {

			for (Marque marDb : listMarDb) {

				marNoDoublons.add(marDb);

			}

		}

	}

	/**
	 * @return the marNoDoublons
	 */
	public List<Marque> getMarNoDoublons() {
		return marNoDoublons;
	}

	/**
	 * @param marNoDoublons the marNoDoublons to set
	 */
	public void setMarNoDoublons(List<Marque> marNoDoublons) {
		this.marNoDoublons = marNoDoublons;
	}
}
