package fr.utt.lo02.uno.io;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.CarteNumero;
import fr.utt.lo02.uno.jeu.carte.Couleur;
import fr.utt.lo02.uno.jeu.carte.TypeCarte;


/**
 * Singleton permettant d'acceder aux images des cartes de Uno, a partir de son sprite
 */
public class ImageCartes {
	private static final int NOMBRE_IMAGES = 16, LARGEUR = 285, HAUTEUR = 435;
	private static ImageCartes instance;
	private final BufferedImage[] images;


	/**
	 * Design-pattern singleton. Cree l'unique instance de cette classe si elle n'existe pas encore
	 * @return l'unique instance de cette classe
	 */
	public static ImageCartes getInstance() {
		synchronized(Images.class) {
			if(instance == null)
				instance = new ImageCartes();
			return instance;
		}
	}

	/**
	 * Constructeur prive, selon le design-pattern singleton. Charge le sprite et range les images de cartes decoupees a l'interieur d'un tableau
	 */
	private ImageCartes() {
		BufferedImage sprite = Images.getInstance().getImage("cartes.png");
		images = new BufferedImage[NOMBRE_IMAGES];
		for(int i=0 ; i<images.length ; i++)
			images[i] = sprite.getSubimage((i % 4) * LARGEUR, (i/4) * HAUTEUR, LARGEUR, HAUTEUR);
	}
	
	/**
	 * @param type le type de la carte
	 * @param numero le numero de la carte, dans le cas d'une carte avec numero
	 * @return l'image associee au type et au numero de la carte
	 * @see Carte
	 * @see CarteNumero
	 */
	public BufferedImage getImage(TypeCarte type, int numero) {
		return images[getRangImage(type, numero)];
	}

	/**
	 * @param carte la carte dont on souhaite obtenir l'image
	 * @return l'image associee a cette carte
	 */
	public BufferedImage getImage(Carte carte) {
		return getImage(carte.getType(), carte.getType() == TypeCarte.NUMERO ? ((CarteNumero) carte).getNumero() : 0);
	}

	/**
	 * Dessine la carte en position {0, 0} avec la taille precisee
	 * @param g les graphics dans lesquels dessiner cette carte
	 * @param width la largeur de la carte
	 * @param height la hauteur de la carte
	 * @param carte la carte devant etre dessinee
	 * @param visible si faux, la carte sera dessinee face cachee
	 * @see #dessiner(Graphics, int, int, int, int, Carte, boolean)
	 */
	public void dessiner(Graphics g, int width, int height, Carte carte, boolean visible) {
		dessiner(g, 0, 0, width, height, carte, visible);
	}

	/**
	 * Dessine la carte en position et taille precisee
	 * @param g les graphics dans lesquels dessiner cette carte
	 * @param x la coordonnee x ou dessiner cette carte
	 * @param y la coordonnee y ou dessiner cette carte
	 * @param width la largeur de la carte
	 * @param height la hauteur de la carte
	 * @param carte la carte devant etre dessinee
	 * @param visible si faux, la carte sera dessinee face cachee
	 * @see #dessiner(Graphics, BufferedImage, int, int, int, int, Couleur)
	 */
	public void dessiner(Graphics g, int x, int y, int width, int height, Carte carte, boolean visible) {
		if(carte != null)
			dessiner(g, visible ? getImage(carte) : getImage(null, 0), x, y, width, height, carte.getCouleur());
	}
	
	/**
	 * Dessine l'image en position et taille precisee, avec prealablement une couleur de fond
	 * @param g les graphics dans lesquels dessiner cette carte
	 * @param image l'image a dessiner
	 * @param x la coordonnee x ou dessiner cette carte
	 * @param y la coordonnee y ou dessiner cette carte
	 * @param width la largeur de la carte
	 * @param height la hauteur de la carte
	 * @param couleur la couleur de fond de l'image
	 * @see #getImage(Carte)
	 */
	public static void dessiner(Graphics g, BufferedImage image, int x, int y, int width, int height, Couleur couleur) {
		g.setColor(couleur == null ? Color.BLACK : couleur.getColor());
		g.fillRoundRect(x + width/75, y + height/55, width - width/33, height - height/33, width/5, height/5);
		g.drawImage(image, x, y, width, height, null);
	}
	
	/**
	 * Calcule la position de l'image dans le tableau, en fonction du type de la carte, et de son numero dans le cas d'une {@link CarteNumero}
	 * @param type le type de la carte
	 * @param numero le numero dans la carte, dans le cas d'une {@link CarteNumero}
	 * @return le rang de l'image dans le tableau des images de cartes, et dans la feuille de sprites
	 */
	public static int getRangImage(TypeCarte type, int numero) {
		if(type != null) switch(type) {
		case NUMERO: return numero;
		case PLUS_DEUX: return 10;
		case INVERSION: return 11;
		case PASSE_TOUR: return 12;
		case PLUS_QUATRE: return 13;
		case JOKER: return 14;
		}
		return 15;
	}
	
}
