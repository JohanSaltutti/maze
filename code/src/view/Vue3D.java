package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import model.Coordonnees;
import model.Gestionnaire;
import model.Labyrinthe;
import model.Position;

/**
 * 
 * @author BERARD Valentin | OBERHAUSSER Lucas | SALTUTTI Johan | SOMMER Matthieu
 *
 */
public class Vue3D extends Vue{
	
	/**
	 * Constantes representant la taille de l'ecran
	 */
	private int SCREEN_HEIGHT, SCREEN_WIDTH;
	
	/**
	 * Constante representant la distance de rendue
	 */
	public static final int RENDERING_DISTANCE = 20;

	/**
	 * Constante representant la distance d'affichage
	 */
	private double SCREEN_DISTANCE;
	
	/**
	 * Constante representant la couleur du labyrinthe
	 */
	private final Color MAZE_COLOR;

	/**
	 * Attribut representant les filtres des cases
	 */
	public ArrayList<Coordonnees> filtreCases;
	
	/**
	 * Image de fond (skybox de la scène)
	 */
	private BufferedImage background;
	
	/**
	 * Constructeur de Vue3D
	 * @param g : le gestionnaire
	 */
	public Vue3D(Gestionnaire g) {
		super(g);
		
		SCREEN_HEIGHT = 1;
		SCREEN_WIDTH = 1;
		SCREEN_DISTANCE = 0.5;
		MAZE_COLOR = new Color(0xBFF80);
		
		filtreCases = new ArrayList<Coordonnees>();

		for(int j = -RENDERING_DISTANCE ; j < 0 ; j++) {
			for(int i = -RENDERING_DISTANCE ; i <= RENDERING_DISTANCE ; i++) {
				filtreCases.add(new Coordonnees(i, j));
			}
		}
		
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/3d/nuitEtoilee.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void resize() {
		Container parent = getParent();
		int parentHeight = parent.getHeight();
		int parentWidth = parent.getWidth();
		
		double heightRatio = (parentHeight - 2.0 * MARGIN_VERT) / SCREEN_HEIGHT;
		double widthRatio = (parentWidth - 2.0 * MARGIN_HORIZ) / SCREEN_WIDTH;
		
		TAILLE_CASE = (heightRatio > widthRatio)? (int)widthRatio : (int)heightRatio;
		
		WIDTH = TAILLE_CASE * SCREEN_WIDTH;
		HEIGHT = TAILLE_CASE * SCREEN_HEIGHT;
		
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		revalidate();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gg = (Graphics2D)g; 
//		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		resize();

		//initialisation
		Position posRobot = gestionnaire.getRobot().getPosition();
		Coordonnees coordRobot = posRobot.getCase().getCoord();
		Labyrinthe l = gestionnaire.getLaby();
		int col = l.getColonnes(), row = l.getLignes();

		//skybox
		gg.drawImage(background, 0, 0, getWidth(), getHeight(),  null);
		
		//capture des faces a afficher
		ArrayList<Face> facesAffichees = new ArrayList<Face>();

		for(Coordonnees c : filtreCases) {

			Coordonnees relativeCoord = new Coordonnees(c.x, c.y);

			switch(posRobot.getDirection()) {

			case Position.EST : {
				relativeCoord.x = - c.y;
				relativeCoord.y = c.x;
				break;
			}

			case Position.SUD : {
				relativeCoord.x = - c.x;
				relativeCoord.y = - c.y;
				break;
			}

			case Position.OUEST : {
				relativeCoord.x = c.y;
				relativeCoord.y = - c.x;
				break;
			}
			}

			Coordonnees absoluteCoord = new Coordonnees(coordRobot.x + relativeCoord.x, coordRobot.y + relativeCoord.y);

			//si la case du filtre est dans le labyrinthe
			if(absoluteCoord.x >= 0 && absoluteCoord.y >= 0 && absoluteCoord.x < col && absoluteCoord.y < row) {
				
				//on retranslate la case au nord
				relativeCoord.x = c.x;
				relativeCoord.y = c.y;				
				
				if(l.getCarte()[absoluteCoord.x][absoluteCoord.y].etreAccessible()) {
					//si la case est vide, on ajoute le sol et le plafond. 
					//on affiche forcément le plafond/sol : pas besoin de faire le test avec la normale
//					Face cieling = new Face(new Point(relativeCoord.x, 0, - relativeCoord.y), new Point(relativeCoord.x + 1, 0, - relativeCoord.y), new Point(relativeCoord.x + 1, 0, - relativeCoord.y - 1), new Point(relativeCoord.x, 0, - relativeCoord.y - 1));
					Face floor = new Face(new Point(relativeCoord.x, -1, - relativeCoord.y), new Point(relativeCoord.x + 1, -1, - relativeCoord.y), new Point(relativeCoord.x + 1, -1, - relativeCoord.y - 1), new Point(relativeCoord.x, -1, - relativeCoord.y - 1));
					
					if(absoluteCoord.equals(l.getSortie().getCoord())) {
						floor.setCouleur(Color.GREEN);
					}
					else if(absoluteCoord.equals(l.getEntree().getCoord())){
						floor.setCouleur(Color.YELLOW);
					}
					else {
						floor.setCouleur(getShade(MAZE_COLOR, floor));
					}
					
//					facesAffichees.add(cieling);
					facesAffichees.add(floor);
				}
				else {
					//sinon on ajoute juste les 4 murs (+ filtrage selon la normale/produit scalaire)

					if(relativeCoord.y < -0.5) {
						Face south = new Face(new Point(relativeCoord.x + 1, 0, - relativeCoord.y - 1), new Point(relativeCoord.x, 0, - relativeCoord.y - 1), new Point(relativeCoord.x, -1, - relativeCoord.y - 1), new Point(relativeCoord.x + 1, -1, - relativeCoord.y - 1));
						south.setCouleur(getShade(MAZE_COLOR, south));
						facesAffichees.add(south);
					}
					else if(relativeCoord.y > 0.5) {						
						Face north = new Face(new Point(relativeCoord.x, 0, - relativeCoord.y), new Point(relativeCoord.x + 1, 0, - relativeCoord.y), new Point(relativeCoord.x + 1, -1, - relativeCoord.y), new Point(relativeCoord.x, -1, - relativeCoord.y));
						north.setCouleur(getShade(MAZE_COLOR, north));
						facesAffichees.add(north);
					}

					if(relativeCoord.x < -0.5) {
						Face east = new Face(new Point(relativeCoord.x + 1, 0, - relativeCoord.y), new Point(relativeCoord.x + 1, 0, - relativeCoord.y - 1), new Point(relativeCoord.x + 1, -1, - relativeCoord.y - 1), new Point(relativeCoord.x + 1, -1, - relativeCoord.y));
						east.setCouleur(getShade(MAZE_COLOR, east));
						facesAffichees.add(east);
					} 
					else if(relativeCoord.x > 0.5) {
						Face west = new Face(new Point(relativeCoord.x, 0, - relativeCoord.y - 1), new Point(relativeCoord.x, 0, - relativeCoord.y), new Point(relativeCoord.x, -1, - relativeCoord.y), new Point(relativeCoord.x, -1, - relativeCoord.y - 1));
						west.setCouleur(getShade(MAZE_COLOR, west));
						facesAffichees.add(west);
					}
				}
			}
		}

		//tri des faces
		Collections.sort(facesAffichees);

		//parcours des faces
		for(int i = 0 ; i < facesAffichees.size() ; i++) {
			Face f = facesAffichees.get(i);

			Point[] points = f.getSommets();

			//passage des points en 2D
			for(int j = 0 ; j < points.length ; j++) {
				Point p = points[j];
				
				double distZ = (0.5 + p.z);

				double dw = ((0.5 - p.x) * SCREEN_DISTANCE) / distZ;
				double dh = ((0.5 + p.y) * SCREEN_DISTANCE) / distZ;

				p.x = SCREEN_WIDTH / 2.0 - dw;
				p.y = SCREEN_HEIGHT / 2.0 - dh;
			}

			//affichage de la face
			Polygon p = new Polygon(f.getXs(TAILLE_CASE), f.getYs(TAILLE_CASE), 4);

			gg.setColor(f.getCouleur());
			gg.fillPolygon(p);
			gg.setColor(Color.BLACK);
			gg.drawPolygon(p);
			gg.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}

	/**
	 *  Méthode permettant de créer une couleur plus ou moins foncée en fonction de la distance d'une face par rapport au robot 
	 * @param initialColor Couleur dont on veut obtenir une teinte
	 * @param f Face dont on veut modifier la couleur
	 * @return
	 */
	private Color getShade(Color initialColor, Face f) {
		double dist = Math.sqrt(f.distanceFromRobot());
		
		int r = initialColor.getRed();
		int g = initialColor.getGreen();
		int b = initialColor.getBlue();
		
		r = (int) Math.max(0, r - 15 * dist);
		g = (int) Math.max(0, g - 15 * dist);
		b = (int) Math.max(0, b - 15 * dist);
		
		return new Color(r, g, b);
	}
	
	/**
	 * Classe interne representant un point
	 */
	private class Point{

		/**
		 * Attributs representant les coordonnes dans l'espace du point 
		 */
		public double x, y, z;

		/**
		 * Constructeur de Point
		 * @param x : le point en abscisse
		 * @param y : le point en ordonnee
		 * @param z : la cote
		 */
		public Point(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	/**
	 * Classe interne representant une face
	 */
	private class Face implements Comparable<Face>{
		
		/**
		 * Attribut representant la liste des sommets
		 */
		private Point[] sommets;
		
		/**
		 * Attribut representant la couleur de la face
		 */
		private Color couleur;

		/**
		 * Constructeur de Face
		 * @param points
		 */
		public Face(Point... points) {
			sommets = points;
		}

		/**
		 * Getter des points en abscisse
		 * @param t
		 * @return
		 */
		public int[] getXs(int t) {

			int[] res = new int[4];

			for(int i = 0 ; i < 4 ; i++) {
				res[i] = (int)(sommets[i].x * TAILLE_CASE);
			}

			return res;
		}

		/**
		 * Getter des points en ordonnees
		 * @param t
		 * @return
		 */
		public int[] getYs(int t) {

			int[] res = new int[4];

			for(int i = 0 ; i < 4 ; i++) {
				res[i] = (int) (sommets[i].y * TAILLE_CASE) ;
			}

			return res;
		}

		/**
		 * Methode permettant de centrer un point
		 * @return
		 */
		private Point center() {
			Point res = new Point(0, 0, 0);
			
			for(int i = 0 ; i < 4 ; i++) {
				res.x += sommets[i].x;
				res.y += sommets[i].y;
				res.z += sommets[i].z;
			}
			
			res.x /= 4;
			res.y /= 4;
			res.z /= 4;

			return res;
		}
		
		/**
		 * Methode permettant de connaitre la distance entre un point et le robot
		 * @return
		 */
		private double distanceFromRobot() {
			Point center = center();
			
			return Math.pow(center.x, 2) + Math.pow(center.y, 2) + Math.pow(center.z, 2);
		}
		
		@Override
		public int compareTo(Face arg0) {
			//tri selon la distance robot-face

			double d1 = arg0.distanceFromRobot();
			double d2 = distanceFromRobot();
			
			if(d1 > d2) {
				return 1;
			}
			else if(d1 == d2) {
				return 0;
			}
			else {
				return -1;
			}
		}

		/**
		 * Getter de l'attribut sommets
		 * @return
		 */
		public Point[] getSommets() {
			return sommets;
		}

		/**
		 * Getter de l'attribut couleur
		 * @return
		 */
		public Color getCouleur() {
			return couleur;
		}

		/**
		 * Setter de l'attribut couleur
		 * @param couleur : la couleur
		 * @return
		 */
		public void setCouleur(Color couleur) {
			this.couleur = couleur;
		}
	}
	
	public void setScreenWidth(int w) {
		SCREEN_WIDTH = w;
	}
	
	public void setScreenDistance(double d) {
		SCREEN_DISTANCE = d;
	}
}
