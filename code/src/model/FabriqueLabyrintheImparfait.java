package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class FabriqueLabyrintheImparfait extends FabriqueLabyrinthe{

	/**
	 * Constructeur de FabriqueLabyrintheImparfait
	 * @param g : le gestionnaire
	 */
	public FabriqueLabyrintheImparfait(Gestionnaire g) {
		super(g);
	}
	
	/**
	 * Methode qui permet de creer un labyrinthe imparfait en utilisant l'algorithme du labyrinthe parfait en enlevant des mur de maniere aleatoire
	 */
	@Override
	public Labyrinthe fabriquerLabyrinthe() {
		
		int COLONNES = this.gestionnaire.getLaby().getColonnes();
		int LIGNES = this.gestionnaire.getLaby().getLignes();
		
		Labyrinthe l = new Labyrinthe(COLONNES ,LIGNES);
		
		
		ArrayList<Integer> murs = new ArrayList<Integer>();
		HashMap<Integer, HashSet<Integer>> hm = new HashMap<Integer, HashSet<Integer>>();
		int m = LIGNES;
		int n = COLONNES;
		int[][] tab = new int[n][m];
	
		// remplissage des bords
		for (int i = 0; i < m; i++) {
			tab[0][i] = -1;
			tab[n - 1][i] = -1;
		}

		for (int i = 0; i < n; i++) {
			tab[i][0] = -1;
			tab[i][m - 1] = -1;
		}

		int nbCases = 0;

		// partie initialisation
		for (int i = 1; i < n - 1; i++) {
			for (int j = 1; j < m - 1; j++) {
				tab[i][j] = -1;
				if (i % 2 == 1 && j % 2 == 1) {
					// si on est sur une case vide
					HashSet<Integer> s = new HashSet<Integer>();
					s.add(i * m + j);
					hm.put(nbCases, s);
					tab[i][j] = nbCases;
					nbCases++;

				} else if (i % 2 == 1 ^ j % 2 == 1) {
					// si on est sur un mur destructible
					murs.add(i * m + j);
				}
			}
		}

		// partie generation
		while (hm.get(0).size() != nbCases || murs.size() != 0) {
			int curr = (int) (murs.size() * Math.random());
			int currVal = murs.get(curr);
			int currI = currVal / m;
			int currJ = currVal % m;

			int min = -1, max = -1;

			if (tab[currI - 1][currJ] != -1) {
				//mur separation verticale
				int up = tab[currI - 1][currJ];
				int down = tab[currI + 1][currJ];

				if (down != -1) {
					// cas normal : on casse un mur entre deux cases vides
					if (up != down) {
						min = (up > down) ? down : up;
						max = (up > down) ? up : down;
					}
				} else {
					// si on essaie de casser un mur entre du vide et un autre
					// mur (cas quand on supprime un mur
					// de la derniere ligne d'un labyrinthe avec un nombre de
					// lignes pair)
					tab[currI][currJ] = up;
					hm.get(up).add(currI * m + currJ);
					nbCases++;
				}

			} else {
				//mur separation horizontale
				int left = tab[currI][currJ - 1];
				int right = tab[currI][currJ + 1];

				if (right != -1) {
					// cas normal : on casse un mur entre deux cases vides
					if (right != left) {
						min = (right > left) ? left : right;
						max = (right < left) ? left : right;
					}
				} else {
					// si on essaie de casser un mur entre du vide et un autre
					// mur (cas quand on supprime un mur
					// de la derniere colonne d'un labyrinthe avec un nombre de
					// colonnes pair)
					tab[currI][currJ] = left;
					hm.get(left).add(currI * m + currJ);
					nbCases++;
				}

			}

			if (min != max) {
				tab[currI][currJ] = min;

				HashSet<Integer> maxSet = hm.get(max), minSet = hm.get(min);

				minSet.add(currI * m + currJ);

				for (Integer i : maxSet) {
					tab[i / m][i % m] = min;
					minSet.add(i);
				}

				hm.remove(max);
				nbCases++;
			}

			murs.remove(curr);
		}

		//on casse un certain pourcentage de murs cassables
		int k = ((tab.length * tab[0].length) / 15);

		for(int cmp = 0 ; cmp < k ; cmp++){
			int i, j;
			do {
				i = (int) (Math.random() * (tab.length - 2) + 1);
				j = (int) (Math.random() * (tab[0].length - 2) + 1);
			} while (tab[i][j] != -1);

			tab[i][j] = 0;
		}

		//on cree le labyrinthe
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {

				switch(tab[i][j]) {
				case -1 :
					l.getCarte()[i][j] = new CaseMur(new Coordonnees(i, j));
					break;
				case 0:
					l.getCarte()[i][j] = new CaseVide(new Coordonnees(i, j));
					l.positionnerSortie(l.getCarte()[i][j]) ;
					break;
				}	
			}
		}

		return l;
	}
}