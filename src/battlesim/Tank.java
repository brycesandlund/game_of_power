package battlesim;

public class Tank extends CombatUnit{

	
	
	public Tank(int gNUnits, int gNUnitsFace) {
		super(gNUnits, gNUnitsFace);
	}

	@Override
	public int nHitsAttack() {
		return 3;
	}

	@Override
	public int nHitsAttackFace() {
		return 5;
	}

	@Override
	public int nHitsDefense() {
		return 3;
	}

	@Override
	public int nHitsDefenseFace() {
		return 5;
	}

	@Override
	public int nHitsAbsorbed() {
		return 5;
	}

}
