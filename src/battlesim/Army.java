package battlesim;

import java.util.ArrayList;

public class Army {

	private int nSoldiersSO;
	private int nArtillerySO;
	private int nTanksSO;
	private int nPlanesSO;
	
	private CombatUnit[] army = new CombatUnit[4];
	
	public static final double probFace = 4.0/13;
	
	public Army(int nSoldiers, int nSoldiersSO, int nSoldiersFace, int nArtillery, int nArtillerySO, int nArtilleryFace, int nTanks, int nTanksSO, int nTanksFace, int nPlanes, int nPlanesSO, int nPlanesFace)
	{
		army[0] = new Soldier(nSoldiers, nSoldiersFace);
		army[1] = new Artillery(nArtillery, nArtilleryFace);
		army[2] = new Tank(nTanks, nTanksFace);
		army[3] = new Plane(nPlanes, nPlanesFace);
		this.nSoldiersSO = nSoldiersSO;
		this.nArtillerySO = nArtillerySO;
		this.nTanksSO = nTanksSO;
		this.nPlanesSO = nPlanesSO;
	}
	
//	public int nHitsGuaranteedA()
//	{
//		int nHits = 0;
//		nHits += nSoldiers * soldier.nHitsAttack() + nSoldiersFace * soldier.nHitsAttackFace();
//		nHits += nTanks;
//	}
//	
//	public int nHitsTotalA()
//	{
//		
//	}
//	
//	public static Outcome battle(Army attacker, Army defender)
//	{
//		
//	}
//	
//	public static ArrayList<Outcome> possibleOutcomes(Army attacker, Army defender)
//	{
//		
//	}
}
