package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class ControleurAffichageRobot implements ActionListener{

	/**
	 * Attribut permettant de gerer les differents elements du model
	 */
	Gestionnaire gest;

	/**
	 * Constructeur de ControleurAffichageRobot
	 * @param g : le gestionnaire
	 */
	public ControleurAffichageRobot(Gestionnaire g) {
		gest = g;
	}

	/**
	 * Methode permettant de definir l'action a effectuer lors de l’interaction avec le controleur
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		JRadioButton jrb = (JRadioButton)arg0.getSource();

		if(jrb.getText().equals("Robot")){
			gest.afficherRobot(true);
		}
		else{
			gest.afficherRobot(false);
		}
	}
}
