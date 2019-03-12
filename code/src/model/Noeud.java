package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class Noeud implements Comparable<Noeud> {

	/**
	 * Attribut representant une case
	 */
	private final Case c;
	
	/**
	 * Attribut representant le noeud parent 
	 */
	private Noeud parent;
	
	/**
	 * Attributs representant le cout G et le cout H 
	 */
	private int coutG, coutH;

	/**
	 * Constructeur de Noeud
	 * @param c : la case
	 * @param parent : le parent
	 * @param coutG : le cout G
	 * @param coutH : le cout H
	 */
	public Noeud(Case c, Noeud parent, int coutG, int coutH) {
		this.c = c;
		this.parent = parent;
		this.coutG = coutG;
		this.coutH = coutH;
	}

	/**
	 * Getter de l'attribut case
	 */
	public Case getCase() {
		return c;
	}

	/**
	 * Setter de l'attribut parent
	 * @param p
	 */
	public void setParent(Noeud p) {
		parent = p;
	}
	
	/**
	 * Getter de l'attribut parent 
	 * @return
	 */
	public Noeud getParent() {
		return parent;
	}

	/**
	 * Setter de l'attribut coutG
	 */
	public void setCoutG(int coutG) {
		this.coutG = coutG;
	}
	
	/**
	 * Getter de l'attribut coutG
	 * @return
	 */
	public int getCoutG() {
		return coutG;
	}
	
	/**
	 * Setter de l'attribut coutH
	 */
	public void setCoutH(int coutH) {
		this.coutH = coutH;
	}
	
	/**
	 * Getter de l'attribut coutH
	 * @return
	 */
	public int getCoutH() {
		return coutH;
	}
	
	/**
	 * Getter de coutF calcule a partir du coutG et du coutH
	 * @return
	 */
	public int getCoutF() {
		return coutG + coutH;
	}
	
	@Override
	public String toString() {
		return c.getCoord() + ", parent=" + ((parent == null)?null : parent.getCase().getCoord()) + ", coutG=" + coutG + ", coutH=" + coutH + "]";
	}

	@Override
	public int compareTo(Noeud n) {	
		if(getCoutF() == n.getCoutF()) {
			return n.getCoutG() - getCoutG();
		}
		return getCoutF() - n.getCoutF();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c == null) ? 0 : c.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Noeud other = (Noeud) obj;
		if (c == null) {
			if (other.c != null)
				return false;
		} else if (!c.equals(other.c))
			return false;
		return true;
	}

}
