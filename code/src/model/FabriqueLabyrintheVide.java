package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class FabriqueLabyrintheVide extends FabriqueLabyrinthe{

	/**
	 * Constructeur de FabriqueLabyrintheVide
	 * @param g : le gestionnaire
	 */
	public FabriqueLabyrintheVide(Gestionnaire g) {
		super(g);
	}
	
	/**
	 * Methode qui permet de fabriquer un labyrinthe vide
	 */
	@Override
	public Labyrinthe fabriquerLabyrinthe() {
		
		
		int COLONNES = this.gestionnaire.getLaby().getColonnes();
		int LIGNES = this.gestionnaire.getLaby().getLignes();
		
		Labyrinthe l = new Labyrinthe(COLONNES ,LIGNES);
		l.positionnerEntree(this.gestionnaire.getLaby().getEntree());
		l.positionnerSortie(this.gestionnaire.getLaby().getSortie());
		
		Case[][] carte = l.getCarte();
		
		for(int i = 0 ; i < COLONNES ; i++){
			for(int j = 0 ; j < LIGNES ; j++){
				boolean etreMurExterieur = l.etreMurExterieur(i, j);
				boolean etreSortie = carte[i][j].equals(l.getSortie());
				boolean etreEntree = carte[i][j].equals(l.getEntree());
				if(!(etreMurExterieur || etreSortie || etreEntree)){
					l.viderCase(l.getCarte()[i][j]); 
				}
			}
		}
		
		return l;
	}
}