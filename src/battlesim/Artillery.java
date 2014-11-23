package battlesim;

public class Artillery extends CombatUnit{

	public Artillery(int gNUnits, int gNUnitsFace) {
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
		return 1;
	}

	@Override
	public int nHitsDefenseFace() {
		return 2;
	}

	@Override
	public int nHitsAbsorbed() {
		return 3;
	}

}
