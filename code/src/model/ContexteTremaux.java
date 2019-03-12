package model;

import java.util.Stack;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class ContexteTremaux {

	/**
	 * Attribut representant si la case a deja ete parcourue
	 */
	public boolean dejaParcouru;
	
	/**
	 * Attribut representant les cases adjacentes
	 */
	public Stack<Case> casesAjdacentes;
	
	/**
	 * Attributs representant les cases courantes, precendentes et prochaines
	 */
	public Case curr, prec, proch;
	
	/**
	 * Attributs representant les intentions vers la case precedente et la prochaine
	 */
	public Position versCasePrec, versCaseProch;
	
	/**
	 * Constructeur de ContexteTremaux
	 * @param c : la case courante
	 * @param p : la case precedente
	 * @param v : l'intention de case precedente
	 * @param ca : les cases adjacentes
	 */
	public ContexteTremaux(Case c, Case p, Position v, Stack<Case> ca) {
		curr = c;
		prec = p;
		versCasePrec = v;
		casesAjdacentes = ca;
		dejaParcouru = false;
	}

	@Override
	public String toString() {
		return "case courante : " + curr + "\ncase précédente : " + prec + "\ncase suivante : " + proch;
	}
}