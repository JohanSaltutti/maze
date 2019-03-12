package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class Labyrinthe{

	/**
	 * Constantes representant le nombre de lignes et de colonnes du labyrinthe
	 */
	private int LIGNES = 10, COLONNES = 10;
	
	/**
	 * Constantes representant les conditions minimales et maximales de creation d'un labyrinthe
	 */
	public static final int MIN_LIGNES = 7, MIN_COLONNES = 7, MAX_LIGNES = 200, MAX_COLONNES = 200;
	
	/**
	 * Attributs representant les cases d'entree et de sortie
	 */
	private Case entree, sortie;
	
	/**
	 * Attribut representant toutes les cases du labyrinthe
	 */
	private Case[][] carte;
	
	/**
	 * Les couleurs correspondant aux differents elements du labyrinthe, utile pour la sauvegarde et l'ouverture d'un labyrinthe en image
	 */
	private static final int COULEUR_SORTIE = Color.GREEN.getRGB(), COULEUR_MUR = Color.BLACK.getRGB(), COULEUR_CASE_VIDE = Color.WHITE.getRGB(), COULEUR_ENTREE = Color.YELLOW.getRGB();
	
	/**
	 * Constructeur de Labyrinthe qui cree un labyrinthe par defaut 
	 */
	public Labyrinthe() {
		initialiser();
	}

	/**
	 * Constructeur de Labyrinthe qui cree un labyrinthe avec le nombre de colonnes et de lignes personnalise
	 * @param colonnes : le nombre de colonnes
	 * @param lignes : le nombre de lignes
	 */
	public Labyrinthe(int colonnes, int lignes) {
		COLONNES = colonnes;
		LIGNES = lignes;
		initialiser();
	}

	/**
	 * Methode qui met des murs sur les contours
	 */
	private void initialiser() {
		carte = new Case[COLONNES][LIGNES];

		for(int i = 0 ; i < COLONNES ; i++){
			for(int j = 0 ; j < LIGNES ; j++){
				if(etreMurExterieur(i, j)){
					carte[i][j] = new CaseMur(new Coordonnees(i, j));
				}
				else{
					carte[i][j] = new CaseVide(new Coordonnees(i, j));
				}
			}
		}
		entree = carte[1][1];
		sortie = carte[COLONNES-2][LIGNES-2];
	}

	/**
	 * Methode qui permet de vider une case
	 * @param c : la case a vider
	 */
	public void viderCase(Case c){
		if( ! c.etreAccessible()){
			int x = c.getCoord().x;
			int y = c.getCoord().y;

			Coordonnees coord = new Coordonnees(x, y);
			CaseVide nouvelleCase = new CaseVide(coord);

			carte[x][y] = nouvelleCase;
		}
	}

	/**
	 * Methode qui permet de positionner une case en case d'entree
	 * @param c : la case a changer
	 */
	public void positionnerEntree(Case c){
		if(c.etreAccessible()){
			entree = c;
		}
	}

	/**
	 * Methode qui permet de positionner une case en case de sortie
	 * @param c : la case a changer
	 */
	public void positionnerSortie(Case c){
		if(c.etreAccessible()){
			sortie = c;
		}
	}

	/**
	 * Methode qui permet de positionner la case d'entree en la premiere case vide
	 */
	public void positionnerEntreePremiereCase() {
		boolean set = false;
		for(int x = 1 ; x <= COLONNES-2 ; x++) {
			for(int y=1; y <= LIGNES-2 ; y++) {
				if(carte[x][y].etreAccessible()) {
					entree = new CaseVide(new Coordonnees(x, y));
					set = true;
					break;
				}
			}
			if(set)
				break;
		}
	}

	/**
	 * Methode qui permet de positionner la case de sortie en la derniere case vide
	 */
	public void positionnerSortieDerniereCase() {
		for(int x = 1 ; x <= COLONNES-2 ; x++) {
			for(int y=1; y <= LIGNES-2 ; y++) {
				if(carte[x][y].etreAccessible())
					sortie = new CaseVide(new Coordonnees(x, y));
			}
		}
	}

	/**
	 * Methode qui permet de savoir si une case est en dehors du labyrinthe
	 * @param c : la case a tester
	 * @return : true ou false
	 */
	public boolean etreMurExterieur(Case c){

		int x = c.getCoord().x;
		int y = c.getCoord().y;

		return etreMurExterieur(x, y);
	}

	/**
	 * Methode qui permet de savoir si une case (par rapport a ses coordonnees) est en dehors du labyrinthe
	 * @param x : l'abscisse
	 * @param y : l'ordonnee 
	 * @return : true ou false
	 */
	public boolean etreMurExterieur(int x, int y){
		return (x <= 0 || y <= 0 || y >= LIGNES - 1 || x >= COLONNES - 1);
	}

	/**
	 * Methode qui permet de remplir une case
	 * @param c : la case a remplir
	 */
	public void remplirCase(Case c) {
		if(!(c.equals(entree) || c.equals(sortie))) {
			int x = c.getCoord().x,
					y = c.getCoord().y;

			if(carte[x][y].etreAccessible())
				carte[x][y]=new CaseMur(new Coordonnees(x, y));
		}
	}

	/**
	 * Methode qui permet de savoir si un mouvement est possible
	 * @param pos : l'orientation a tester
	 * @return : true ou false
	 */
	public boolean etreMouvementValide(Position pos) {
		int x = pos.getCase().getCoord().x;
		int y = pos.getCase().getCoord().y;

		switch (pos.getDirection()) {

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

		return carte[x][y].etreAccessible();
	}

	/**
	 * Methode qui permet d'afficher une representation du labyrinthe
	 */
	@Override
	public String toString(){
		String res = "";

		for(int i = 0 ; i < LIGNES ; i++){  
			for(int j = 0 ; j < COLONNES ; j++){
				res += (carte[j][i].etreAccessible())? "." : "#" ;
			}
			res += "\n";
		}
		return res;
	}

	/**
	 * Methode qui permet de modifier la taille du labyrinthe
	 * @param lignes : le nombre de lignes
	 * @param colonnes : le nombre de colonnes
	 */
	public void modifierTaille(int lignes, int colonnes) {

		Labyrinthe newLab = new Labyrinthe(colonnes, lignes);
		newLab.initialiser();


		int ligneParcours = ((lignes>=LIGNES)?LIGNES:lignes),
				colonnesParcours = ((colonnes>=COLONNES)?COLONNES:colonnes);


		for(int x = 1; x <= colonnesParcours -2 ; x++) {
			for(int y = 1; y <= ligneParcours - 2 ; y++) {
				if(carte[x][y].etreAccessible())
					newLab.carte[x][y] = new CaseVide(new Coordonnees(x, y));
				else
					newLab.carte[x][y] = new CaseMur(new Coordonnees(x, y));
			}
		}

		carte = newLab.carte;
		LIGNES = lignes;
		COLONNES = colonnes;
		positionnerEntreePremiereCase();
		positionnerSortieDerniereCase();

	}

	/**
	 * Methode permettant de sauvegarder le labyrinthe dans un fichier 
	 * @param file : le fichier de sauvegarde
	 * @throws IOException : erreur d'ouverture ou de sauvegarde
	 */
	public void sauvegarder(File file) throws IOException {
		FileWriter f = new FileWriter(file);
		
		for(int y = 0 ; y < LIGNES ; y++) {
			for(int x = 0 ; x < COLONNES ; x++) {
				if(carte[x][y] instanceof CaseMur) {
					f.write('#');
				}
				else {
					if(!(carte[x][y].equals(entree)) && !(carte[x][y].equals(sortie))) {
						f.write('!');
					}
					if(carte[x][y].equals(entree)) {
						f.write('E');
					}
					if(carte[x][y].equals(sortie)) {
						f.write('S');
					}
				}
			}
			f.write("\n");
		}
		f.close();

	}

	/**
	 * Methode permettant d'ouvrir le fichier de sauvegarde du labyrinthe
	 * @param file : le fichier a ouvrir
	 * @throws IOException : erreur d'ouverture ou de sauvegarde
	 * @throws LectureFichierException : exception generee selon l'erreur 
	 */
	public void ouvrir(File file) throws IOException, LectureFichierException {
		//Backup si erreur dans l'ouverture du fichier
		Case[][] carteBackup = carte;
		Coordonnees coordEntre = new Coordonnees(entree.getCoord().x, entree.getCoord().y);
		Coordonnees coordSortie = new Coordonnees(sortie.getCoord().x, sortie.getCoord().y);

		boolean etreFichier = file.getPath().toLowerCase().endsWith(".maze");
		boolean etreImage = file.getPath().toLowerCase().endsWith(".png");
		
		int ancienneLigne = LIGNES,	ancienneColonne = COLONNES,	nbEntree = 0, nbSortie = 0,	nbLigne = 0, nbColonne = 0;
		
		ArrayList<Character> contenuFich = new ArrayList<Character>();
		BufferedImage image = null;

		if(etreFichier) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String ligne;
			boolean setColonne = false;
			
			while ((ligne = br.readLine()) != null) {
				if(!setColonne) {
					nbColonne = ligne.length();
					setColonne = true;
				}
					
				if(ligne.length() != nbColonne) {
					br.close();
					throw new LectureFichierException("Erreur : Il faut des lignes de meme longueur");
				}else {
					for(int position = 0 ; position < ligne.length() ; position++) {
						char s = ligne.charAt(position);
						contenuFich.add(s);
					}
					nbLigne++;
				}
			}
			br.close();
			
		}else if(etreImage) {
			image = ImageIO.read(file);
			nbColonne = image.getWidth();
			nbLigne = image.getHeight();
		}

		if(nbColonne < MIN_COLONNES || nbColonne > MAX_COLONNES)
			throw new LectureFichierException("Erreur : Verifiez votre nombre de colonnes (MIN : " + MIN_COLONNES + " et MAX : " + MAX_COLONNES + ")");

		if(nbLigne < MIN_LIGNES || nbLigne > MAX_LIGNES) {
			throw new LectureFichierException("Erreur : Verifiez votre nombre de lignes (MIN : " + MIN_LIGNES + " et MAX : " + MAX_LIGNES + ")");
		}
	
		carte = new Case[nbColonne][nbLigne];
		
		int nbChar = 0;
		int value = 0;
		for(int y = 0 ; y < nbLigne ; y++) {
			for(int x = 0 ; x < nbColonne ; x++) {
			
				if(etreFichier)
					value = (int)(contenuFich.get(nbChar++));
				else if(etreImage)
					value = new Color(image.getRGB(x, y)).getRGB();
				
				if (value == 35 || value == COULEUR_MUR) {
					carte[x][y] = new CaseMur(new Coordonnees(x, y));
				} else if (value == 33 || value == COULEUR_CASE_VIDE) {
					carte[x][y] = new CaseVide(new Coordonnees(x, y));
				} else if (value == 69 || value == COULEUR_ENTREE) {
					if(nbEntree == 0) {
						carte[x][y] = new CaseVide(new Coordonnees(x, y));
						positionnerEntree(carte[x][y]);
						nbEntree++;
					}else {
						carte = carteBackup;
						positionnerEntree(new CaseVide(coordEntre));
						positionnerSortie(new CaseVide(coordSortie));
						throw new LectureFichierException("Erreur : Il y a plusieurs entrees dans le labyrinthe");
					}
				} else if (value == 83 || value == COULEUR_SORTIE) {
					if(nbSortie == 0) {
						carte[x][y] = new CaseVide(new Coordonnees(x, y));
						positionnerSortie(carte[x][y]);
						nbSortie++;
					}else {
						carte = carteBackup;
						positionnerEntree(new CaseVide(coordEntre));
						positionnerSortie(new CaseVide(coordSortie));
						throw new LectureFichierException("Erreur : Il y a plusieurs sorties dans le labyrinthe");
					}
				} else {
					carte = carteBackup;
					positionnerEntree(new CaseVide(coordEntre));
					positionnerSortie(new CaseVide(coordSortie));
					if(etreFichier)
						throw new LectureFichierException("Erreur : Caractere inconnu. \nSeuls '#', '!', 'E', 'S' sont acceptes.");	
					else if(etreImage)
						throw new LectureFichierException("Erreur : Couleur inconnue. \nVerifiez vos couleurs. ");
				}	
			}
		}
		
		if(nbEntree == 0) {
			carte = carteBackup;
			positionnerEntree(new CaseVide(coordEntre));
			positionnerSortie(new CaseVide(coordSortie));
			throw new LectureFichierException("Erreur : Il n'y a pas d'entree dans le labyrinthe");
		}

		if(nbSortie == 0) {
			carte = carteBackup;
			positionnerEntree(new CaseVide(coordEntre));
			positionnerSortie(new CaseVide(coordSortie));
			throw new LectureFichierException("Erreur : Il n'y a pas de sortie dans le labyrinthe");
		}

		LIGNES = nbLigne;
		COLONNES = nbColonne;

		//Verifie s'l y a des bordures
		for(int i = 0 ; i < nbColonne ; i++){
			for(int j = 0 ; j < nbLigne ; j++){		
				if(etreMurExterieur(i, j) && carte[i][j].etreAccessible()){ 
					carte = carteBackup;
					LIGNES = ancienneLigne;
					COLONNES = ancienneColonne;
					positionnerEntree(new CaseVide(coordEntre));
					positionnerSortie(new CaseVide(coordSortie));
					throw new LectureFichierException("Erreur : Le labyrinthe doit etre entoure de murs");
				}
			}
		}
	}

	/**
	 * Methode permettant de deplacer le labyrinthe s'il y a de nouvelles cases
	 * @param direction : la direction
	 */
	public void deplacerLabyrinthe(int direction) {

		int x, y;

		switch (direction) {
		case Position.NORD:

			for(x = 1; x <= COLONNES-2; x++) {
				for(y = 2 ; y <= LIGNES-2; y++) {
					carte[x][y-1] = (carte[x][y].etreAccessible())? new CaseVide(new Coordonnees(x, y-1)) : new CaseMur(new Coordonnees(x, y-1));
				}
				carte[x][LIGNES-2] = new CaseVide(new Coordonnees(x, LIGNES-2));
			}
			break;

		case Position.SUD:

			for(x = 1; x <= COLONNES-2; x++) {
				for(y = LIGNES-3 ; y >= 1; y--) {
					carte[x][y+1] = (carte[x][y].etreAccessible())? new CaseVide(new Coordonnees(x, y+1)) : new CaseMur(new Coordonnees(x, y+1));
				}
				carte[x][1] = new CaseVide(new Coordonnees(x, 1));
			}
			break;

		case Position.OUEST:
			for(y = 1; y <= LIGNES-2; y++) {
				for(x = 2 ; x <= COLONNES-2 ; x++) {
					carte[x-1][y] = (carte[x][y].etreAccessible())? new CaseVide(new Coordonnees(x-1, y)) : new CaseMur(new Coordonnees(x-1, y));
				}
				carte[COLONNES-2][y] = new CaseVide(new Coordonnees(COLONNES-2, y));
			}
			break;

		case Position.EST:
			for(y = 1 ; y <= LIGNES-2; y++) {
				for(x = COLONNES-3; x >= 1; x--) {
					carte[x+1][y] = (carte[x][y].etreAccessible())? new CaseVide(new Coordonnees(x+1, y)) : new CaseMur(new Coordonnees(x+1, y));
				}
				carte[1][y] = new CaseVide(new Coordonnees(1, y));
			}
			break;
		}

		positionnerEntreePremiereCase();
		positionnerSortieDerniereCase();

	}
	
	/**
	 * Methode permettant d'exporter un labyrinthe 
	 * dans un fichier image 
	 * @param name : Le nom du fichier
	 * @throws IOException : erreur de sauvegarde
	 */
	public void exporterImage(String name) throws IOException{
		BufferedImage bi = new BufferedImage(COLONNES, LIGNES, BufferedImage.TYPE_INT_RGB);
		
		for(int y = 0 ; y < LIGNES ; y++) {
			for(int x = 0 ; x < COLONNES ; x++) {
				if(!carte[x][y].etreAccessible()){
					//mur
					bi.setRGB(x, y, COULEUR_MUR);
				}else{
					if(!(carte[x][y].equals(entree)) && !(carte[x][y].equals(sortie))) {
						//case vide
						bi.setRGB(x, y, COULEUR_CASE_VIDE);
					}else {
						if(carte[x][y].equals(entree)) {
							//entree
							bi.setRGB(x, y, COULEUR_ENTREE);
						}
						else{
							//sortie
							bi.setRGB(x, y, COULEUR_SORTIE);
						}
					}
					
				}
			}
		}

		ImageIO.write(bi, "png", new File(name));
	}
	
	/**
	 * Getter de lignes
	 * @return : le nombre de lignes
	 */
	public int getLignes() {
		return LIGNES;
	}

	/**
	 * Getter de colonnes
	 * @return : le nombre de colonnes
	 */
	public int getColonnes() {
		return COLONNES;
	}

	/**
	 * Getter de carte
	 * @return : toutes les cases du labyrinthe
	 */
	public Case[][] getCarte() {
		return carte;
	}

	/**
	 * Getter d'entree
	 * @return : la case de l'entree
	 */
	public Case getEntree() {
		return entree;
	}

	/**
	 * Getter de sortie
	 * @return : la case de la sortie
	 */
	public Case getSortie() {
		return sortie;
	}
}
