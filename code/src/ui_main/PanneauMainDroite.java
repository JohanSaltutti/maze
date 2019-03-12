																																																																																		package ui_main;import java.awt.GridLayout;

import javax.swing.JPanel;

import model.Algorithme;
import model.Gestionnaire;
import view.DessineurLabyrinthe;
import view.VueLabyrinthe;
import view.VueMainDroite;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class PanneauMainDroite extends PanneauAlgoLocal {

	/**
	 * Constructeur de PanneauMainDroite
	 * @param g : le gestionnaire
	 * @param a : l'algorithme
	 */
	public PanneauMainDroite(Gestionnaire g, Algorithme a) {
		super(g, a);
	}

	@Override
	public VueLabyrinthe vue() {
		return new VueMainDroite(gestionnaire);	
	}	

	@Override
	public JPanel legend() {
		JPanel p = new JPanel(new GridLayout(2, 2));

		JPanel p1 = creerLegende(DessineurLabyrinthe.COULEUR_ENTREE, "Entree");
		JPanel p2 = creerLegende(DessineurLabyrinthe.COULEUR_SORTIE, "Sortie");
		JPanel p3 = creerLegende(DessineurLabyrinthe.COULEUR_CASES_PARCOURUES, "Case Parcourue");
		JPanel p4 = creerLegende(DessineurLabyrinthe.COULEUR_MUR_MAIN_DROITE, "Mur sur lequel on s'appuie");
		
		p.add(p1);
		p.add(p2);
		p.add(p3);
		p.add(p4);

		return p;
	}
}