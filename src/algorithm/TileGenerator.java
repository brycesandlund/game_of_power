package algorithm;

import java.awt.Point;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import ui.*;

public class TileGenerator {
	
	public static final String location = /*"C:/Users/Owner/Documents/Word Documents/TheGameOfPower/";*/
		"";
	public static final String mapLocation = location + "map.txt";
	
	//inverted once again, x is y and y is x
					//nPlayers:  0, 1, 2, 3, 4, 5, 6
//	static final int[] xTiles = {0, 0, 5, 6, 7, 7, 7};
//	static final int[] yTiles = {0, 0, 4, 5, 6, 7, 8};
	
	//updated 5/19/12
//	static final int[] xTiles = {0, 0, 4, 5, 6, 7, 7};
//	static final int[] yTiles = {0, 0, 4, 5, 6, 6, 7};
	
	//updated 7/10/12//nPlayers: 0, 1, 2, 3, 4, 5, 6
	static final int[] xTiles = {0, 0, 4, 4, 5, 5, 6};
	static final int[] yTiles = {0, 0, 3, 4, 4, 5, 5};
	
	
	//not inverted...
	//UNUSED
								//nPlayers:  0, 1, 2, 3, 4, 5, 6
	static final int[] originalSquareXppl = {0, 0, 2, 3, 3, 4, 4};
	static final int[] originalSquareYppl = {0, 0, 2, 2, 3, 3, 3};
	
//	static final int[] nStartTilesPpl = {0, 0, 6, 10, 14, 18, 22};
//	static final double[] percentPpl = {0, 0, .16, .08, .04, .02, .01};
	
	static final double percent = .15;
	
	static int[] squareStats = new int[TileNames.values().length];
	static int[] resStats = new int[Res.values().length];
	
	public Tile[][] grid;
	
	int nPlayers;
	
	public TileGenerator()
	{
	}
	
	public void continueGame()
	{
		try
		{
			File mapFile = new File(mapLocation);	//map file location
			
			Scanner fileIn = new Scanner(mapFile);
			nPlayers = fileIn.nextInt();
			fileIn.next();	//skips "player" String literal
			
			grid = new Tile[yTiles[nPlayers]][xTiles[nPlayers]];
			
			TileNames[] tileValues = TileNames.values();
			Res[] resValues = Res.values();
			
			Tile currentTile;
			Res[] currentReses;
			
			for (int i = 0; i < grid.length; ++i)
			{
				for (int j = 0; j < grid[i].length; ++j)
				{
					String tileName = fileIn.next();
					currentTile = new Tile();
					for (int k = 0; k < tileValues.length; ++k)	//converts string into storable TileName enum
					{
						if (tileName.equals(tileValues[k].toString()))
						{
							currentTile = new Tile(tileValues[k]);
						}
					}
					
					if (fileIn.nextInt() == 1)
					{
						currentTile.uncover();
					}
					
					currentReses = new Res[4];
					for (int k = 0; k < 4; ++k)
					{
						String resString = fileIn.next();
//						String charString = resString.charAt(0) + "";	//should be a String of the first char
						for (int l = 0; l < resValues.length; ++l)	//HashMap would be more efficient
						{
							if (resString.equals(resValues[l].toString()))
							{
								currentReses[k] = resValues[l];
							}
						}
					}
					currentTile.resources = currentReses;
					grid[i][j] = currentTile;
					adjustStats(currentTile);
				}
			}
//			playGame(grid, 0, 0, nPlayers);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("oh FUCK!...  Continue Game");
		}
	}
	
	public void uncoverStart()
	{
//		uncoverStartSquare(grid, originalSquareXppl[nPlayers], originalSquareYppl[nPlayers], nPlayers);
//		int nTiles = originalSquareXppl[nPlayers] * originalSquareYppl[nPlayers];
		uncoverRandomSquares((int)Math.round(xTiles[nPlayers] * yTiles[nPlayers] * percent));
	}
	
//	public void uncoverPlayer(int player)
//	{
//		uncoverRandomSquares((int)Math.round(xTiles[nPlayers] * yTiles[nPlayers] * percentPpl[player]));
//	}
	
	public void uncoverRandomSquares(int nTiles)
	{
		Random r = new Random();
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		for (int i = 0; i < grid.length; ++i)
		{
			for (int j = 0; j < grid[i].length; ++j)
			{
				if (!grid[i][j].isUncovered())
				{
					tiles.add(grid[i][j]);
				}
			}
		}
		for (int i = 0; i < nTiles; ++i)
		{
			if (!tiles.isEmpty())
			{
				tiles.remove(r.nextInt(tiles.size())).uncover();
			}
		}
	}
	
	public void newGame(int players)
	{
		nPlayers = players;
		try
		{
			
			grid = map(yTiles[nPlayers], xTiles[nPlayers], (int)(yTiles[nPlayers] * xTiles[nPlayers] * 1.5), .8, 4);	//and here
			uncoverStart();
			updateGrid();
		
//			playGame(grid, originalSquareXppl[nPlayers], originalSquareYppl[nPlayers], nPlayers);	//x and y are correct here
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("oh FUCK!...  New Game");
		}
	}
	
	public void updateGrid()
	{
		PrintWriter out = null;
		try
		{
			File mapFile = new File(mapLocation);	//map file location
			out = new PrintWriter(mapFile);
						
			out.println(nPlayers + " players");
	
			for (int i = 0; i < grid.length; ++i)
			{
				for (int j = 0; j < grid[i].length; ++j)
				{
					out.print(grid[i][j]);
				}
				out.print("\n");
			}
			out.close();	//unfortunately, the finally call never will happen since playGame continues to check for coordinates
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("oh FUCK!...  Update Grid");
			if (out != null)
				out.close();
		}
	}
	
	private void uncoverStartSquare(Tile[][] grid, int startSquareX, int startSquareY, int nPlayers)
	{
		int topAdjust = (grid.length - startSquareY) / 2;
		int leftAdjust = (grid[0].length - startSquareX) / 2;	//assumes not a jagged array
		for (int i = topAdjust; i < topAdjust + startSquareY; ++i)
		{
			for (int j = leftAdjust; j < leftAdjust + startSquareX; ++j)
			{
				grid[i][j].uncover();
			}
		}
	}
	
	private void playGame(Tile[][] grid, int startSquareX, int startSquareY, int nPlayers)
	{
//		printStats();
		
		//print starting square
		int topAdjust = (grid.length - startSquareY) / 2;
		int leftAdjust = (grid[0].length - startSquareX) / 2;	//assumes not a jagged array
		if (startSquareX != 0 && startSquareY != 0)
		{
			System.out.println("Starting coordinate square is: (" + leftAdjust + ", " + topAdjust + ")");
			System.out.println("Board size: " + grid.length + " x " + grid[0].length);
		}
		for (int i = topAdjust; i < topAdjust + startSquareY; ++i)
		{
			for (int j = leftAdjust; j < leftAdjust + startSquareX; ++j)
			{
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
		
		//prompt for coordinates to display
		while (true)
		{
			try
			{
				System.out.println("Enter coordinates of explored squares, or \"originalsquare\" to view the starting grid:");
				Scanner in = new Scanner(System.in);
				String inString;
				String exitString = "originalsquare";
				while (true)
				{
					System.out.print("X: ");
					inString = in.next();
					if (inString.toLowerCase().equals(exitString))
						break;
					int x = Integer.parseInt(inString);
					System.out.print("Y: ");
					inString = in.next();
					if (inString.toLowerCase().equals(exitString))
						break;
					int y = Integer.parseInt(inString);
					System.out.println(grid[y][x]);
					System.out.println();
				}
				playGame(grid, originalSquareXppl[nPlayers], originalSquareYppl[nPlayers], nPlayers);	//these values need to be the same as last game in order to print the same starting square
			}
			catch (Exception e)
			{
				continue;
			}
		}
	}
	
	private static void printGrid(Tile[][] grid, int Xlength, int Ylength)
	{
		for (int i = 0; i < Xlength; ++i)
		{
			for (int j = 0; j < Ylength; ++j)
			{
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
	
	public static Point newRandomPt(Tile[][] grid, Random r)
	{
		Point newPt = null;
		do
		{
			newPt = new Point(r.nextInt(grid.length), r.nextInt(grid[0].length));
		}
		while (grid[newPt.x][newPt.y] != null);
		return newPt;
	}
	
	/**
	 * 
	 * @param totalX - Height of grid (mistake made in code)
	 * @param totalY - Width of grid (same deal)
	 * @param nTiles - The number of total tiles to select from, selected tiles are removed (variance of grid)
	 * @param probWater - The probability of A string of water continuing or ending, higher number means more likely to be a smaller number of water systems
	 * @param waterContinuity - A parameter affecting the placement of a lake or river from a random point, higher number means random points selected near water are more likely to be that same water
	 * @return
	 */
	public static Tile[][] map(int totalX, int totalY, int nTiles, double probWater, int waterContinuity)
	{
		Random r = new Random();
		ArrayList<Tile> allTiles = new ArrayList<Tile>();
		//need to modify Tile if these names are changed
//		double[] probTiles = {6.0/81, 11.0/81, 10.0/81, 20.0/81, 15.0/81, 16.0/81};
		//updated 6-2-12
//		String[] names = {"desert", "river", "lake", "field", "wooded", "mountain"};
		double[] probTiles = {0.1, 0.15, 0.1, 0.20, 0.25, 0.2};
		int[] tilesOfEach = new int[probTiles.length];
		for (int i = 0; i < probTiles.length; ++i)
		{
			tilesOfEach[i] = (int)Math.round(probTiles[i] * nTiles);
		}
		for (int i = 0; i < tilesOfEach.length; ++i)
		{
			for (int j = 0; j < tilesOfEach[i]; ++j)
			{
				allTiles.add(new Tile(TileNames.values()[i]));
			}
		}
		Tile[][] grid = new Tile[totalX][totalY];
		int totalXY = totalX * totalY;
		int filledXY = 0;
		ArrayList<Tile>[][] influenceGrid = new ArrayList[totalX][totalY];
		for (int i = 0; i < totalX; ++i)
		{
			for (int j = 0; j < totalY; ++j)
			{
				influenceGrid[i][j] = new ArrayList<Tile>();
			}
		}
		//water contour
		ArrayList<Tile> waterTiles = new ArrayList<Tile>();
		//adds all water tiles into waterTiles arraylist
		for (int i = 0; i < allTiles.size(); ++i)
		{
			if (allTiles.get(i).equals(new Tile(TileNames.lake)) || allTiles.get(i).equals(new Tile(TileNames.river)))
			{
				waterTiles.add(allTiles.get(i));
			}
			else
			{
				waterTiles.add(new Tile(TileNames.nothing));
			}
		}
		//remove water tiles from allTiles
		while (allTiles.contains(new Tile(TileNames.lake)))
		{
			allTiles.remove(new Tile(TileNames.lake));
		}
		while (allTiles.contains(new Tile(TileNames.river)))
		{
			allTiles.remove(new Tile(TileNames.river));
		}
//		System.out.println(allTiles.contains(new Tile(TileNames.lake)));
//		System.out.println(allTiles.contains(new Tile(TileNames.river)));
		int nTries = 0;
		boolean inString = false;
		int nWaterTiles = 0;
		Point currentPoint = new Point(r.nextInt(totalX), r.nextInt(totalY));
		Point next = currentPoint;
		while (nTries < totalXY)
		{
			currentPoint = new Point(next);
			Tile currentTile = new Tile(TileNames.nothing);
			
			ArrayList<Tile> newTempList = new ArrayList<Tile>();
			newTempList.addAll(waterTiles);
						
			//if not already in a run, start from a new random point
			if (!inString || (!newTempList.contains(new Tile(TileNames.lake)) && !newTempList.contains(new Tile(TileNames.river))))
			{
				currentPoint = newRandomPt(grid, r);
			}
			
			//adds extras according to bordering tiles
			for (int i = 0; i < waterTiles.size(); ++i)
			{
				for (int j = 0; j < influenceGrid[currentPoint.x][currentPoint.y].size(); ++j)
				{
					if (influenceGrid[currentPoint.x][currentPoint.y].get(j).equals(waterTiles.get(i)))
					{
						newTempList.add(waterTiles.get(i));
					}
				}
			}
			
			if (!inString || (!waterTiles.contains(new Tile(TileNames.lake)) && !waterTiles.contains(new Tile(TileNames.river))))
			{
				currentTile = new Tile(waterTiles.get(r.nextInt(waterTiles.size())));
			}
			else
			{
				while (currentTile.tileName == TileNames.nothing)
				{
					currentTile = new Tile(waterTiles.get(r.nextInt(waterTiles.size())));					
				}
			}

			if (currentTile.tileName != TileNames.nothing)
			{
				if (r.nextDouble() < probWater)
				{
					inString = true;
				}
				else
				{
					inString = false;
				}
				int xLeft = currentPoint.x - 1;
				int xRight = currentPoint.x + 1;
				int yUp = currentPoint.y - 1;
				int yDown = currentPoint.y + 1;
				//if out of bounds, saving as previous point will just affect influence on a grid that will never
				//be used.  Which will work.
				if (xLeft < 0)
				{
					xLeft = currentPoint.x;
				}
				if (yUp < 0)
				{
					yUp = currentPoint.y;
				}
				if (xRight > totalX - 1)
				{
					xRight = currentPoint.x;
				}
				if (yDown > totalY - 1)
				{
					yDown = currentPoint.y;
				}
				//list is repeated to keep things even more in similar motion, so lakes will likely run into lakes and rivers into other rivers
				for (int i = 0; i < waterContinuity; ++i)
				{
					influenceGrid[xLeft][currentPoint.y].add(currentTile);
					influenceGrid[xRight][currentPoint.y].add(currentTile);
					influenceGrid[currentPoint.x][yUp].add(currentTile);
					influenceGrid[currentPoint.x][yDown].add(currentTile);
				}
				currentTile.fillResources(r);
				grid[currentPoint.x][currentPoint.y] = currentTile;
				adjustStats(currentTile);
				if (grid[xLeft][currentPoint.y] != null && grid[xRight][currentPoint.y] != null && grid[currentPoint.x][yUp] != null && grid[currentPoint.x][yDown] != null)
				{
					inString = false;
				}
				else	//this is a sloppy and shitty way of doing it...  Will fix later - maybe ;)
				{
					do
					{
						switch (r.nextInt(6))	//looks like a do-while would've been better here!
						{
						case 0: 
						case 1:
							next.x = xLeft;
							next.y = currentPoint.y;
							break;
						case 2:
						case 3:
							next.x = xRight;
							next.y = currentPoint.y;
							break;
						case 4: 
							next.x = currentPoint.x;
							next.y = yUp;
							break;
						case 5:
							next.x = currentPoint.x;
							next.y = yDown;
							break;
						}
					}	//added clause in the while will cause rivers or lakes to wrap around corners
					while (grid[next.x][next.y] != null || currentPoint.x == next.x && currentPoint.y == next.y);
				}
				++nWaterTiles;
			}
			waterTiles.remove(currentTile);
			++nTries;
		}
		
		for (int i = 0; i < influenceGrid.length; ++i)
		{
			for (int j = 0; j < influenceGrid[i].length; ++j)
			{
				influenceGrid[i][j].clear();
			}
		}
		//normal grid
		while (filledXY < totalXY)
		{
//			Point currentPoint = new Point(filledXY % totalX, filledXY / totalX);
			currentPoint = new Point(filledXY % totalX, filledXY / totalX);
			while (grid[currentPoint.x][currentPoint.y] != null)
			{
				++filledXY;
				if (filledXY >= totalXY)
				{
					return grid;
				}
				currentPoint = new Point(filledXY % totalX, filledXY / totalX);
			}
			ArrayList<Tile> newTempList = new ArrayList<Tile>();
			newTempList.addAll(allTiles);
			//adds extras according to bordering tiles
			for (int i = 0; i < allTiles.size(); ++i)
			{
				for (int j = 0; j < influenceGrid[currentPoint.x][currentPoint.y].size(); ++j)
				{
					if (influenceGrid[currentPoint.x][currentPoint.y].get(j).equals(allTiles.get(i)))
					{
						newTempList.add(allTiles.get(i));
					}
				}
			}
			Tile currentTile = new Tile(newTempList.get(r.nextInt(newTempList.size())));
			int xLeft = currentPoint.x - 1;
			int xRight = currentPoint.x + 1;
			int yUp = currentPoint.y - 1;
			int yDown = currentPoint.y + 1;
			//if out of bounds, saving as previous point will just affect influence on a grid that will never
			//be used.  Which will work.
			if (xLeft < 0)
			{
				xLeft = currentPoint.x;
			}
			if (yUp < 0)
			{
				yUp = currentPoint.y;
			}
			if (xRight > totalX - 1)
			{
				xRight = currentPoint.x;
			}
			if (yDown > totalY - 1)
			{
				yDown = currentPoint.y;
			}
			influenceGrid[xLeft][currentPoint.y].add(currentTile);
			influenceGrid[xRight][currentPoint.y].add(currentTile);
			influenceGrid[currentPoint.x][yUp].add(currentTile);
			influenceGrid[currentPoint.x][yDown].add(currentTile);
			++filledXY;
			allTiles.remove(currentTile);
			currentTile.fillResources(r);
			grid[currentPoint.x][currentPoint.y] = currentTile;
			adjustStats(currentTile);
		}
		
		return grid;
	}
	
	public static void adjustStats(Tile currentTile)
	{
		++squareStats[currentTile.tileName.ordinal()];
		for (int i = 0; i < 4; ++i)
		{
			++resStats[currentTile.resources[i].ordinal()];
		}
	}
	
	public static void printStats()
	{
		TileNames[] vals = TileNames.values();
		for (int i = 0; i < vals.length; ++i)
		{
			System.out.println(vals[i] + ": " + squareStats[i]);
		}
		System.out.println();
		
		Res[] resVals = Res.values();
		for (int i = 0; i < resVals.length; ++i)
		{
			System.out.println(resVals[i] + ": " + resStats[i]);
		}
		System.out.println();
	}
}



