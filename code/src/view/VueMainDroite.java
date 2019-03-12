package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import model.Case;
import model.Gestionnaire;
import model.MainDroite;
import model.Position;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class VueMainDroite extends VueLabyrintheChemin {
	
	/**
	 * Constructeur
	 * @param g : le gestionnaire associe
	 */
	public VueMainDroite(Gestionnaire g) {
		super(g);
	}

	public void paintComponent(Graphics g){
		
		super.paintComponent(g); 
		
		MainDroite algo = (MainDroite) gestionnaire.getAlgoCourant();
		int etat = algo.getEtatCourant();
	
		if(etat != 0) {

			ArrayList<Position> pos = algo.getPositionsRobot();
			
		
			for(int i = 0 ; i < etat - 1 ; i++) {
				dess.dessinerCase(g, TAILLE_CASE, pos.get(i).getCase(), DessineurLabyrinthe.COULEUR_CASES_PARCOURUES);
			}
			
			//mur sur lequel on s'appuie
			Case caseADroite = gestionnaire.caseADroite();
			if(!caseADroite.etreAccessible()) {
				dess.dessinerCase(g, TAILLE_CASE, caseADroite, Color.GRAY);
			}
			
			dess.dessinerEntree(g, TAILLE_CASE);
			dess.dessinerSortie(g, TAILLE_CASE);
			dess.dessinerRobot(g, TAILLE_CASE, false);
		}
		else {
			dess.dessinerEntree(g, TAILLE_CASE);
			dess.dessinerSortie(g, TAILLE_CASE);
			dess.dessinerRobot(g, TAILLE_CASE, false);
		}
	}
}