package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class Position {

	/**
	 * Attribut representant une case
	 */
	private Case c;
	
	/**
	 * Attribut representant une direction
	 */
	private int direction;
	
	/**
	 * Attribut representant chaque direction
	 */
	public static final int NORD = 1, EST = 2, SUD = 3, OUEST = 4;
	
	/**
	 * Constructeur de Position
	 * @param c : la case actuelle
	 * @param direction : la direction du robot
	 */
	public Position(Case c, int direction) {
		this.c = c;
		this.direction = direction;
	}
	
	/**
	 * Getter de Case
	 * @return : la case actuelle
	 */
	public Case getCase() {
		return c;
	}
	
	/**
	 * Setter de Case
	 * @param c : la nouvelle case
	 */
	public void setCase(Case c) {
		this.c = c;
	}
	
	/**
	 * Getter de direction
	 * @return : la direction du robot
	 */
	public int getDirection() {
		return direction;
	}
	
	/**
	 * Setter de direction
	 * @param direc : la nouvelle direction
	 */
	public void setDirection(int direc) {
		if(direc>0 && direc <5)
			direction = direc;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c == null) ? 0 : c.hashCode());
		result = prime * result + direction;
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
		Position other = (Position) obj;
		if (c == null) {
			if (other.c != null)
				return false;
		} else if (!c.equals(other.c))
			return false;
		if (direction != other.direction)
			return false;
		return true;
	}

	/**
	 * Methode permettant d'afficher la direction
	 */
	public String toString(){
		return c.toString() + " " + direction;
	}
}
