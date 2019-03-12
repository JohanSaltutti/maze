
package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public abstract class Case {
	
	/**
	 * Attribut qui represente les coordonnees de la case
	 */
    private Coordonnees coord;
    
    /**
	 * Constructeur de Case
	 * @param c : les coordonnees de la case
	 */
    public Case(Coordonnees c) {
    	coord = c;
    }
    
    /**
     * Methode abstraite qui permet de savoir si une case est accessible
     * @return
     */
    public abstract boolean etreAccessible();

    /**
     * Getter de Coordonnees
     * @return : les coordonnees de la case
     */
    public Coordonnees getCoord() {
        return coord;
    }

    /**
     * Setter de Coordonnees (en utilisant l'objet)
     * @param coord : les nouvelles coordonnees
     */
    public void setCoord(Coordonnees coord) {
        this.coord = coord;
    }
    
    /**
     * Setter de Coordonnees (en utilisant des abscisses et ordonnees)
     * @param x : l'abscisse de la nouvelle case
     * @param y : l'ordonnee de la nouvelle case
     */
    public void setCoord(int x, int y) {
    	coord.x = x;
    	coord.y = y;
    }

    /**
     * Methode qui permet d'afficher les coordonnees de la case
     */
    @Override
    public String toString() {
    	return coord.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coord == null) ? 0 : coord.hashCode());
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
		Case other = (Case) obj;
		if (coord == null) {
			if (other.coord != null)
				return false;
		} else if (!coord.equals(other.coord))
			return false;
		return true;
	}
}
