package mice;

import boot.MouseChallenge;
import maze.challengemode.ChallengeInfo;

public class Mouse_LKJ extends MouseChallenge {
	ChallengeInfo da = new ChallengeInfo();
	private int[][] map;
	private int dir;
	private int searchCount;
	private int start_x, start_y;
	private int x, y;

	public Mouse_LKJ() {
		this.map = new int [12][12]; 
		this.dir = 1;
		this.searchCount = 0;
		start_x = 1;
		start_y = 1;
		x = start_x;
		y = start_y;
	}

	public void moveXY(int dir) {
		if (dir == 1 && y > 0) {
			if (map[y - 1][x] != 1)
				y--;
		} else if (dir == 2 && x < map[0].length - 1) {
			if (map[y][x + 1] != 1)
				x++;
		} else if (dir == 3 && y < map.length - 1) {
			if (map[y + 1][x] != 1)
				y++;
		} else if (dir == 4 && x > 0) {
			if (map[y][x - 1] != 1)
				x--;
		}
	}
	
	@Override
	public void printClassName() {
		System.out.println("MOUSE_LKJ");
	}
	
	public void printMap(int[][] pmap, int mx, int my) {
		for (int i = 0; i < pmap.length; i++) {
			for (int j = 0; j < pmap[i].length; j++) {
				String tile = "- ";
				if (mx == j && my == i) {
					tile = "H ";
				} else if (pmap[i][j] == 0) {
					tile = "0 ";
				} else if (pmap[i][j] == 1) {
					tile = "1 ";
				} else if (pmap[i][j] == 2) {
					tile = "D ";
				}

				System.out.print(tile);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void initMouse() {
		searchCount++;
	}
	
	public void overlabMap(int[][] smap) {
		for (int i = 0; i < smap.length; i++) {
			for (int j = 0; j < smap[i].length; j++) {
				int map_x = x - (smap[i].length / 2) + j;
				int map_y = y - (smap.length / 2) + i;
//				System.out.print(map_x + ", " + map_y + "\t");
				map[map_y][map_x] = smap[i][j];
			}
		}
//		System.out.println();
	}

	// Mouse�� �������� ������ ������ ���Ѵ�.
	// 0: ���ڸ�, 1: ����, 2: ������, 3: �Ʒ���, 4: ����, -1 : Ž�� ����

//  ==============Recursive Method===================	
//	const int PATHWAY = 0; // ������ �� �ִ� ��                               ->��� [final)
//  const int WALL = 1; // ��						   ->��� [final)
//  const int BLOCKED = 2; // ���ƿ��鼭 ���� ��                             ->��� [final)
//  const int PATH = 3; //�������鼭 ǥ���� ��                                   ->��� [final)
	

	
//	int findMapPath(int x, int y) {
//	    if(x<0 || y<0 || x>=N || y>=N) {
//	        return 0;
//	    }else if(map[x][y] != PATHWAY) return 0;
//	    else if(x == N-1 && y == N-1) {
//	        map[x][y] = PATH;
//	        return 1;
//	    }else {
//	        map[x][y] = PATH;
//	        if(findMapPath(x-1, y) || findMapPath(x, y+1)
//	            || findMapPath(x+1, y) || findMapPath(x, y-1)) {
//	            return 1;
//	        }
//	    map[x][y] = BLOCKED;
//	    return 0;
//	    }
//	}
// ====================================================
	
	public int nextMove(int[][] smap) {
		
		
		int[] rp_X = { 0, 2, 1, 0, 1 }; // right position by current dir
		int[] rp_Y = { 0, 1, 2, 1, 0 };

		int[] sp_X = { 0, 1, 2, 1, 0 }; // straight position by current dir
		int[] sp_Y = { 0, 0, 1, 2, 1 };

		int[] rt_Dir = { 0, 2, 3, 4, 1 };
		int[] ut_Dir = { 0, 3, 4, 1, 2 };

		this.checkMoved();
		// ���� ������ �������� �������� �˻�
		if (smap[rp_Y[dir]][rp_X[dir]] != 1) {
			// �������� ��������� dir�� ���������� ����
			dir = rt_Dir[dir];

		} else if (smap[sp_Y[dir]][sp_X[dir]] != 1) {
			// �������� ����������, ���� ������ �˻�
			// ���������� ��������� dir�� ���� ����
			

		} else {
			// ���������� ����������, dir�� �ݴ� ���� �ڷ� ���� -> nextMove();
			dir = ut_Dir[dir];
			dir = nextMove(smap);
		}
		
		return dir;
	}

	public int nextSearch(int[][] smap) {
		int[] rp_X = { 0, 2, 1, 0, 1 }; // right position by current dir
		int[] rp_Y = { 0, 1, 2, 1, 0 };
		
		int[] sp_X = { 0, 1, 2, 1, 0 }; // straight position by current dir
		int[] sp_Y = { 0, 0, 1, 2, 1 };
		
		int[] lp_X = { 0, 0, 1, 2, 1 }; // left position by current dir
		int[] lp_Y = { 0, 1, 0, 1, 2 };

		int[] rt_Dir = { 0, 2, 3, 4, 1 };	//������ ����
		int[] ut_Dir = { 0, 3, 4, 1, 2 };
		int[] lt_Dir = { 0, 4, 1, 2, 3 };	//���� ����


		this.overlabMap(smap);
		// ù��° Ž�� - ������ ��Ģ
		if (searchCount == 1) {
			// ���� ������ �������� �������� �˻�
			if (smap[rp_Y[dir]][rp_X[dir]] != 1) {
				// �������� ��������� dir�� ���������� ����
				dir = rt_Dir[dir];
				this.moveXY(dir);
			} else if (smap[sp_Y[dir]][sp_X[dir]] != 1) {
				// �������� ����������, ���� ������ �˻�
				// ���������� ��������� dir�� ���� ����
				this.moveXY(dir);
			} else {
				// ���������� ����������, dir�� �ݴ� ���� �ڷ� ���� -> nextMove();
				dir = ut_Dir[dir];
				dir = nextSearch(smap);
			}
		}

		
		// Mouse�� �������� ������ ������ ���Ѵ�.
		// �ι�° Ž�� - �޼� ��Ģ
		if (searchCount == 2) {
			// ���� ������ �������� ������ �˻�
			if (smap[lp_Y[dir]][lp_X[dir]] != 1) {
				// ������ ��������� dir�� �������� ����
				dir = lt_Dir[dir];
				this.moveXY(dir);
			} else if (smap[sp_Y[dir]][sp_X[dir]] != 1) {
				// ������ ����������, ���� ������ �˻�
				// ���������� ��������� dir�� ���� ����
				this.moveXY(dir);
			} else {
				// ���������� ����������, dir�� �ݴ� ���� �ڷ� ���� -> nextMove();
				dir = ut_Dir[dir];
				dir = nextSearch(smap);
				
			}
		}

		
		// ����° Ž�� - �ִ��� ����� ����
		if (searchCount == 3) {
			
		}
		
		printMap(map, x, y);
		if(searchCount==1)
			System.out.println("������ ��Ģ");
		else if(searchCount==2)
			System.out.println("�޼� ��Ģ");
		else if(searchCount==3)
			System.out.println("?? Ž��");
			

		return dir;
	}


}