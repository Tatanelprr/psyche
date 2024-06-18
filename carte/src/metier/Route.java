package carte.metier;

import java.util.ArrayList;
import java.util.List;

public class Route
{
    private static List<Route> routes = new ArrayList<>();

	private int    nbTroncons;
	private Ville villeDep  ;
	private Ville villeArr  ;

	public static Route ajouterRoute(int nbTroncons, Ville villeDep,  Ville villeArr)
	{
		if (nbTroncons >= 0 && nbTroncons <= 2)
		{
			Route route = new Route(nbTroncons, villeDep, villeArr);
			routes.add(route);
            villeDep.ajouterRoute(route);
            villeArr.ajouterRoute(route);
			return route;
		}
		else
		{
			return null;
		}

	}

	private Route(int nbTroncons, Ville villeDep,  Ville villeArr)
	{
		this.nbTroncons = nbTroncons;
		this.villeDep   = villeDep;
		this.villeArr   = villeArr;
	}

	public Ville getVilleDep() { return this.villeDep; }

	public Ville getVilleArr() { return this.villeArr; }

	public int getNbTroncons() { return this.nbTroncons; }

	public String toString()
	{
		return "Route :" + this.villeDep.getNumero() + ":" + this.villeArr.getNumero() + ":" + this.nbTroncons;
	}
	

	public static List<Route> getRoutes() { return routes; }
}
