package battlesim;

public class Outcome {

	private Army attacker;
	private Army defender;
	private double probability;
	
	public Outcome(Army gAttacker, Army gDefender, double gProbability)
	{
		attacker = gAttacker;
		defender = gDefender;
		probability = gProbability;
	}
	
	public Army getLostAttackers()
	{
		return attacker;
	}
	
	public Army getLostDefenders()
	{
		return defender;
	}
	
	public double getProbability()
	{
		return probability;
	}
}
