package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tile {

	public double probability;
	public String name;	//unused by map generation algorithm
	public TileNames tileName;
	public Res[] resources = new Res[4];
	private byte stringN;
	private boolean uncovered = false;
	
	//smarter way of doing this would be with boolean arrays
	public static final Res[][] resourceCapabilities =
	{
	/*desert*/{Res.S, Res.C, Res.I, Res.O, Res.G},
	/*river*/{Res.L, Res.M, Res.S, Res.G},
	/*lake*/{Res.L, Res.M, Res.S, Res.O},
	/*field*/{Res.W, Res.R, Res.M, Res.S, Res.C, Res.I, Res.O, Res.G},
	/*wooded*/{Res.M, Res.L, Res.S, Res.C, Res.I, Res.O, Res.G},
	/*mountain*/{Res.M, Res.L, Res.S, Res.C, Res.I, Res.O, Res.G}
	};
	
	public static final Res[][] resourceDoubles =
	{
	/*desert*/{Res.O, Res.O},
	/*river*/{Res.M, Res.L, Res.S},
	/*lake*/{Res.M, Res.L, Res.S},
	/*field*/{Res.W, Res.R, Res.W, Res.R},
	/*wooded*/{Res.L, Res.L},
	/*mountain*/{Res.S, Res.I, Res.S, Res.I}
	};
	
	public static final double probDouble = 0.25;
	
	public Tile(double probability, String name) {
		this.probability = probability;
//		this.name = name;
	}
	
	public Tile()
	{
		
	}
	
	public Tile(TileNames tileName)
	{
		this.tileName = tileName;
	}
	
	public Tile(Tile other)
	{
		this.tileName = other.tileName;
	}
	
	public boolean isUncovered()
	{
		return uncovered;
	}
	
	public void uncover()
	{
		uncovered = true;
	}
	
	public void cover()
	{
		uncovered = false;
	}
	
	public void fillResources(Random r)
	{
		List<Res> totalPoss = new ArrayList<Res>();
		Res[] values = Res.values();
		for (int i = 0; i < 10; ++i)	//only add single resources + N
		{
			totalPoss.add(values[i]);
		}
		for (int i = 0; i < resourceDoubles[tileName.ordinal()].length; ++i)
		{
			totalPoss.add(resourceDoubles[tileName.ordinal()][i]);
		}
		for (int i = 0; i < resources.length; ++i)
		{
			Res currentRes = totalPoss.get(r.nextInt(totalPoss.size()));

			if (Arrays.asList(resourceCapabilities[tileName.ordinal()]).contains(currentRes))
			{
				if (r.nextDouble() < probDouble && currentRes != Res.N)
				{
					//Magic number 10 is offset from singles to doubles in Res enum
					resources[i] = Res.values()[currentRes.ordinal() + 10];
				}
				else
				{
					resources[i] = currentRes;
				}
			}
			else
			{
				resources[i] = Res.N;
			}
		}
	}
	
	public boolean equals(Object other)
	{
		if (other.getClass() != this.getClass())
		{
			return false;
		}
		Tile otherTile = (Tile)other;
		if (otherTile.tileName.equals(tileName))
		{
			return true;
		}
		return false;
	}
	
	public String toString()
	{
		StringBuilder text = new StringBuilder();
		text.append(String.format("%8s ", tileName));
		text.append((uncovered ? 1 : 0) + "  ");
		for (int i = 0; i < resources.length; ++i)
		{
//			if (Arrays.asList(resourceDoubles[tileName.ordinal()]).contains(resources[i]))
//			{
//				text.append(resources[i].toString() + resources[i].toString() + " ");
//			}
//			else
//			{
				text.append(resources[i].toString() + "  ");
//			}
		}
		text.append(" ");
//		return tileName.toString() + "[" + resources[0].toString() + "," + resources[1].toString() + "," + resources[2].toString() + "," + resources[3].toString() + "]";
		return text.toString();
	}
}
