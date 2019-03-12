package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class FabriqueLabyrinthePlein  extends FabriqueLabyrinthe{

	/**
	 * Constructeur de FabriqueLabyrinthePlein
	 * @param g : le gestionnaire
	 */
	public FabriqueLabyrinthePlein(Gestionnaire g) {
		super(g);
	}
	
	/**
	 * Methode qui permet de creer un labyrinthe plein
	 */
	@Override
	public Labyrinthe fabriquerLabyrinthe() {
		
		
		int COLONNES = this.gestionnaire.getLaby().getColonnes();
		int LIGNES = this.gestionnaire.getLaby().getLignes();
		
		
		Labyrinthe l = new Labyrinthe(COLONNES ,LIGNES);
		l.positionnerEntree(this.gestionnaire.getLaby().getEntree());
		l.positionnerSortie(this.gestionnaire.getLaby().getSortie());
		
		for(int i = 0 ; i < COLONNES ; i++){
			for(int j = 0 ; j < LIGNES ; j++){
				if(!(l.getCarte()[i][j].equals(l.getEntree()) || l.getCarte()[i][j].equals(l.getSortie()))){
					l.remplirCase(l.getCarte()[i][j]); 
				}
			}
		}
		
		return l;
	}

}