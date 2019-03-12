package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import model.Gestionnaire;
import model.Position;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class ControleurDeplacementLaby implements ActionListener {
	
	/**
	 * Attribut permettant de gerer les differents elements du model
	 */
	private Gestionnaire gestionnaire;
	
	/**
	 * Constructeur de ControleurDeplacementLaby
	 * @param g : le gestonaire
	 */
	public ControleurDeplacementLaby(Gestionnaire g) {
		gestionnaire = g;
	}

	/**
	 * Methode permettant de definir l'action a effectuer lors de l’interaction avec le controleur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String value = ((JButton)(e.getSource())).getName();		
		switch (value) {
		case "Haut":
			gestionnaire.deplacerLaby(Position.NORD);
			break;

		case "Bas":
			gestionnaire.deplacerLaby(Position.SUD);
			break;
			
		case "Gauche":
			gestionnaire.deplacerLaby(Position.OUEST);
			break;
			
		case "Droite":
			gestionnaire.deplacerLaby(Position.EST);
			break;
		}
	}

}
