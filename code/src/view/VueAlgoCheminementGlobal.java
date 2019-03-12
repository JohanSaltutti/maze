package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashSet;

import model.AlgorithmeCheminementGlobal;
import model.Case;
import model.Coordonnees;
import model.Gestionnaire;
import model.Noeud;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class VueAlgoCheminementGlobal extends VueLabyrintheChemin{

	/**
	 * Constantes representant les fleches des cases parentes
	 */
	private static final String FLECHE_BAS = "\u2304", FLECHE_HAUT = "\u2303", FLECHE_DROITE = "\u203A", FLECHE_GAUCHE = "\u2039";

	/**
	 * Constructeur de VueAlgoCheminementGlobal
	 * @param g : le gestionnaire
	 */
	public VueAlgoCheminementGlobal(Gestionnaire g) {
		super(g);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gg = (Graphics2D) g;
		AlgorithmeCheminementGlobal algo = (AlgorithmeCheminementGlobal) gestionnaire.getAlgoCourant();

		int etat = algo.getEtatCourant();

		if(etat != 0) {

			Case caseCourante = algo.getCasesCourantes().get(etat - 1);
			ArrayList<ArrayList<Noeud>> listeOuverte = algo.getListeOpenList();
			HashSet<Noeud> open = new HashSet<Noeud>();

			int etatSortieTrouvee = algo.getEtatSortieTrouvee();
			int bSup = etat - 1;

			if(etatSortieTrouvee != -1 && etat - 1 >= etatSortieTrouvee) {
				bSup = etatSortieTrouvee;
			}

			ArrayList<Noeud> listeFermee = new ArrayList<>(algo.getListeCloseList().subList(0, bSup + 1));

			for(int i = 0 ; i <= bSup; i++) {
				Noeud no = listeFermee.get(i);
				
				dess.dessinerCase(g, TAILLE_CASE, no.getCase(), DessineurLabyrinthe.COULEUR_CASES_PARCOURUES);

				for(int j = 0 ; j < listeOuverte.get(i).size() ; j++) {
					Noeud n = listeOuverte.get(i).get(j);
					if(!listeFermee.contains(n)) {
						open.add(n);
					}
				}
				
				if(no.getParent() != null && algo.getAfficherParent()) {
					dessinerFleche(g, no);
				}
			}

			for(Noeud n : open) {

				dess.dessinerCase(g, TAILLE_CASE, n.getCase(), DessineurLabyrinthe.COULEUR_ADJACENTS);
				//valuations				

				if(algo.getAfficherValuation()) {
					
					Coordonnees c = n.getCase().getCoord();
					gg.setColor(DessineurLabyrinthe.COULEUR_VALUATIONS);

					String cf = n.getCoutF() + "", ch = n.getCoutH() + "", cg = n.getCoutG() + ""; 
					String longestOne = cf;

					if(cf.length() < cg.length()) {
						longestOne = cg;
					}

					if(longestOne.length() < ch.length()) {
						longestOne = ch;
					}

					gg.setFont(dess.creerPolice(longestOne, 0.3, 0.3, TAILLE_CASE));

					gg.drawString(cg , c.x * TAILLE_CASE +1 + 2, c.y * TAILLE_CASE +1 + TAILLE_CASE/4);

					gg.drawString(ch + "" , c.x * TAILLE_CASE +1 + TAILLE_CASE/2 + TAILLE_CASE/12, c.y * TAILLE_CASE +1 + TAILLE_CASE/4);

					gg.drawString(cf + "" , c.x * TAILLE_CASE +1 + 2, c.y * TAILLE_CASE +1 + TAILLE_CASE - 5);
				}

				if(algo.getAfficherParent()) {
					dessinerFleche(g, n);
				}
			}

			//chemin minimal
			if(etatSortieTrouvee != -1 && etat - 1 >= etatSortieTrouvee) {

				ArrayList<Noeud> chemin = algo.getCheminFinal();

				for(int i = 0 ; i < etat - etatSortieTrouvee; i++) {
					Noeud n = chemin.get(i);
					
					dess.dessinerCase(g, TAILLE_CASE, n.getCase(), DessineurLabyrinthe.COULEUR_CHEMIN_FINAL);
					
					if(algo.getAfficherParent() && n.getParent() != null) {
						dessinerFleche(g, chemin.get(i));
					}
				}
			}

			dess.dessinerEntree(gg, TAILLE_CASE);
			dess.dessinerSortie(gg, TAILLE_CASE);

			//Case courante
			dess.dessinerCase(g, TAILLE_CASE, caseCourante, DessineurLabyrinthe.COULEUR_CASE_COURANTE);
		}
		else {
			dess.dessinerEntree(gg, TAILLE_CASE);
			dess.dessinerSortie(gg, TAILLE_CASE);
		}
	}

	private void dessinerFleche(Graphics g, Noeud n){

		Graphics2D gg = (Graphics2D)g;
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		gg.setColor(DessineurLabyrinthe.COULEUR_VALUATIONS);

		Coordonnees c = n.getCase().getCoord(), p = n.getParent().getCase().getCoord();

		String direction;

		if(p.y < c.y) {
			direction = FLECHE_HAUT;
			gg.setFont(dess.creerPolice(direction, 0.5, 0.5, TAILLE_CASE));
			gg.drawString(direction, c.x * TAILLE_CASE + TAILLE_CASE/3, c.y * TAILLE_CASE + TAILLE_CASE - TAILLE_CASE/5);
		}	
		else if (p.y > c.y) {
			direction = FLECHE_BAS;
			gg.setFont(dess.creerPolice(direction, 0.5, 0.5, TAILLE_CASE));
			gg.drawString(direction, c.x * TAILLE_CASE + TAILLE_CASE/3, c.y * TAILLE_CASE + TAILLE_CASE/2 + TAILLE_CASE/12);

		}	
		else if (p.x < c.x) {
			direction = FLECHE_GAUCHE;
			gg.setFont(dess.creerPolice(direction, 0.5, 0.5, TAILLE_CASE));
			gg.drawString(direction, c.x * TAILLE_CASE + 2*(TAILLE_CASE/5) + TAILLE_CASE/15, c.y * TAILLE_CASE + TAILLE_CASE/2 + TAILLE_CASE/12 + TAILLE_CASE/15);

		}	
		else {
			direction = FLECHE_DROITE;
			gg.setFont(dess.creerPolice(direction, 0.5, 0.5, TAILLE_CASE));
			gg.drawString(direction, c.x * TAILLE_CASE + 2*(TAILLE_CASE/5) + TAILLE_CASE/15, c.y * TAILLE_CASE + TAILLE_CASE/2 + TAILLE_CASE/8);
		}
	}
}