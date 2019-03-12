package controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class ControleurMode implements ActionListener {
	
	/**
	 * Attribut permettant de gerer les differents elements du model
	 */
	private Gestionnaire gestionnaire;
	
	/**
	 * Constructeur de ControleurMode
	 * @param g : le gestionnaire
	 */
	public ControleurMode(Gestionnaire g) {
		gestionnaire = g;
	}
	
	/**
	 * Methode permettant de definir l'action a effectuer lors de l’interaction avec le controleur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String value = ((JButton)(e.getSource())).getText();
		gestionnaire.setChoixCreationCase(value);
	}
	
}