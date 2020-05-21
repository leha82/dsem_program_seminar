package maze;

import java.util.*;

import mice.*;

public class MazeEscape {
	private int count;
	private String mapfile;
	private Maze maze;
	private ArrayList<String> miceList;
	private Mouse mouse;
	private int start_x, start_y;
	private int esc_x, esc_y;
	
	public MazeEscape() {
		this.count = 0;
		this.mapfile = "maps/testmap.txt";
	}
	
	public void loadMap() {
		this.maze = new Maze(mapfile);
		this.start_x = 0;
		this.start_y = 0;
		this.esc_x = 6;
		this.esc_y = 6;
	}
	
	public void loadMice() {
		miceList.add("RandomMouse");
		this.mouse = new RandomMouse();
	}
	
	public void printMap() {
		// 지도를 출력하고 엔터하나 입력받도록 한다 #1
		
	}
	
	public void play() {
		int x = this.start_x;
		int y = this.start_y;
		while ((x != this.esc_x) || (y != this.esc_y)) {
			this.printMap();
			mouse.nextMove(x, y, maze.getArea(x, y) );
		}
	}
	
	public static void main(String[] args) {
		MazeEscape me = new MazeEscape();
		me.loadMap();
		me.loadMice();
		me.play();
	}
}
