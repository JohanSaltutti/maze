package model;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class LectureFichierException extends Exception {
	
	/**
	 * Constructeur de LectureFichierException qui permet de creer des exceptions personnalisees
	 * @param error : le message d'erreur
	 */
	public LectureFichierException(String error) {
		super(error);
	}
}