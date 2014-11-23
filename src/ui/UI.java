package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import algorithm.Res;
import algorithm.Tile;
import algorithm.TileGenerator;

public class UI extends javax.swing.JFrame{
	
	public static final int minPlayers = 2;
	public static final int maxPlayers = 6;
	
	int width;
	int height;
	
	JComboBox combo;
	
	final String imageLocation = "images/";
		/*TileGenerator.location + "Images/";*/
	ImageIcon desertIcon = new ImageIcon(imageLocation + "Desert.jpg");
	ImageIcon riverIcon = new ImageIcon(imageLocation + "River.jpg");
	ImageIcon lakeIcon = new ImageIcon(imageLocation + "Lake.jpg");
	ImageIcon fieldIcon = new ImageIcon(imageLocation + "Field.jpg");
	ImageIcon woodedIcon = new ImageIcon(imageLocation + "Wooded.jpg");
	ImageIcon mountainIcon = new ImageIcon(imageLocation + "Mountain.jpg");

	ImageIcon unknownIcon = new ImageIcon(imageLocation + "Unknown.jpg");
	
	TileGenerator tiles;
	
	JLabel[][] labels;
	
	private String getStringAndSetColor(Graphics g, Res resource, Tile t)
	{
		String s = resource.toString();
//		if (Arrays.asList(Tile.resourceDoubles[t.tileName.ordinal()]).contains(resource))
//		{
//			s += resource.toString();
//		}
		
		switch (resource)
		{
		case WW:
		case W:
			g.setColor(new Color(173, 255, 47));
			break;
		case RR:
		case R:
			g.setColor(new Color(0, 100, 0));
			break;
		case LL:
		case L:
			g.setColor(new Color(139, 69, 19));
			break;
		case MM:
		case M:
			g.setColor(Color.RED);
			break;
		case SS:
		case S:
			g.setColor(Color.WHITE);
			break;
		case CC:
		case C:
			g.setColor(Color.BLACK);
			break;
		case II:
		case I:
			g.setColor(Color.BLUE);
			break;
		case OO:
		case O:
			g.setColor(new Color(255, 140, 0));
			break;
		case GG:
		case G:
			g.setColor(Color.YELLOW);
			break;
		case N:
			s = "";
			break;
		}
		
		return s;
	}
	
	private ImageIcon setSizedIcon(int width, int height, ImageIcon thisIcon, Tile cur)
	{
		Image img = thisIcon.getImage();
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		
		if (cur.isUncovered())
		{
			double fontCoefficient = .2;
			g.setFont(new Font("TimesRoman", Font.BOLD, (int)Math.round(((width + height) / 2) * fontCoefficient)));	//makes font proportional to both width and height
			g.setColor(Color.RED);
			
			double xLocation = .275;	//x's are .275 of width/ height away from edge
			
			int offset = (int)Math.round(fontCoefficient * 60);		//offsets the letters so they are in the center
			
			//fills clockwise from upper leftmost point
			String curString = getStringAndSetColor(g, cur.resources[0], cur);
			
			//attempt at offsetting double resources.  Decided it wasn't worth it.
//			g.drawString(curString, (int)Math.round(width * xLocation - offset * ((curString.length() == 2) ? 2 : 1)), (int)Math.round(height * xLocation) + offset);
			g.drawString(curString, (int)Math.round(width * xLocation) - offset, (int)Math.round(height * xLocation) + offset);

			
			curString = getStringAndSetColor(g, cur.resources[1], cur);
			g.drawString(curString, width - (int)Math.round(width * xLocation) - offset, (int)Math.round(height * xLocation) + offset);

			curString = getStringAndSetColor(g, cur.resources[2], cur);
			g.drawString(curString, width - (int)Math.round(width * xLocation) - offset, height - (int)Math.round(height * xLocation) + offset);

			curString = getStringAndSetColor(g, cur.resources[3], cur);
			g.drawString(curString, (int)Math.round(width * xLocation) - offset, height - (int)Math.round(height * xLocation) + offset);
		}
		
		return new ImageIcon(bi);
	}
	
	private ImageIcon correspondingIcon(Tile currentTile)
	{
		ImageIcon icon = null;
		if (currentTile.isUncovered())
		{
			switch (currentTile.tileName)
			{
			case desert:
				icon = setSizedIcon(width, height, desertIcon, currentTile);
				break;
			case river:
				icon = setSizedIcon(width, height, riverIcon, currentTile);
				break;
			case lake:
				icon = setSizedIcon(width, height, lakeIcon, currentTile);
				break;
			case field:
				icon = setSizedIcon(width, height, fieldIcon, currentTile);
				break;
			case wooded:
				icon = setSizedIcon(width, height, woodedIcon, currentTile);
				break;
			case mountain:
				icon = setSizedIcon(width, height, mountainIcon, currentTile);
				break;
			}
		}
		else
		{
			icon = setSizedIcon(width, height, unknownIcon, currentTile);
		}
		return icon;
	}
	
	
	
	private void playGame()
	{
		getContentPane().removeAll();
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(tiles.grid.length, tiles.grid[0].length, 0, 0));
        
        width = Toolkit.getDefaultToolkit().getScreenSize().width / tiles.grid[0].length;
        height = Toolkit.getDefaultToolkit().getScreenSize().height / tiles.grid.length;
        
        labels = new JLabel[tiles.grid.length][tiles.grid[0].length];
        
        for (int i = 0; i < tiles.grid.length; ++i)
        {
        	for (int j = 0; j < tiles.grid[i].length; ++j)
        	{
        		Tile currentTile = tiles.grid[i][j];
        		ImageIcon icon = null;
	        	icon = correspondingIcon(currentTile);
        		
        	    JLabel label = new JLabel(icon);
        	    labels[i][j] = label;
        	    
        	    label.setSize(width, height);
        	    label.addMouseListener(new myListener(currentTile, label));
        	    panel.add(label);
        	}
        }
        
        panel.addKeyListener(new KeyAdapter()
        {
        	public void keyTyped(KeyEvent e)
        	{
        		//no idea how this is supposed to work, but ctrl + r is the 18 of the char
        		if ((int)e.getKeyChar() == 18 && e.isControlDown())
        		{
        			for (int i = 0; i < tiles.grid.length; ++i)
        			{
        				for (int j = 0; j < tiles.grid[i].length; ++j)
        				{
        					if (!tiles.grid[i][j].isUncovered())
        					{
	        					tiles.grid[i][j].uncover();
	        					labels[i][j].setIcon(correspondingIcon(tiles.grid[i][j]));
        					}
        				}
        			}
					tiles.updateGrid();
        		}
        		else if ((int)e.getKeyChar() == 8 && e.isControlDown())	//control + h
        		{
        			for (int i = 0; i < tiles.grid.length; ++i)
        			{
        				for (int j = 0; j < tiles.grid[i].length; ++j)
        				{
        					if (tiles.grid[i][j].isUncovered())
        					{
	        					tiles.grid[i][j].cover();
        					}
        				}
        			}
        			tiles.uncoverStart();
        			for (int i = 0; i < tiles.grid.length; ++i)
        			{
        				for (int j = 0; j < tiles.grid[i].length; ++j)
        				{
        					labels[i][j].setIcon(correspondingIcon(tiles.grid[i][j]));
        				}
        			}
        			tiles.updateGrid();
        		}
        		else if ((int)e.getKeyChar() == 14 && e.isControlDown())	//control + n
        		{
        			tiles.uncoverRandomSquares(2);
        			for (int i = 0; i < tiles.grid.length; ++i)
        			{
        				for (int j = 0; j < tiles.grid[i].length; ++j)
        				{
        					labels[i][j].setIcon(correspondingIcon(tiles.grid[i][j]));
        				}
        			}
        			tiles.updateGrid();
        		}
//        		else
//        		{
//        			System.out.println((int)e.getKeyChar());
//        		}
        	}
        });
        
        setLayout(new GridLayout(1, 1, 0, 0));
        add(panel);
        panel.grabFocus();
        repaint();
        pack();
	}
	
	private class myListener extends MouseAdapter
	{
		Tile thisTile;
		JLabel thisLabel;
		
		public myListener(Tile me, JLabel in)
		{
			this.thisTile = me;
			thisLabel = in;
		}
		
		public void mouseClicked(MouseEvent me) 
		{
			if (SwingUtilities.isLeftMouseButton(me))
			{
				if (!thisTile.isUncovered())
				{
					thisTile.uncover();
			        thisLabel.setIcon(correspondingIcon(thisTile));
					tiles.updateGrid();
				}
			}
			else if (SwingUtilities.isRightMouseButton(me))
			{
				if (thisTile.isUncovered())
				{
					thisTile.cover();
					thisLabel.setIcon(correspondingIcon(thisTile));
					tiles.updateGrid();
				}
			}
			else if (SwingUtilities.isMiddleMouseButton(me))
			{
				System.exit(0);
			}
		}
	}
	
	public UI()
	{
        super("Game of Power");
		setUndecorated(true);
		setPreferredSize( new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        getContentPane().setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 200));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        JLabel title = new JLabel();
        title.setText("Welcome to The Game of Power Desktop Application");
        title.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N

        
        panel.setLayout(new GridLayout(0, 1, 0, 20));
        
        combo = new JComboBox();
        for (int i = minPlayers; i <= maxPlayers; ++i)
        {
        	combo.addItem(makeObj(i + " Players"));
        }
        
        
        JButton button1 = new JButton();
        button1.setText("Continue Previous Game");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	tiles = new TileGenerator();
                tiles.continueGame();
                playGame();
            }
        });
        
        JButton button2 = new JButton();
        button2.setText("Start New Game");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	
            	int players = combo.getSelectedIndex() + minPlayers;
            	
                
                tiles = new TileGenerator();
                tiles.newGame(players);
                playGame();
            }
        });
        
        panel.add(title);

        panel.add(button2);
        panel.add(button1);
        getContentPane().add(panel);
        getContentPane().add(combo);
        pack();
	}

	private Object makeObj(final String item)  {
	     return new Object() { public String toString() { return item; } };
	   }
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new UI().setVisible(true);
            }
        });
	}
}
