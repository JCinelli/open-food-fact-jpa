package fr.diginamic.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "marque")
public class Marque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "nom", length = 255, nullable = false)
	private String nom;

	@OneToMany (mappedBy = "marque")
	List<Article> articleQuiOntMar;
	
//	CONSTRUCTORS
	public Marque() {
		super();
	}

	public Marque(String nom) {
		super();
		this.nom = nom;
	}

	public Marque(int id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}

//  METHODS
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

}
