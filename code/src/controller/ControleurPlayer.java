package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.AlgorithmeCheminement;
import model.AlgorithmeCheminementLocal;
import model.Gestionnaire;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class ControleurPlayer implements ActionListener, ChangeListener {

	/**
	 * Attribut permettant de gerer les différents éléments du model
	 */
	private Gestionnaire gestionnaire;

	/**
	 * Attribut permettant de gerer le player grace a un thread
	 */
	private Thread threadPlayer;

	/**
	 * Constructeur de ControleurPlayer
	 * @param g : le gestionnaire
	 */
	public ControleurPlayer(Gestionnaire g) {
		gestionnaire = g;
	}

	/**
	 * Methode permettant de definir l'action a effectuer lors de l’interaction avec le controleur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		AlgorithmeCheminement algo = ((AlgorithmeCheminement)(gestionnaire.getAlgoCourant()));

		String value = ((JButton)(e.getSource())).getName();
		int etat = algo.getEtatCourant(), nbEtats = algo.getNbEtats();

		switch (value) {

		case "reinitAlgo":

			algo.initialiserAlgorithme();
			replacerRobot(algo);
			gestionnaire.miseAJour();
			break;


		case "debutPlayer":
			algo.setEtatCourant(0);
			replacerRobot(algo);
			gestionnaire.miseAJour();
			break;

		case "retourPlayer":
			if(etat > 0) {
				algo.setEtatCourant(etat - 1);
				replacerRobot(algo);
				gestionnaire.miseAJour();
			}

			break;

		case "pausePlayer":
			algo.pause();
			break;

		case "demarrerPlayer":
			if(threadPlayer == null) {
				threadPlayer = new Thread() {
					public void run() {
						algo.demarrer();
						while(!algo.etreEnPause()) {
							if(algo.getEtatCourant() != algo.getNbEtats()){
								algo.setEtatCourant(algo.getEtatCourant() + 1);
								replacerRobot(algo);
								gestionnaire.miseAJour();
							}
							else{
								algo.executerAlgorithme();
								if(!algo.etreTermine()) {
									algo.setEtatCourant(algo.getEtatCourant() + 1);
									replacerRobot(algo);
									gestionnaire.miseAJour();
								}
								else {
									algo.pause();
								}

							}
							try {
								Thread.sleep(algo.getSleepTime());
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
						threadPlayer = null;
					}
				};
				threadPlayer.start();
			}
			break;

		case "avantPlayer": 

			if(etat != nbEtats){
				algo.setEtatCourant(etat + 1);
				replacerRobot(algo);
				gestionnaire.miseAJour();
			}
			else{
				algo.executerAlgorithme();
				if(!algo.etreTermine()) {
					algo.setEtatCourant(algo.getEtatCourant() + 1);
					replacerRobot(algo);
					gestionnaire.miseAJour();
				}

			}

			break;
		}
	}

	/**
	 * Methode permettant de replacer le robot sur une case ou sur l'entree
	 * @param algo : l'algorithme de cheminement
	 */
	private void replacerRobot(AlgorithmeCheminement algo) {

		if(algo instanceof AlgorithmeCheminementLocal) {
			if(algo.getEtatCourant() != 0) {
				gestionnaire.changerPositionRobot(((AlgorithmeCheminementLocal)(algo)).getPositionsRobot().get(algo.getEtatCourant() - 1));
			}
			else {
				gestionnaire.changerPositionRobot(AlgorithmeCheminementLocal.getEntreeRobot());
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider s = (JSlider)e.getSource();
		switch(s.getName()) {

		case "states": 
			AlgorithmeCheminement a = (AlgorithmeCheminement) gestionnaire.getAlgoCourant();

			a.setEtatCourant(s.getValue());
			replacerRobot(a);
			
			gestionnaire.miseAJour();	
			break;

		case "speed":
			int time = 2000 - s.getValue();
			((AlgorithmeCheminement)(gestionnaire.getAlgoCourant())).setSleepTime(time);
			break;
		}

	}

}