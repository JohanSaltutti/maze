package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class Coordonnees{

	/**
	 * Attributs representant les abscisses et ordonnees
	 */
	public int x, y;

	/**
	 * Constructeur de Coordonnees
	 * @param x : l'abscisse de la case
	 * @param y : l'odonnee de la case
	 */
	public Coordonnees(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Methode qui permet d'afficher les coordonnees
	 */
	@Override
	public String toString() {
		return "( " + x + " ; "+ y + " )";
	}

	/**
     * Methode qui permet de comparer deux cases
     * @param c : la case a tester
     * @return : true ou false
     */
	public boolean equals(Coordonnees c){
		return (c.x == x) && (c.y == y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Coordonnees other = (Coordonnees) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}