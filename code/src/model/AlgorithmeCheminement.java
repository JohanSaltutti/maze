package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public abstract class AlgorithmeCheminement extends Algorithme {

	/**
	 * Attributs representant les etats courants, le nombre d'etats, le temps entre chaque etapes et l'etat lorsque la sortie est trouvee
	 */
	protected volatile int etatCourant = 0, nbEtats, sleepTime = 500, etatSortieTrouvee = -1;
	
	/**
	 * Attributs representant l'etat de pause et de la fin
	 */
	protected volatile boolean pause = true, etreTermine = false;
	
	/**
	 * Attributs representant l'affichage des valuations et des parents
	 */
	protected boolean afficherValuation = true, afficherParent = true;

	/**
	 * Constructeur de AlgorithmeCheminement
	 * @param g : le gestionnaire
	 */
	protected AlgorithmeCheminement(Gestionnaire g) {
		super(g);
	}
	
	@Override
	public void initialiserAlgorithme() {
		etatCourant = 0;
		etreTermine = false;
		nbEtats = 0;
		pause = true;
		etatSortieTrouvee = -1;
	}
	
	/**
	 * Getter de l'attribut etreTermine
	 * @return
	 */
	public boolean etreTermine() {
		return etreTermine;
	}
	
	/**
	 * Getter de l'attribut nbEtats
	 * @return
	 */
	public int getNbEtats() {
		return nbEtats;
	}
	
	/**
	 * Methode permettant de copier une position
	 * @param p : la position a copier
	 * @return la position copie
	 */
	public static Position copierPosition(Position p) {
		return new Position(copierCase(p.getCase()), p.getDirection());
	}
	
	/**
	 * Methode permettant de copier une case
	 * @param c : la case a copier
	 * @return la case copie
	 */
	private static Case copierCase(Case c) {
		return new CaseVide(new Coordonnees(c.getCoord().x, c.getCoord().y));
	}

	public void changeParent() {
		afficherParent = !afficherParent;
	}
	
	/**
	 * Methode permettant de definir si l'on veut afficher les valuations
	 */
	public void changeValuation() {
		afficherValuation = !afficherValuation;
	}
	
	/**
	 * Getter de l'attribut afficherValuation
	 * @return
	 */
	public boolean getAfficherValuation() {
		return afficherValuation;
	}
	
	/**
	 * Getter de l'attribut afficherParent
	 * @return
	 */
	public boolean getAfficherParent() {
		return afficherParent;
	}
	
	public int getEtatSortieTrouvee() {
		return etatSortieTrouvee;
	}
	
	/**
	 * Getter de l'etat courant
	 * @return int
	 */
	public int getEtatCourant() {
		return etatCourant;
	}

	/**
	 * Setter de l'etat courant
	 * @param etatCourant : l'etat courant
	 */
	public void setEtatCourant(int etatCourant) {
		this.etatCourant = etatCourant;
	}
	
	/**
	 * Setter du temps de pause
	 * @param sleepTime : temps de pause
	 */
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}


	/**
	 * Methode permettant de demarrer l'algorithme
	 */
	public void demarrer() {
		pause = false;
	}
	
	/**
	 * Methode permettant de mettre en pause l'algorithme
	 */
	public void pause() {
		pause = true;
	}

	
	/**
	 * Getter de l'attribut pause
	 * @return boolean
	 */
	public boolean etreEnPause() {
		return pause;
	}
	
	/**
	 * Getter de l'attribut sleepTime
	 * @return int
	 */
	public int getSleepTime() {
		return sleepTime;
	}
}