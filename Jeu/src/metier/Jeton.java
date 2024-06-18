package Jeu.metier;

public class Jeton
{
	private final IRessource type;

	public Jeton( IRessource type )
	{
		this.type = type;
	}

	public IRessource getType()
	{
		return this.type;
	}

	public String toString()
	{
		return this.type.toString();
	}
	
}