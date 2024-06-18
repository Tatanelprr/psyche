package Jeu.metier;

import java.awt.Color;

public enum Couleur {
	
	OR     (255, 215,   0),
	GRIS   (240, 240, 242),
	JAUNE  (255, 255,   0),
	ROUGE  (255,   0,   0),
	ORANGE (255, 165,   0),
	BLEU   (  0,   0, 255),
	VIOLET (128,   0, 128),
	VERT   (  0, 128,   0),
	CYAN   (  0, 255, 255);

	private int r;
	private int v;
	private int b;

	Couleur( int r, int v, int b)
	{
		this.r = r;
		this.v = v;
		this.b = b;
	}

	public Color getColor()
	{
		return new Color(this.r, this.v, this.b);
	}

	public String getSymbole()
	{
		return name();
	}
	
	public static int getNbCouleur()
	{
		return Couleur.values().length;
	}

	public static Couleur valueOf( int ordinal)
	{
		if ( ordinal >= 0 && ordinal <= Couleur.values().length)
		{
			return Couleur.values()[ordinal];
		}
		return null;
	}
}