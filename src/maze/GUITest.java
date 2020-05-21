package maze;

import java.awt.*;
import javax.swing.*;

public class GUITest extends JFrame {
	JPanel mainPanel;
	JPanel mapPanel;
	JPanel infoPanel;
	JButton btnNext;
	JLabel lbCount;
	JLabel wall;
	JLabel way;
	JLabel goal;
	JLabel mouse;
	
	Maze maze;
	
	public GUITest() {
		super("GUITest");
	}
	
	public void initWindow(int map[][]) {
		mapPanel = new JPanel();
		mapPanel.setLayout(new GridLayout(1,4));
		
		wall = new JLabel(new ImageIcon("/res/wall.bmp"));
		way = new JLabel(new ImageIcon("way.bmp"));
		goal = new JLabel(new ImageIcon("./res/goal.bmp"));
		mouse = new JLabel(new ImageIcon("./mouse.bmp"));
		
		System.out.println(wall);
		System.out.println(way);
		System.out.println(goal);
		System.out.println(mouse);
		
		
		mapPanel.add(wall);
		mapPanel.add(way);
		mapPanel.add(goal);
		mapPanel.add(mouse);
		
		
		btnNext = new JButton("다음 이동");
		lbCount = new JLabel("이동횟수 : 0");
		infoPanel = new JPanel();
		infoPanel.add(btnNext);
		infoPanel.add(lbCount);
		
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(mapPanel,"Center");
		mainPanel.add(infoPanel,"South");
		
		add(mainPanel);
		
		setSize (800, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		GUITest gt = new GUITest();
		int[][] map = { {0, 0, 0, 1, 1, 1},
						{1, 1, 0, 0, 0, 0},
						{1, 1, 1, 1, 0, 1},
						{1, 1, 1, 1, 0, 0}};
		gt.initWindow(map);
		
	}
}
