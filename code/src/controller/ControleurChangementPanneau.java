package controller;

import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Algorithme;
import model.AlgorithmeCheminement;
import model.AlgorithmeCheminementLocal;
import model.Gestionnaire;
import ui_main.Panneau;
import ui_main.PanneauAlgo;
import ui_main.PanneauCreation;
import view.Vue;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 * 
 * Classe permettant d'"aiguiller" les observateurs sur les bonnes vues et sur l'algorithme courant dans le panneau selectionne
 */
public class ControleurChangementPanneau implements ChangeListener{

	private Gestionnaire gest;
	private ArrayList<ArrayList<Panneau>> listePanneaux;
	
	/**
	 * Constructeur de ControleurChangementPanneau
	 */
	public ControleurChangementPanneau(Gestionnaire g, ArrayList<ArrayList<Panneau>> pl){
		gest = g;
		listePanneaux = pl;
	}

	/**
	 * Methode permettant de definir l'action a effectuer lors du changement d'etat du controleur
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		gest.deleteObservers();
		
		JTabbedPane tabbedPane = ((JTabbedPane)(e.getSource()));
		int i = tabbedPane.getSelectedIndex();

		Panneau panneauCourant = null;
		
		//on trouve le nouveau panneau selectionne
		if(listePanneaux.get(i).size() > 1) {
			//si c'est un JTabbedPane, on choisit le panneau courant de celui-ci
			panneauCourant = (Panneau) ((JTabbedPane)(tabbedPane.getSelectedComponent())).getSelectedComponent();
		}
		else {
			//Si c'est un panneau classique, on le choisit
			panneauCourant = listePanneaux.get(i).get(0);
		}
		
		//on met en pause la lecture de l'algorithme sur le panneau precedent s'il était en mode lecture
		Algorithme ancienAlgo = gest.getAlgoCourant();
		if(ancienAlgo instanceof AlgorithmeCheminement) {
			((AlgorithmeCheminement)ancienAlgo).pause();
		}
		
		//on change l'algorithme courant en fonction du panneau sur lequel on se trouve
		if(!(panneauCourant instanceof PanneauCreation)) {
			gest.setAlgoCourant(panneauCourant.getAlgoCourant());
			
			Algorithme algo = gest.getAlgoCourant();
			if(algo instanceof AlgorithmeCheminementLocal) {
				if(((AlgorithmeCheminement) algo).getEtatCourant() != 0) {
					gest.changerPositionRobot(  ((AlgorithmeCheminementLocal)(algo)).getPositionsRobot().get(((AlgorithmeCheminement) algo).getEtatCourant() - 1)   );
				}
				else {
					gest.changerPositionRobot(AlgorithmeCheminementLocal.getEntreeRobot());
				}
			}
		}
		
		//on fait observer toutes les vues contenues dans ce panneau
		for(Vue v : panneauCourant.getVues()) {
			gest.addObserver(v);
		}
		
		//si c'est un panneau avec un player, on fait observer aussi son slider d'etats
		if(panneauCourant instanceof PanneauAlgo){
			gest.addObserver(((PanneauAlgo) panneauCourant).getPlayer().getSliderEtats());
		}
	}
}
