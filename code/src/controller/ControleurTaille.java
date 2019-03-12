package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSpinner;

import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class ControleurTaille implements ActionListener {
	
	/**
	 * Attribut permettant de gerer les differents elements du model
	 */
	private Gestionnaire gestionnaire;
	
	/**
	 * Attribut permettant de selectionner le nombre de lignes ou de colonnes
	 */
	private JSpinner JSpinnerLignes, JSpinnerColonnes;
	
	/**
	 * Constructeur de ControleurTaille
	 * @param g : le gestionnaire
	 * @param jslignes : le JSpinner permettant de selectionner le nombre de lignes
	 * @param jscolonnes : le JSpinner permettant de selectionner le nombre de colonnes
	 */
	public ControleurTaille(Gestionnaire g, JSpinner jslignes, JSpinner jscolonnes) {
		gestionnaire = g;
		JSpinnerLignes = jslignes;
		JSpinnerColonnes = jscolonnes;
	}
	
	/**
	 * Methode permettant de definir l'action a effectuer lors de l’interaction avec le controleur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int lignes = (int)JSpinnerLignes.getValue();
		int colonnes = (int)JSpinnerColonnes.getValue();
		
		gestionnaire.changerTailleLaby(lignes, colonnes);
	}

}
