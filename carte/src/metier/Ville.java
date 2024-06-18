package carte.metier;

import java.util.ArrayList;
import java.util.List;

public class Ville 
{
	private static int nbVilles = 0;
	private static List<Ville> villes = new ArrayList<>();
	
	private int    numero;
	private String nom   ;
	private int    x     ;
	private int    y     ;

	private List<Route> routesAdj;


	public static Ville creerVille(String nom, int x,  int y)
	{
		if (x >= 0 && x <= 1000 && y >= 0 && y <= 800)
		{
			Ville ville = new Ville(nom, x, y);
			villes.add(ville);
			return ville;
		}
		else
		{
			return null;
		}

	}

	private Ville(String nom, int x, int y)
	{
		this.numero = Ville.nbVilles;
		Ville.nbVilles++;
		this.nom    = nom;
		this.x      = x;
		this.y      = y;
		this.routesAdj = new ArrayList<>();
	}

	public String toString()
	{
		return "Ville " + this.numero + " :" + this.nom + ":" + this.x + "," + this.y;
	}
	

	public void ajouterRoute(Route route)
	{
        this.routesAdj.add(route);
    }

	public int getAbscisse() { return this.x; }

	public int getOrdonnee() { return this.y; }

	public String getNom() { return this.nom; }

	public int getNumero() { return this.numero; }

	public static List<Ville> getVilles() { return villes;}
	
    public void setAbscisse(int x) { this.x = x; }

    public void setOrdonnee(int y) { this.y = y; }

	public void setNom(String nom) { this.nom = nom;}

	public static Ville trouverVilleParNum(int num)
	{
        for (Ville ville : villes)
		{
            if (ville.getNumero() == num)
			{
                return ville;
            }
        }
        return null; 
    }
}
