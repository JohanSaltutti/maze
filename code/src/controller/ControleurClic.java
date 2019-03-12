package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.Case;
import model.Gestionnaire;
import view.VueLabyrinthe;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class ControleurClic implements MouseListener, MouseMotionListener {

	/**
	 * Attribut permettant de gerer les differents elements du model
	 */
	private Gestionnaire gestionnaire;
	
	/**
	 * Attribut permettant d'avoir la vue du labyrinthe
	 */
	private VueLabyrinthe vueLaby;
	
	/**
	 * Attribut entier representant la taille d'une case du labyrinthe
	 */
	private int tailleCase;
	
	/**
	 * Constructeur de ControleurClic
	 * @param g : le gestionnaire
	 * @param vl : la vue du labyrinthe
	 */
	public ControleurClic(Gestionnaire g, VueLabyrinthe vl) {
		gestionnaire = g;
		vueLaby = vl;
	}
	
	/**
	 * Methode permettant de gerer les clics de souris
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
		tailleCase = vueLaby.getTailleCase();
		
		int x = e.getX() / tailleCase;
		int y = e.getY() / tailleCase;
				
		if(! gestionnaire.getLaby().etreMurExterieur(x, y)) { 
			
			String choix = gestionnaire.getChoixCreationCase();
			Case c = gestionnaire.getLaby().getCarte()[x][y];
			switch (choix) {
			case "Mur":
				gestionnaire.creerCaseMur(c);
				break;

			case "Vide":
				gestionnaire.creerCaseVide(c);
				break;
				
			case "Entree":
				gestionnaire.creerCaseEntree(c);
				break;
				
			case "Sortie":
				gestionnaire.creerCaseSortie(c);
				break;
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mousePressed(arg0);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}

}
