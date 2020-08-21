package fr.diginamic.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ingredient")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	@Column(name = "nom", length = 255, nullable = false)
	private String nom;
	
	
	@ManyToMany(mappedBy = "listIngredients")
	private List<Article> listArticlesQuiContiennentIng;

//	CONSTRUCTOR
	public Ingredient() {
		super();
	}

	public Ingredient(String nom) {
		super();
		this.nom = nom;
	}

	public Ingredient(int id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}

	// METHODS
	@Override
	public String toString() {
		return "Id : " + id + " | Nom : " + nom;
	}

//	GETTERS & SETTERS
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the listArticles
	 */
	public List<Article> getListArticlesQuiContiennentIng() {
		return listArticlesQuiContiennentIng;
	}

	/**
	 * @param listArticles the listArticles to set
	 */
	public void setListArticlesQuiContiennentIng(List<Article> listArticles) {
		this.listArticlesQuiContiennentIng = listArticles;
	}

}
