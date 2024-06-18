package Jeu.metier;

public class ZoneCliquable
{
    private int x, y, largeur, hauteur;
    private Ville villeAssociee;

    public ZoneCliquable(int x, int y, int largeur, int hauteur, Ville villeAssociee)
	{
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.villeAssociee = villeAssociee;
	}
		

    public boolean contains(int clickX, int clickY)
	{
        return clickX >= x && clickX <= x + this.largeur && clickY >= y && clickY <= y + this.hauteur;
    }

    public Ville getVilleAssociee()
	{
        return this.villeAssociee;
    }
}
