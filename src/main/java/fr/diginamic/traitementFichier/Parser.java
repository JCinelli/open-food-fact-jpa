package fr.diginamic.traitementFichier;

import java.util.ArrayList;
import java.util.List;

import fr.diginamic.entities.Additif;
import fr.diginamic.entities.Allergene;
import fr.diginamic.entities.Categorie;
import fr.diginamic.entities.Ingredient;
import fr.diginamic.entities.Marque;
import fr.diginamic.utils.StringConvert;
import fr.diginamic.entities.Article;

public class Parser {

	public static List<Article> parseFichier(List<String> lignesFichier) {
		
		List<Article> listProduits = new ArrayList<Article>();
		
//		Suppression de la premiere ligne car inutile
		lignesFichier.remove(0);
		
//		Parcours de chaque lignes du fichier
		for (String ligne : lignesFichier) {
			
			String ligneNet = ligne.replaceAll("'", " ");

			
//			Decoupages de la ligne en plusieurs morceaux correspondant aux attributs d'un produit
			String[] morceaux = ligneNet.split("\\|", -1);
			
			Categorie categorie = new Categorie(morceaux[0]);
			Marque marque = new Marque(morceaux[1]);
			String nom = morceaux[2];
			String grade = morceaux[3];
			List<Ingredient> listIngredients = StringConvert.toIngredientList(morceaux[4].replaceAll(" - ", ","));
			double energie100g =  StringConvert.toDouble(morceaux[5]);
			double graisse100g =  StringConvert.toDouble(morceaux[6]);
			double sucres100g =  StringConvert.toDouble(morceaux[7]);
			double fibres100g =  StringConvert.toDouble(morceaux[8]);
			double proteines100g =  StringConvert.toDouble(morceaux[9]);
			double sel100g =  StringConvert.toDouble(morceaux[10]);
			double vitA100g =  StringConvert.toDouble(morceaux[11]);
			double vitD100g =  StringConvert.toDouble(morceaux[12]);
			double vitE100g =  StringConvert.toDouble(morceaux[13]);
			double vitK100g =  StringConvert.toDouble(morceaux[14]);
			double vitC100g =  StringConvert.toDouble(morceaux[15]);
			double vitB1100g =  StringConvert.toDouble(morceaux[16]);
			double vitB2100g =  StringConvert.toDouble(morceaux[17]);
			double vitPP100g =  StringConvert.toDouble(morceaux[18]);
			double vitB6100g =  StringConvert.toDouble(morceaux[19]);
			double vitB9100g =  StringConvert.toDouble(morceaux[20]);
			double vitB12100g =  StringConvert.toDouble(morceaux[21]);
			double calcium100g =  StringConvert.toDouble(morceaux[22]);
			double magnesium100g =  StringConvert.toDouble(morceaux[23]);
			double iron100g =  StringConvert.toDouble(morceaux[24]);
			double fer100g =  StringConvert.toDouble(morceaux[25]);
			double betaCarotene100g =  StringConvert.toDouble(morceaux[26]);
			double presenceHuilePalme =  StringConvert.toDouble(morceaux[27]);
			List<Allergene> listAllergenes = StringConvert.toAllergeneList(morceaux[28]);
			List<Additif> listAdditifs = StringConvert.toAdditifList(morceaux[29]);
			
			Article produit = new Article(categorie, marque, nom, grade, listIngredients, energie100g, graisse100g, sucres100g, 
					fibres100g, proteines100g, sel100g, vitA100g, vitD100g, vitE100g, vitK100g, vitC100g, vitB1100g, vitB2100g, 
					vitPP100g, vitB6100g, vitB9100g, vitB12100g, calcium100g, magnesium100g, iron100g, fer100g, betaCarotene100g, 
					presenceHuilePalme, listAllergenes, listAdditifs);
			
			listProduits.add(produit);
			
		}
		
		return listProduits;
		
	}
	
}
