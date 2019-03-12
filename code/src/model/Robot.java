package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class Robot {

	/**
	 * Attribut representant la position du robot
	 */
	private Position position; 
	
	/**
	 * Constructeur de Robot
	 */
	public Robot() {
		position = new Position(null, Position.EST);
	}

	/**
	 * Methode permettant au robot d'avancer
	 */
	public void avancer() {
		Case caseCourante = position.getCase();
		int x = caseCourante.getCoord().x;
		int y = caseCourante.getCoord().y;
		
		switch (position.getDirection()) {
		case Position.NORD:
			y--;
			break;
		case Position.EST:
			x++;
			break;
		case Position.SUD:
			y++;
			break;
		case Position.OUEST:
			x--;
			break;
		}
		caseCourante.setCoord(x, y);
	}

	/**
	 * Methode permettant de changer de position
	 * @param p : la position a changee
	 */
	public void changerPosition(Position p) {
		position = p;
	}

	/**
	 * Methode permettant au robot de faire une rotation a 90° a droite
	 */
	public void tournerADroite() {
		int orientation = position.getDirection();
		if (orientation == Position.OUEST)
			position.setDirection(Position.NORD);
		else
			position.setDirection(++orientation);
	}

	/**
	 * Methode permettant au robot de faire une rotation a 90° a gauche
	 */
	public void tournerAGauche() {
		int orientation = position.getDirection();
		if (orientation == Position.NORD)
			position.setDirection(Position.OUEST);
		else
			position.setDirection(--orientation);
	}

	/**
	 * Methode qui verifie si le robot est sur une case
	 * @param c : la case a verifier
	 * @return : true ou false
	 */
	public boolean etreSurCase(Case c) {
		return position.getCase().equals(c);
	}
	
	/**
	 * Getter de position
	 * @return : la position du robot
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Methode qui permet d'afficher la direction du robot
	 */
	public String toString() {
		String direction = "";
		switch (position.getDirection()) {
		case Position.NORD:
			direction = "NORD";
			break;
		case Position.EST:
			direction = "EST";
			break;
		case Position.SUD:
			direction = "SUD";
			break;
		case Position.OUEST:
			direction = "OUEST";
			break;
		}
		return "[ " + position.getCase() + " : " + direction + " ]";
	}

}
