package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import model.Case;
import model.Gestionnaire;
import model.Position;
import model.Tremaux;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class VueTremaux extends VueLabyrintheChemin {

	/**
	 * Contructeur de VueTremaux
	 * @param g : le gestionnaire
	 */
	public VueTremaux(Gestionnaire g) {
		super(g);
	}

	public void paintComponent(Graphics g){

		super.paintComponent(g);
		Graphics2D gg = (Graphics2D) g;

		Tremaux algo = (Tremaux) gestionnaire.getAlgoCourant();

		int etat = algo.getEtatCourant();

		if(etat != 0) {
			ArrayList<Position> pos = algo.getPositionsRobot();

			//affichage des cases qui ont deja ete traitees
			for(int i = 0 ; i < etat - 1 ; i++) {
				dess.dessinerCase(g, TAILLE_CASE, pos.get(i).getCase(), DessineurLabyrinthe.COULEUR_CASES_PARCOURUES);	
			}

			//backtracking du chemin
			int etatSortieTrouvee = algo.getEtatSortieTrouvee();
			if(etatSortieTrouvee != -1 && etat - 1 >= etatSortieTrouvee) {

				ArrayList<Case> chemin = algo.getCheminFinal();

				for(int i = 0 ; i <= etat - etatSortieTrouvee -1; i++) {
					dess.dessinerCase(g, TAILLE_CASE, chemin.get(i), DessineurLabyrinthe.COULEUR_CHEMIN_FINAL);
				}
			}

			dess.dessinerEntree(g, TAILLE_CASE);
			dess.dessinerSortie(g, TAILLE_CASE);

			//affichage des cailloux
			HashMap<Position, Integer> listeCailloux = algo.getListeCailloux().get(etat - 1);
			int tailleCaillou = TAILLE_CASE/3;

			for(Position p : listeCailloux.keySet()) {
				Case c = p.getCase();

				int x = c.getCoord().x * TAILLE_CASE + 1;
				int y = c.getCoord().y * TAILLE_CASE + 1;

				switch(p.getDirection()) {

				case Position.NORD:{
					x += tailleCaillou;
					break;
				}

				case Position.EST:{
					x += 2 * tailleCaillou;
					y += tailleCaillou;
					break;
				}

				case Position.SUD:{
					x += tailleCaillou;
					y += 2 * tailleCaillou;
					break;
				}	

				case Position.OUEST:{
					y += tailleCaillou;
					break;
				}				
				}

				gg.setColor((listeCailloux.get(p) == 2)? DessineurLabyrinthe.COULEUR_CAILLOU_DOUBLE : DessineurLabyrinthe.COULEUR_CAILLOU_SIMPLE);

				gg.fillOval(x, y, tailleCaillou, tailleCaillou);
			}

			dess.dessinerRobot(g, TAILLE_CASE, true);
		}	
		else {
			dess.dessinerEntree(g, TAILLE_CASE);
			dess.dessinerSortie(g, TAILLE_CASE);
			dess.dessinerRobot(g, TAILLE_CASE, true);
		}
	}
}
