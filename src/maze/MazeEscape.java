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
	private int curr_x, curr_y;
	private int esc_x, esc_y;

	public MazeEscape() {
		this.count = 0;
		this.mapfile = "maps/testmap2.txt";
		this.miceList = new ArrayList<String>();
	}

	public void loadMap() {
		this.maze = new Maze(mapfile);
		this.start_x = maze.getStart_x();
		this.start_y = maze.getStart_y();
		this.esc_x = maze.getEsc_x();
		this.esc_y = maze.getEsc_y();
		
		this.curr_x = this.start_x;
		this.curr_y = this.start_y;

	}

	public void loadMice() {
		miceList.add("RandomMouse");

		this.mouse = new Mouse_sojin();
	}


	public void printMap(int x, int y) {
		// 지도를 출력하고 엔터하나 입력받도록 한다 #1
		int[][] map = maze.getMap();
		String goal = "▶";
		String road = "·";
		String block = "■";
		String mouse = "§";
		
		for (int i = 0; i < map.length; i++) {

			for (int j = 0; j < map[i].length; j++) {
				if (x == j && y == i) {
					System.out.print(mouse);
				} else if (esc_x == j && esc_y == i) {
					System.out.print(goal);
				} else if (map[i][j] == 0) {
					System.out.print(road);
				} else if (map[i][j] == 1) {
					System.out.print(block);
				} 
				
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println("count : " + count);

	}

	public void play() {
		Scanner sc = new Scanner(System.in);
		this.count = 0;

		while ((this.curr_x != this.esc_x) || (this.curr_y != this.esc_y)) {
			this.printMap(this.curr_x, this.curr_y);
			sc.nextLine();

			int dir = mouse.nextMove(this.curr_x, this.curr_y, maze.getArea(this.curr_x, this.curr_y));
		
			if (dir==1 && curr_y > 0) { // check up
				if (maze.getMapPoint(curr_x,curr_y-1)==0)
					curr_y--;	
			} else if (dir==2 && curr_x < maze.getWidth()-1) { // check right
				if (maze.getMapPoint(curr_x+1,curr_y)==0)
					curr_x++;
			} else if (dir==3 && curr_y < maze.getHeight()-1) { // check down
				if (maze.getMapPoint(curr_x,curr_y+1)==0)
					curr_y++;	
			} else if (dir==4 && curr_x > 0) {	// check left
				if (maze.getMapPoint(curr_x-1,curr_y)==0)
					curr_x--;
			}

			count++;
		}
		
		System.out.println("Escape Success!!");
		System.out.println("Total Moves : " + count);
		sc.close();
	}

	public static void main(String[] args) {
		MazeEscape me = new MazeEscape();
		me.loadMap();
		me.loadMice();
		me.play();
	}
}
