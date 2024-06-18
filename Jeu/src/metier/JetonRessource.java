package Jeu.metier;

public enum JetonRessource implements IRessource
{
	ALUMINIUM ( Couleur.OR     ),
	ARGENT    ( Couleur.GRIS   ),
	OR        ( Couleur.JAUNE  ),
	COBALT    ( Couleur.ROUGE  ),
	FER       ( Couleur.ORANGE ),
	NICKEL    ( Couleur.BLEU   ),
	PLATINE   ( Couleur.VIOLET ),
	TITANE    ( Couleur.VERT   ),
	MONNAIE   ( Couleur.CYAN   );

	private Couleur couleur;

	private JetonRessource( Couleur coul )
	{
		this.couleur = coul;
	}

	public String getLibCourt()
	{
		return name().substring(0, 3);
	}

	public Couleur getCouleur()
	{
		return this.couleur;
	}

	public String toString()
	{
		return name();
	}

}