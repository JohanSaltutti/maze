package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */

public class MainDroite extends AlgorithmeCheminementLocal {
	
	/**
	 * Attribut permettant de connaitre le mur en diagonal a droite
	 */
	private boolean murADroiteAvant ;

	/**
	 * Constructeur de MainDroite
	 * @param g : le gestionnaire 
	 */
	public MainDroite(Gestionnaire g) {
		super(g);
	}
	
	/**
	 * Methode qui permet de creer un algorithme selon le modele
	 */
	@Override
	public void initialiserAlgorithme() {
		super.initialiserAlgorithme();
		positionsRobot.add(AlgorithmeCheminementLocal.getEntreeRobot());
		nbEtats = 1;
	}
	
	/**
	 * Methode permettant d'executer l'algorithme
	 */
	public void executerAlgorithme(){
		
		etatCourant = nbEtats;
		
		gestionnaire.changerPositionRobot(positionsRobot.get(nbEtats - 1));
		
		Position p = gestionnaire.getRobot().getPosition();
		
		if(!gestionnaire.getLaby().getSortie().equals(p.getCase()) && !etreTermine){
			
			if(nbEtats == 1) {
				murADroiteAvant = !gestionnaire.murADroiteAvant().etreAccessible();
			}
			
			nbEtats++;
			
			if(!gestionnaire.caseADroite().etreAccessible()) {
				if(!gestionnaire.caseDevant().etreAccessible()) {
					gestionnaire.tournerAGaucheRobot();
				}
				else{
					gestionnaire.avancerRobot();
				}
			}
			else{
				if(murADroiteAvant) {
					gestionnaire.tournerADroiteRobot();
					gestionnaire.avancerRobot();
				}
				else{
					if(!gestionnaire.caseDevant().etreAccessible()) {
						gestionnaire.tournerAGaucheRobot();
						murADroiteAvant = true;
					}
					else{
						gestionnaire.avancerRobot();
					}
				}
			}
			

			positionsRobot.add(copierPosition(p));
		}
		else {
			etreTermine = true;
		}
	}
}
