package battlesim;


public class Soldier extends CombatUnit{
	
	public Soldier(int gNUnits, int gNUnitsFace) {
		super(gNUnits, gNUnitsFace);
	}

	@Override
	public int nHitsAttack() {
		return 1;
	}

	@Override
	public int nHitsAttackFace() {
		return 2;
	}

	@Override
	public int nHitsDefense() {
		return 2;
	}

	@Override
	public int nHitsDefenseFace() {
		return 3;
	}

	@Override
	public int nHitsAbsorbed() {
		return 3;
	}
	
}
