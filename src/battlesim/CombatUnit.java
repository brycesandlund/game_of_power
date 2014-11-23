package battlesim;

public abstract class CombatUnit {
	
	protected int nUnits;
	protected int nUnitsFace;
	
	public CombatUnit(int gNUnits, int gNUnitsFace)
	{
		nUnits = gNUnits;
		nUnitsFace = gNUnitsFace;
	}
	
	public void setNUnits(int gNUnits)
	{
		nUnits = gNUnits;
	}
	
	public void setNUnitsFace(int gNUnitsFace)
	{
		nUnitsFace = gNUnitsFace;
	}
	
	public int nUnits()
	{
		return nUnits;
	}
	
	public int nUnitsFace()
	{
		return nUnitsFace;
	}
	
	public int nTotalHitsAttack()
	{
		return nHitsAttack() * nUnits() + nHitsAttackFace() * nUnitsFace();
	}
	
	public int nTotalHitsDefense()
	{
		return nHitsDefense() * nUnits() + nHitsDefenseFace() * nUnitsFace();
	}
	
	public int nTotalHitsAbsorbed()
	{
		return (nUnits() + nUnitsFace()) * nHitsAbsorbed();
	}

	//wish all could be static...
	public abstract int nHitsAttack();
	
	public abstract int nHitsAttackFace();
	
	public abstract int nHitsDefense();
	
	public abstract int nHitsDefenseFace();
	
	public abstract int nHitsAbsorbed();
	
}
