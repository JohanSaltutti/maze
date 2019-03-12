package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class Tremaux extends AlgorithmeCheminementLocal {

	/**
	 * Attribut representant la pile des appels de l'algorithme
	 */
	private Stack<ContexteTremaux> pileAppels;
	
	/**
	 * Attribut representant les cailloux utilises dans l'algorithme
	 */
	private HashMap<Position, Integer> cailloux;

	/**
	 * Attribut representant la liste des cailloux
	 */
	private ArrayList<HashMap<Position, Integer>> listeCailloux;
	
	/**
	 * Attribut representant le chemin final
	 */
	private ArrayList<Case> cheminFinal;

	/**
	 * Constructeur de Tremaux
	 * @param g : le gestionnaire
	 */
	public Tremaux(Gestionnaire g) {
		super(g);
	}

	@Override
	public void initialiserAlgorithme() {
		super.initialiserAlgorithme();
		cailloux = new HashMap<Position, Integer>();
		cheminFinal = new ArrayList<Case>();
		listeCailloux = new ArrayList<HashMap<Position, Integer>>();
		pileAppels = new Stack<ContexteTremaux>();
		pileAppels.add(new ContexteTremaux(gestionnaire.getLaby().getEntree(), null, null, casesAdjacentesTraitables(gestionnaire.getLaby().getEntree(), null)));
	}
	
	@Override
	public void executerAlgorithme() {
		etatCourant = nbEtats;
		
		if(!pileAppels.isEmpty()) {
			//si on n'a pas trouve la sortie ou explore toutes les solutions
			
			nbEtats++;
			ContexteTremaux contexteCourant = pileAppels.peek();
			Case curr = contexteCourant.curr;
			Position pRob;
			
			if(curr.equals(gestionnaire.getLaby().getSortie()) || etatSortieTrouvee != -1){
				//si on est sur la sortie ou sur le chemin du retour
				
				cheminFinal.add(curr);
				pileAppels.pop();
				listeCailloux.add(copierHashMap(cailloux));
				
				if(etatSortieTrouvee != -1) {
					pRob = positionAvance(contexteCourant.proch, curr);
				}
				else {
					pRob = positionAvance(contexteCourant.prec, curr);
				}				
				
				etatSortieTrouvee = (etatSortieTrouvee == -1)? etatCourant : etatSortieTrouvee;
			}
			else {

				Stack<Case> adj = contexteCourant.casesAjdacentes;
				Position versPrec = contexteCourant.versCasePrec;
				Position versProch = contexteCourant.versCaseProch;

				if(contexteCourant.dejaParcouru) {
					//on a backtrack et on s'est retrouve ici
					
					pRob = positionAvance(contexteCourant.proch, curr);

					if(adj.isEmpty()) {
						//s'il n'y a plus d'adjacentes 
						
						if(versProch != null) {
							//on est sur une intersection : on met à jour les cailloux
							
							cailloux.put(versProch, 2);
							listeCailloux.add(copierHashMap(cailloux));
							cailloux.put(versPrec, 2);
						}
						else {
							listeCailloux.add(copierHashMap(cailloux));
						}

						//on peut plus avancer : on backtrack
						pileAppels.pop();
					}
					else {
						//on est forcement sur une intersection : on met a jour le caillou d'ou l'on backtrack
						
						cailloux.put(versProch, 2);
						listeCailloux.add(copierHashMap(cailloux));

						Case proch;
						Position posProch;

						do {
							proch = adj.pop();
							posProch = position(curr, proch);
						}
						while(cailloux.containsKey(posProch) && !adj.isEmpty());

						if(cailloux.get(posProch) != null) {
							// plus de chemin : on backtrack
							
							cailloux.put(versPrec, 2);
							pileAppels.pop();
						}
						else {
							//on empile et on avance sur la prochaine case
							
							cailloux.put(posProch, 1);
							pileAppels.add(new ContexteTremaux(proch, curr, position(proch, curr), casesAdjacentesTraitables(proch, curr)));
							
							contexteCourant.versCaseProch = posProch;
							contexteCourant.proch = proch;
						}
					}
				}
				else {
					//c'est la premiere fois qu'on arrive sur cette case (ou on repasse sur une case deja parcourue mais en avancant)
					
					int casesParcourables = adj.size();
					Case proch;

					if(casesParcourables > 1) {
						//on est sur une intersection : on met un caillou par la ou on est arrive

						if(versPrec != null) {
							cailloux.put(versPrec, 1);
							pRob = positionAvance(contexteCourant.prec, curr);
						}
						else {
							pRob = AlgorithmeCheminementLocal.entreeRobot;
						}
						
						listeCailloux.add(copierHashMap(cailloux));
						
						Position posProch;

						do {
							proch = adj.pop();
							posProch = position(curr, proch);
						}
						while(cailloux.containsKey(posProch) && !adj.isEmpty());

						if(cailloux.get(posProch) != null) {
							//plus de chemins dans ce sens : on depile et on fait demi tour
							
							pileAppels.pop();
							cailloux.put(versPrec, 2);
						}
						else {
							//on empile et on avance sur la prochaine case

							pileAppels.add(new ContexteTremaux(proch, curr, position(proch, curr), casesAdjacentesTraitables(proch, curr)));
							cailloux.put(posProch, 1);

							contexteCourant.dejaParcouru = true;
							contexteCourant.versCaseProch = posProch;
							contexteCourant.proch = proch;
						}
					}
					else if(casesParcourables == 1) {
						//on est dans un couloir : on avance et on empile
						
						proch = adj.pop();
						
						if(versPrec != null) {
							pRob = positionAvance(contexteCourant.prec, curr);
						}
						else {
							pRob = new Position(gestionnaire.getLaby().getEntree(), Position.EST);
						}
						
						listeCailloux.add(copierHashMap(cailloux));
						pileAppels.add(new ContexteTremaux(proch, curr, position(proch, curr), casesAdjacentesTraitables(proch, curr)));
						
						contexteCourant.dejaParcouru = true;
						contexteCourant.proch = proch;
					}
					else {
						//on est dans un cul-de-sac : on depile et on fait demi tour
						
						listeCailloux.add(copierHashMap(cailloux));
						pRob = positionAvance(contexteCourant.prec, curr);
						pileAppels.pop();
					}
				}
			}

			positionsRobot.add(pRob);
		}
		else {
			etreTermine = true;
		}
	}

	/**
	 * Methode permettant d'avoir les cases adjacentes utilisables
	 * @param cu : la case a aller
	 * @param p : la case initiale
	 * @return
	 */
	private Stack<Case> casesAdjacentesTraitables(Case cu, Case p){

		Stack<Case> res = new Stack<Case>();

		Case[][] carte = gestionnaire.getLaby().getCarte();
		int currX = cu.getCoord().x, currY = cu.getCoord().y;

		for(int[] t : tabAdj) {
			Case c = carte[currX + t[0]][currY + t[1]];

			//si c'est pas un mur ET qu'on vient pas de la 
			if(c.etreAccessible() && (!c.equals(p) || cu.equals(gestionnaire.getLaby().getEntree()))) {
				res.add(c);
			}
		}
		
		Collections.shuffle(res);
		return res;
	}
	
	/**
	 * Retourne la position sur la case curr sur laquelle il faut etre pour, en avancant, se retrouver sur la case dest
	 * @param curr case de reference
	 * @param dest case d'arrivee
	 * @return la position sur la case curr sur laquelle il faut etre pour, en avancant, se retrouver sur la case dest
	 */
	private Position position(Case curr, Case dest) {

		Case[][] carte = gestionnaire.getLaby().getCarte();
		int currX = curr.getCoord().x, currY = curr.getCoord().y;
		int position = -1;

		for(int i = 0 ; i < 4 ; i++) {
			Case c = carte[currX + tabAdj[i][0]][currY + tabAdj[i][1]];
			if(c.equals(dest)) {
				position = i + 1;
				break;
			}
		}

		return (position == -1)? null : new Position(curr, position) ;
	}

	/**
	 * Methode retournant la position sur laquelle on se retrouve lorsqu'on veut aller tout droit
	 * @param curr : la case courante
	 * @param dest : la case de destination
	 * @return
	 */
	private Position positionAvance(Case curr, Case dest) {

		Position p = position(curr, dest);

		return (p == null)? null : new Position(dest, p.getDirection()) ;
	}
	
	/**
	 * Methode permettant de copier la HashMap
	 * @param hm : la HashMap a copier
	 * @return la HashMap copiee
	 */
	public HashMap<Position, Integer> copierHashMap(HashMap<Position, Integer> hm){
		HashMap<Position, Integer> res = new HashMap<Position, Integer>();

		Set<Position> set = hm.keySet();
		for (Position p : set) {
			res.put(p, hm.get(p));
		}

		return res;
	}

	/**
	 * Getter de l'attribut listeCailloux
	 */
	public ArrayList<HashMap<Position, Integer>> getListeCailloux() {
		return listeCailloux;
	}

	/**
	 * Getter de l'attribut cheminFinal
	 */
	public ArrayList<Case> getCheminFinal() {
		return cheminFinal;
	}
}
