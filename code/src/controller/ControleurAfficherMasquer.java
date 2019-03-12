package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import model.AlgorithmeCheminement;
import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class ControleurAfficherMasquer implements ActionListener{

	/**
	 * Attribut permettant de gerer les differents elements du model
	 */
	private Gestionnaire gestionnaire;
	
	/**
	 * Constructeur de ControleurAfficherMasquer
	 * @param g : le gestionnaire
	 */
	public ControleurAfficherMasquer(Gestionnaire g) {
		gestionnaire = g;
	}
	
	/**
	 * Methode permettant de definir l'action a effectuer lors de l’interaction avec le controleur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		AlgorithmeCheminement algo = (AlgorithmeCheminement) gestionnaire.getAlgoCourant();
		
		switch(((JCheckBox)e.getSource()).getText()) {
		case "Parents" :
			algo.changeParent();
			break;
			
		case "Valuations":
			algo.changeValuation();
			break;
		}
		
		gestionnaire.miseAJour();
	}
}