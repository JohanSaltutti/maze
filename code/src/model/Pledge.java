package model;

import java.util.ArrayList;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */

public class Pledge extends AlgorithmeCheminementLocal{

	/**
	 * Attribut representant la liste de compteurs
	 */
	private ArrayList<Integer> compteurs;
	
	/**
	 * Attribut representant le compteur d'etape de l'algorithme 
	 */
	private int compteur;
	
	/**
	 * Constantes representant le mouvement tout droit et a droite
	 */
	public static final int TOUT_DROIT = 0, MAIN_DROITE = 1; 

	/**
	 * Attribut representant le mode actuel d'execution
	 */
	private int mode;

	/**
	 * Constructeur de Pledge
	 * @param g : le gestionnaire 
	 */
	public Pledge(Gestionnaire g) {
		super(g);
	}

	/**
	 * Methode qui permet de creer un algorithme selon le modele
	 */
	@Override
	public void initialiserAlgorithme() {
		super.initialiserAlgorithme();
		compteur = 0;
		mode = TOUT_DROIT;
		compteurs = new ArrayList<Integer>();
		positionsRobot.add(AlgorithmeCheminementLocal.getEntreeRobot());
		compteurs.add(0);
		nbEtats = 1;
	}

	/**
	 * Methode permettant d'execute l'algorithme
	 */
	public void executerAlgorithme(){
		etatCourant = nbEtats;

		gestionnaire.changerPositionRobot(positionsRobot.get(nbEtats - 1));
		
		Position p = gestionnaire.getRobot().getPosition();
		
		if(!gestionnaire.getLaby().getSortie().equals(p.getCase()) && !etreTermine){
			
			nbEtats++;
			
			if(mode == TOUT_DROIT) {
				if(gestionnaire.caseDevant().etreAccessible()) {
					gestionnaire.avancerRobot();
				}
				else {
					gestionnaire.tournerAGaucheRobot();
					compteur--;
					mode = MAIN_DROITE;
				}
			}
			else {
				if(!gestionnaire.caseADroite().etreAccessible()) {
					if(!gestionnaire.caseDevant().etreAccessible()) {
						gestionnaire.tournerAGaucheRobot();
						compteur--;
					}
					else {
						gestionnaire.avancerRobot();
					}
				}
				else {
					gestionnaire.tournerADroiteRobot();
					gestionnaire.avancerRobot();
					compteur++;
				}

				if(compteur == 0) {
					mode = TOUT_DROIT;
				}
			}
			
			compteurs.add(compteur);
			positionsRobot.add(copierPosition(p));
		}
		else {
			etreTermine = true;
		}
	}

	/**
	 * Getter de compteurs
	 * @return la liste des compteurs
	 */
	public ArrayList<Integer> getCompteurs() {
		return compteurs;
	}
	
	/**
	 * Getter de mode
	 * @return le mode
	 */
	public int getMode() {
		return mode;
	}
}
