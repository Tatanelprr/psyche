package Jeu.metier;

import java.util.*;

public class Pioche
{
	private ArrayList<Jeton> tabJeton;

	public Pioche()
	{
		this.tabJeton = new ArrayList<>();
		this.initPioche();
	}

	private void initPioche()
	{
		for(int i = 0; i < 4; i++)
		{
			this.tabJeton.add(new Jeton(JetonRessource.ALUMINIUM));
			this.tabJeton.add(new Jeton(JetonRessource.ARGENT));
			this.tabJeton.add(new Jeton(JetonRessource.OR));
			this.tabJeton.add(new Jeton(JetonRessource.COBALT));
			this.tabJeton.add(new Jeton(JetonRessource.FER));
			this.tabJeton.add(new Jeton(JetonRessource.NICKEL));
			this.tabJeton.add(new Jeton(JetonRessource.PLATINE));
			this.tabJeton.add(new Jeton(JetonRessource.TITANE));
			this.tabJeton.add(new Jeton(JetonRessource.MONNAIE));
			this.tabJeton.add(new Jeton(JetonRessource.MONNAIE));
		}

		Collections.shuffle(this.tabJeton);
	}

	public Jeton tirerJeton()
	{
		if(this.tabJeton.get(0) != null)
		{
			return this.tabJeton.remove(0);
		}
		return null;
	}

	public int getTaille()
	{
		return this.tabJeton.size();
	}
}