package maze.mazemaker;

import java.util.Scanner;

// This is DFS based Map Generation algorithm

public class RMC_sh extends RandomMapCreator {
	private int tmp_x;
	private int tmp_y;
	
	// dir의 순서는 0 : 위, 1 : 오른쪽, 2 : 아래, 3 : 왼쪽
	private int[] dir_x = {0, 1, 0, -1};
	private int[] dir_y = {-1, 0, 1, 0};
	
	public RMC_sh(String mapName, int width, int height) {
		super(mapName, width, height);

		int dir = ((int)(Math.random()*100) % dir_x.length);
		switch(dir) {
		case 0:
			tmp_x = ((int)(Math.random()*100) % width);
			tmp_y = 0;
			break;
		case 1:
			tmp_x = width-1;
			tmp_y = ((int)(Math.random()*100) % height);
			break;
		case 2:
			tmp_x = ((int)(Math.random()*100) % width);
			tmp_y = height-1;
			break;
		case 3:
			tmp_x = 0;
			tmp_y = ((int)(Math.random()*100) % height);
			break;
		}
				
		start_x = tmp_x;
		start_y = tmp_y;
		esc_x = width - 1;
		esc_y = height - 1; 
	}

	public static void main(String[] args) {
		RMC_sh rmc = new RMC_sh("RMCSH01", 100, 100);
		rmc.createMap();
		rmc.printMap();
		rmc.makeMapFile();
	}
	
	@Override
	public void createMap() {
		for (int i=0; i<y_size; i++) {
			for (int j=0; j<x_size; j++) {
				map[i][j] = 1;
			}
		}
		
		dfsGen(start_x, start_y);
	}
	
	private int dep = 0;
	Scanner sc = new Scanner(System.in);
	
	private void dfsGen(int x, int y) {
		int[] dirList = randomDirList();
		
		map[y][x] = 0;

//		if (dep%10 == 0) {
//			printMap();
//			System.out.println(x + "," + y);
//			String str = sc.nextLine();
//		}
//		dep++;
		
		
		for (int i=0; i<dirList.length; i++) {
			if (checkMap(x+dir_x[dirList[i]], y+dir_y[dirList[i]], dirList[i])) {
				dfsGen(x+dir_x[dirList[i]], y+dir_y[dirList[i]]);
			} 
		}
		
		dep--;
	}
	
	private int[] randomDirList() {
		int[] list = new int[4];
		boolean[] check = new boolean[list.length];
		for (int i=0; i < list.length; i++) {
			check[i] = false;
		}
		
		for (int i=0; i<list.length; i++) {
			int rnd = (int)(Math.random()*100) % list.length;
			while(check[rnd]) {
				rnd = (rnd+1) % list.length;
			}

			list[i] = rnd;
			check[rnd] = true;
		}
		
		return list;
	}
	
	private boolean checkMap(int x, int y, int dir) {
		// 현재 위치가 범위 밖이면 false
		if (x<0 || x>x_size-1 || y<0 || y>y_size-1) 
			return false;
		
		// 방향에 따라 6칸이 모두 벽이어야 함
		for (int i=-1; i<=1; i++) {
			for (int j=-1; j<=1; j++) {
				if ((y+i >= 0) && (y+i <= y_size-1) &&
					(x+j >= 0) && (x+j <= x_size-1)) {
					if ((dir==0) && (i!=1) && map[y+i][x+j]==0) {
						return false;
					} else if ((dir==1) && (j!=-1) && map[y+i][x+j]==0) {
						return false;
					} else if ((dir==2) && (i!=-1) && map[y+i][x+j]==0) {
						return false;
					} else if ((dir==3) && (j!=1) && map[y+i][x+j]==0) {
						return false;
					}
				}
			}
		}
				
		return true;
	}
}
