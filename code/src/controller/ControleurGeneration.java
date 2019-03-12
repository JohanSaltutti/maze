package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.FabriqueLabyrinthe;
import model.FabriqueLabyrintheIlot;
import model.FabriqueLabyrintheImparfait;
import model.FabriqueLabyrintheParfait;
import model.FabriqueLabyrinthePlein;
import model.FabriqueLabyrintheVide;
import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class ControleurGeneration implements ActionListener {
	
	/**
	 * Attribut permettant de gerer les differents elements du model
	 */
	private Gestionnaire gestionnaire;
	
	/**
	 * Constructeur de ControleurGeneration
	 * @param g : le gestionnaire
	 */
	public ControleurGeneration(Gestionnaire g) {
		gestionnaire = g;
	}
	
	/**
	 * Methode permettant de definir l'action a effectuer lors de l’interaction avec le controleur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String value = ((JButton)(e.getSource())).getText();
		
		FabriqueLabyrinthe nouvelleFabrique = null;
		
		switch(value) {
		case "Remplir" :
			nouvelleFabrique = new FabriqueLabyrinthePlein(gestionnaire);
			break;
		case "Ilot":
			nouvelleFabrique = new FabriqueLabyrintheIlot(gestionnaire);
			break;
		case "Vider":
			nouvelleFabrique = new FabriqueLabyrintheVide(gestionnaire);
			break;
		case "Parfait":
			nouvelleFabrique = new FabriqueLabyrintheParfait(gestionnaire);
			break;
		case "Imparfait":
			nouvelleFabrique = new FabriqueLabyrintheImparfait(gestionnaire);
			break;
		}
		
		gestionnaire.fabriquerNouveauLabyrinthe(nouvelleFabrique);
	}
}