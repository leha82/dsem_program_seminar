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
				
		start_x = -1;
		start_y = -1;
		esc_x = -1;
		esc_y = -1; 
	}

	public static void main(String[] args) {
		RMC_sh rmc = new RMC_sh("RMCSHsmpl100", 100, 100);
		rmc.createMap();
		rmc.printMap();
		rmc.makeMapFile();
	}
	
	private void setStartPoint() {
		for(int i=0; i<x_size || i<y_size; i++) {
			for (int j=0; j<=i; j++) {
				if (i<x_size && j<y_size && map[j][i]==0) {
					start_x = i;
					start_y = j;
					return;
				} else if (j<x_size && i<y_size && map[i][j]==0) {
					start_x = j;
					start_y = i;
					return;
				}
			}
		}
	}

	private void setEscPoint() {
		for(int i=1; i<=x_size || i<=y_size; i++) {
			for (int j=1; j<=i; j++) {
				if (x_size-i>0 && y_size-j>0 && map[y_size-j][x_size-i]==0) {
					esc_x = x_size-i;
					esc_y = y_size-j;
					return;
				} else if (x_size-j>0 && y_size-i>0 && map[y_size-i][x_size-j]==0) {
					esc_x = x_size-j;
					esc_y = y_size-i;
					return;
				}
			}
		}
	}
	
	@Override
	public void createMap() {
		for (int i=0; i<y_size; i++) {
			for (int j=0; j<x_size; j++) {
				map[i][j] = 1;
			}
		}
		
//		dfsGen(tmp_x, tmp_y, -1);
		simpleDFSGen(tmp_x, tmp_y);
		setStartPoint();
		setEscPoint();
		
		System.out.println("Start : " + start_x + "," + start_y);
		System.out.println("Escape : " + esc_x + "," + esc_y);
	}
	
	private void simpleDFSGen(int x, int y) {
		int[] dirList = randomDirList();
		
		map[y][x] = 0;
		
		for (int i=0; i<dirList.length; i++) {
			if (checkTiles(x+dir_x[dirList[i]], y+dir_y[dirList[i]], dirList[i])) {
				simpleDFSGen(x+dir_x[dirList[i]], y+dir_y[dirList[i]]);
			}
		}
	}
	
	
//	private int dep = 0;
//	Scanner sc = new Scanner(System.in);
	
	private void dfsGen(int x, int y, int dir) {
		int[] dirList;

		map[y][x] = 0;

//		if (dep%10 == 0) {
//			printMap();
//			System.out.println(x + "," + y);
//			String str = sc.nextLine();
//		}
//		dep++;

		if (dir != -1 && checkTileswithCorner(x+dir_x[dir], y+dir_y[dir], dir) ) {
			dfsGen(x+dir_x[dir], y+dir_y[dir], -1);
		}

		dirList = randomDirList();

		for (int i=0; i<dirList.length; i++) {
			if (checkTileswithCorner(x+dir_x[dirList[i]], y+dir_y[dirList[i]], dirList[i])) {
				dfsGen(x+dir_x[dirList[i]], y+dir_y[dirList[i]], dirList[i]);
			}
		}
		
//		dep--;
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
	
	private boolean checkTiles(int x, int y, int dir) {
		// 현재 위치가 범위 밖이면 false
		if (x<0 || x>x_size-1 || y<0 || y>y_size-1) 
			return false;
		
		for (int i=0; i<dir_x.length; i++) {
			if (!((dir+2)%4 == i)) {
				if (x+dir_x[i]>=0 && x+dir_x[i]<=x_size-1 &&
					y+dir_y[i]>=0 && y+dir_y[i]<=y_size-1) {
					if (map[y+dir_y[i]][x+dir_x[i]]==0)
						return false;
				}
			}
		}
				
		return true;
	}
	
	private boolean checkTileswithCorner(int x, int y, int dir) {
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
