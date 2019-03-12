package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public abstract class AlgorithmeCheminementGlobal extends AlgorithmeCheminement {

	/**
	 * Attributs representant la liste des cases ouvertes et fermees
	 */
	protected ArrayList<Noeud> ouverte, fermee;

	/**
	 * Attribut representant les cases courantes
	 */
	private ArrayList<Case> casesCourantes;
	
	/**
	 * Attributs representant le chemin final et la liste des cases fermees
	 */
	private ArrayList<Noeud> listeCloseList, cheminFinal;
	
	/**
	 * Attribut representant la liste des cases ouvertes
	 */
	private ArrayList<ArrayList<Noeud>> listeOpenList;

	/**
	 * Constructeur de AlgorithmeCheminementGlobal
	 * @param g : le gestionnaire
	 */
	protected AlgorithmeCheminementGlobal(Gestionnaire g) {
		super(g);
	}

	@Override
	public void initialiserAlgorithme() {
		super.initialiserAlgorithme();
		ouverte = new ArrayList<>();
		fermee = new ArrayList<>();
		
		casesCourantes = new ArrayList<>();
		listeCloseList = new ArrayList<>();
		listeOpenList = new ArrayList<>();
		cheminFinal = new ArrayList<>();
		
		Case entree = gestionnaire.getLaby().getEntree();
		Noeud depart = new Noeud(entree, null, 0, coutH(entree));
		ouverte.add(depart);
	}
	
	@Override
	public void executerAlgorithme() {
		etatCourant = nbEtats;

		if(!ouverte.isEmpty() && etatSortieTrouvee == -1) {

			nbEtats++;
			Collections.sort(ouverte);
			Noeud meilleurNoeud = ouverte.get(0); 

			casesCourantes.add(meilleurNoeud.getCase());
			listeCloseList.add(meilleurNoeud);

			ouverte.remove(0);
			fermee.add(meilleurNoeud);	

			if(meilleurNoeud.getCase().equals(gestionnaire.getLaby().getSortie())) {
				cheminFinal.add(meilleurNoeud);
				etatSortieTrouvee = etatCourant;
				listeOpenList.add(new ArrayList<Noeud>());

			}else {

				ArrayList<Noeud> openList = new ArrayList<>(); 
				for(Case voisin : voisins(meilleurNoeud)){

					Noeud n = new Noeud(voisin, meilleurNoeud, 0, 0);

					if(!fermee.contains(n)) {
						if(!ouverte.contains(n)) {
							n.setCoutG(meilleurNoeud.getCoutG()+1);
							n.setCoutH(coutH(voisin));
							ouverte.add(n);
							openList.add(copieNoeud(n));

						}else {

							if(meilleurNoeud.getCoutG()+1 < ouverte.get(ouverte.indexOf(n)).getCoutG()) {
								ouverte.get(ouverte.indexOf(n)).setCoutG(meilleurNoeud.getCoutG()+1);
								ouverte.get(ouverte.indexOf(n)).setParent(meilleurNoeud);
								openList.add(copieNoeud(n));
							}
						}
					}
				}
				listeOpenList.add(openList);
			}
		}else if(etatSortieTrouvee != -1 && !etreTermine) { 
			Noeud n = cheminFinal.get(cheminFinal.size()-1).getParent();

			if(n != null) {
				casesCourantes.add(n.getCase());
				cheminFinal.add(n);
				nbEtats++;
			}
			else {
				etreTermine = true;
			}

		}else {
			etreTermine = true;
		}
	}
	
	protected abstract int coutH(Case c);

	/**
	 * Methode permettant de connaitre la liste des cases voisines
	 * @param n : les cases a traitees
	 * @return la liste des voisins
	 */
	private ArrayList<Case> voisins(Noeud n) {
		ArrayList<Case> noeuds = new ArrayList<>();
		Case[][] tab = gestionnaire.getLaby().getCarte();

		Coordonnees coord = n.getCase().getCoord();

		for(int i=0; i<4; i++) {

			int x = coord.x + tabAdj[i][0],
					y = coord.y + tabAdj[i][1];

			if(tab[x][y].etreAccessible())
				noeuds.add(tab[x][y]);
		}

		return noeuds;
	}

	/**
	 * Methode permettant de copier un noeud
	 * @param n : le noeud a copier
	 * @return le noeud copie
	 */
	public Noeud copieNoeud(Noeud n) {
		return new Noeud(n.getCase(), n.getParent(), n.getCoutG(), n.getCoutH());
	}

	/**
	 * Getter de casesCourantes
	 * @return
	 */
	public ArrayList<Case> getCasesCourantes() {
		return casesCourantes;
	}

	/**
	 * Getter de listeCloseList
	 * @return
	 */
	public ArrayList<Noeud> getListeCloseList() {
		return listeCloseList;
	}

	/**
	 * Getter de listeOpenList
	 * @return
	 */
	public ArrayList<ArrayList<Noeud>> getListeOpenList() {
		return listeOpenList;
	}
	
	/**
	 * Getter de cheminFinal
	 * @return
	 */
	public ArrayList<Noeud> getCheminFinal() {
		return cheminFinal;
	}
}
