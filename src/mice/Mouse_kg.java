package mice;

import java.util.Scanner;


public class Mouse_kg {
	private int count =0;
	private int node=1;
	public int[][] map= {{2,0,1,1,0},
						 {1,0,0,0,0},
						 {1,0,1,1,1},
						 {1,0,1,1,1},
						 {1,0,0,0,3}
						 //0 = 길, 1 = 벽, 2 = 출발, 3 = 도착
	};
	
	public void printMap() {
		String goal = "▶";
		String road = "·";
		String block = "■";
		String mouse = "§";
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] ==2) {
					System.out.print(mouse);
				} else if (map[i][j] == 3) {
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
	
	
	public void temp() {
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[i].length; j++) {
				if((map[i][j]!=1)&&(map[i][j]!=2)) {
					if(map[i][j]==3) {
						count++;
						printMap();
						System.out.println("--Escape!!--");
						break;
					}
					map[i][j]=2;
					count++;
					
				}
			}
		}
	}
	
	public void play(int map) {
		
	}
	public void shortestDistance(int[][] smap) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] =2;
			}
		}
	}
	
	public static void main(String[] args) {
		Mouse_kg ms = new Mouse_kg();

	}

}
