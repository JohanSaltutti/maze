package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class FabriqueLabyrintheIlot extends FabriqueLabyrinthe{

	/**
	 * Constructeur de FabriqueLabyrintheIlot
	 * @param g : le gestionnaire
	 */
	public FabriqueLabyrintheIlot(Gestionnaire g) {
		super(g);
	}
	
	/**
	 * Methode qui permet de creer un labyrinthe avec un ilot au centre
	 */
	@Override
	public Labyrinthe fabriquerLabyrinthe() {
		
		int COLONNES = this.gestionnaire.getLaby().getColonnes();
		int LIGNES = this.gestionnaire.getLaby().getLignes();
		
		Labyrinthe l = new Labyrinthe(COLONNES ,LIGNES);
		Case[][] carte = l.getCarte();
		
		for(int i = 1 ; i <= COLONNES-2 ; i++){
			for(int j = 1 ; j <= 2 ; j++){
				carte[i][j] = new CaseVide(new Coordonnees(i, j));
			}
		}

		for(int i = 1 ; i <= 2 ; i++){
			for(int j = 1 ; j <= LIGNES-2 ; j++){
				carte[i][j] = new CaseVide(new Coordonnees(i, j));
			}
		}

		for(int i = 1 ; i <= COLONNES-2 ; i++){
			for(int j = LIGNES-3 ; j <= LIGNES-2 ; j++){
				carte[i][j] = new CaseVide(new Coordonnees(i, j));
			}
		}

		for(int i = COLONNES-3 ; i <= COLONNES-2 ; i++){
			for(int j = 1 ; j <= LIGNES-2 ; j++){
				carte[i][j] = new CaseVide(new Coordonnees(i, j));
			}
		}

		for(int i = 3 ; i < COLONNES-3 ; i++){
			for(int j = 3 ; j < LIGNES-3 ; j++){
				carte[i][j] = new CaseMur(new Coordonnees(i, j));
			}
		}
		
		return l;
	}
}