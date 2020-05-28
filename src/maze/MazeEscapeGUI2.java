package maze;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import mice.Mouse;
import mice.RandomMouse;

public class MazeEscapeGUI2 extends JFrame {
	int[][] initmap = { {0, 0, 0, 1, 1, 1},
					{1, 1, 0, 0, 0, 0},
					{1, 1, 1, 1, 0, 1},
					{1, 1, 1, 1, 0, 0}};

	
	private JPanel mainPanel;
	private JPanel mapPanel;
	private JPanel infoPanel;
	private JButton btnNext;
	private JLabel lbCount;
	private JLabel[][] mapLabels;
	
	private int count;
	private String mapfile;
	private Maze maze;
	private ArrayList<String> miceList;
	private Mouse mouse;
	private int start_x, start_y;
	private int curr_x, curr_y;
	private int esc_x, esc_y;
	
	private boolean finished;
	
	public MazeEscapeGUI2() {
		super("Maze Escape");
		this.count = 0;
		this.mapfile = "maps/testmap.txt";
		this.finished = false;
	}
	
	public void loadMap() {
//		this.maze = new Maze(mapfile);
		this.maze = new Maze(6, 4, initmap);
		
		this.start_x = 0;
		this.start_y = 0;
		this.curr_x = start_x;
		this.curr_y = start_y;
		this.esc_x = 5;
		this.esc_y = 3;
	}
	
	public void loadMice() {
		miceList = new ArrayList<String>();
		miceList.add("RandomMouse");
		this.mouse = new RandomMouse();
	}
	
	public void initWindow() {
		int[][] map = maze.getMap();
		
		mapPanel = new JPanel();
		mapPanel.setLayout(new GridBagLayout());

		mapLabels = new JLabel[map.length][];
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		for (int i=0; i<map.length; i++) {
			mapLabels[i] = new JLabel[map[i].length];
			
			for (int j=0; j<map[i].length; j++) {
				gbc.gridx=j;
				gbc.gridy=i;

				if (curr_x == j && curr_y == i) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/mouse.jpg"));
				} else if (esc_x == j && esc_y == i) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/goal.jpg"));
				} else if (map[i][j]==1) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/wall.jpg"));
				} else {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/way.jpg"));
				}
				mapPanel.add(mapLabels[i][j], gbc);
			}
		}

		btnNext = new JButton("´ÙÀ½ ÀÌµ¿");
		btnNext.addActionListener(new NextButtonListener());

		lbCount = new JLabel("ÀÌµ¿È½¼ö : " + count);
		infoPanel = new JPanel();
		infoPanel.add(btnNext);
		infoPanel.add(lbCount);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(mapPanel,"Center");
		mainPanel.add(infoPanel,"South");
		
		add(mainPanel);
		
		setSize (map[0].length * 60, map.length * 60 + 50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setWindow(int prev_x, int prev_y, int [][] map) {
		GridBagConstraints gbc = new GridBagConstraints();
		
		mapPanel.remove(mapLabels[prev_y][prev_x]);
		mapLabels[prev_y][prev_x] = new JLabel(new ImageIcon("res/way.jpg"));
		gbc.gridx=prev_x;
		gbc.gridy=prev_y;
		mapPanel.add(mapLabels[prev_y][prev_x], gbc);

		mapPanel.remove(mapLabels[curr_y][curr_x]);
		mapLabels[curr_y][curr_x] = new JLabel(new ImageIcon("res/mouse.jpg"));
		gbc.gridx=curr_x;
		gbc.gridy=curr_y;
		mapPanel.add(mapLabels[curr_y][curr_x], gbc);

		lbCount.setText("ÀÌµ¿È½¼ö : " + count);
		
		revalidate();
		repaint();
	}
	
	public void play() {
		int[][] map = maze.getMap();
		int prev_x = curr_x;
		int prev_y = curr_y;
		
		int dir = mouse.nextMove(curr_x, curr_y, maze.getArea(curr_x, curr_y) );
		if (dir==1 && curr_y > 0) {
			if (map[curr_y-1][curr_x]==0)
				curr_y--;	
		} else if (dir==2 && curr_x < maze.getWidth()-1) {
			if (map[curr_y][curr_x+1]==0)
				curr_x++;
		} else if (dir==3 && curr_y < maze.getHeight()-1) {
			if (map[curr_y+1][curr_x]==0)
				curr_y++;	
		} else if (dir==4 && curr_x > 0) {
			if (map[curr_y][curr_x-1]==0)
				curr_x--;
		}
		
		count++;
		this.setWindow(prev_x, prev_y, map); 
		

		if ((curr_x == this.esc_x) && (curr_y == this.esc_y)) {
			JOptionPane.showMessageDialog(null, "Å»Ãâ¿¡ ¼º°øÇß½À´Ï´Ù. ÃÑ ÀÌµ¿ È½¼ö : " + count);
			finished = true;
		}
	}
	
	public static void main(String[] args) {
		MazeEscapeGUI2 me = new MazeEscapeGUI2();
		me.loadMap();
		me.loadMice();
		me.initWindow();
	}
	
	class NextButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!finished) {
				if (e.getSource() == btnNext)
					play();
			}
		}
	}
}