package fr.diginamic.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import fr.diginamic.entities.Additif;
import fr.diginamic.entities.Allergene;
import fr.diginamic.entities.Ingredient;
import fr.diginamic.traitementFichier.Splitter;

public class StringConvert {

	/**
	 * Convertit une chaine de caractères en Double, ou retourne null si la chaine
	 * ne contient pas un nombre décimal.
	 * 
	 * @param value valeur à convertir
	 * @return Integer
	 */
	public static Double toDouble(String value) {
		if (NumberUtils.isCreatable(value)) {
			return Double.parseDouble(value);
		}
		return 0.0;
	}
	
	/**
	 * Convertit une chaine de caractères en liste d'ingredients
	 * 
	 * @param ligne
	 * @return liste d'ingredients
	 */
	public static List<Ingredient> toIngredientList(String ligne) {
		
		List<String> morceaux = Splitter.splitChaine(ligne);
		
		List<Ingredient> listIng = new ArrayList<>();
		
		for (String morceau : morceaux) {
			
			if (morceau.length() < 255) {
			
				listIng.add(new Ingredient(morceau));
				
			}
			
		}
		
		return listIng;
		
	}
	
	/**
	 * Convertit une chaine de caractères en liste d'allergènes
	 * 
	 * @param ligne
	 * @return liste d'allergenes
	 */
	public static List<Allergene> toAllergeneList(String ligne) {
		
		List<String> morceaux = Splitter.splitChaine(ligne);
		
		List<Allergene> listAll = new ArrayList<>();
		
		for (String morceau : morceaux) {
			
			listAll.add(new Allergene(morceau));
			
		}
		
		return listAll;
		
	}
	
	/**
	 * Convertit une chaine de caractères en liste d'ingredients
	 * 
	 * @param ligne
	 * @return liste d'ingredients
	 */
	public static List<Additif> toAdditifList(String ligne) {
		
		List<String> morceaux = Splitter.splitChaine(ligne);
		
		List<Additif> listAdd = new ArrayList<>();
		
		for (String morceau : morceaux) {
			
			listAdd.add(new Additif(morceau));
			
		}
		
		return listAdd;
		
	}
	
}
