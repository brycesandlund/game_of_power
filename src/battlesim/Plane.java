package battlesim;

public class Plane extends CombatUnit{

	public Plane(int gNUnits, int gNUnitsFace) {
		super(gNUnits, gNUnitsFace);
	}

	@Override
	public int nHitsAttack() {
		return 2;
	}

	@Override
	public int nHitsAttackFace() {
		return 4;
	}

	@Override
	public int nHitsDefense() {
		return 2;
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
