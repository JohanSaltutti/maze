package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

import model.AlgorithmeCheminementLocal;
import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class ControleurDeplacementRobot implements KeyListener, ActionListener{

	/**
	 * Attribut permettant de gerer les differents elements du model
	 */
	private Gestionnaire gestionnaire;

	/**
	 * Constructeur de ControleurDeplacementRobot
	 * @param g : le gestionnaire
	 */
	public ControleurDeplacementRobot(Gestionnaire g) {
		gestionnaire = g;
	}

	/**
	 * Methode permettant de definir l'action a effectuer lors de l’interaction avec le controleur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String choix = ((JButton)(e.getSource())).getName();
		switch (choix) {
		case "Avancer":
			gestionnaire.avancerRobot();
			break;
		case "Gauche":
			gestionnaire.tournerAGaucheRobot();
			break;
		case "Droite":
			gestionnaire.tournerADroiteRobot();
			break;
		case "Orientation":
			gestionnaire.placerRobot();
			gestionnaire.initialiserAlgorithmes();
			gestionnaire.tournerADroiteRobot();
			AlgorithmeCheminementLocal.setEntreeRobot(gestionnaire.getRobot().getPosition());
			break;

		}
	}

	/**
	 * Methode permettant de gerer les clics du clavier
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_Z:
			gestionnaire.avancerRobot();
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_Q:
			gestionnaire.tournerAGaucheRobot();
			break;

		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			gestionnaire.tournerADroiteRobot();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent e) {}

}
