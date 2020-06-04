package mice;

import maze.Maze;
import maze.MazeEscape;

public class RandomMouse_woolin extends Mouse {
	private Maze maze;
	private MazeEscape me;
	
	public int[][] tmap = new int[10][10];
	public int path = 2;
	public RandomMouse_woolin() {
		
	}
	
	// Mouse�� �������� ������ ������ ���Ѵ�.
	// 0: ���ڸ�
	// 1: ����
	// 2: ������
	// 3: �Ʒ���
	// 4: ����
	public int nextMove(int x, int y, int[][] smap) {
		int[] dir_arr = {1, 2, 3, 4};   // �������� ������ ���� �迭
		int[] index_y = {-1, 0, 1, 0};  
		int[] index_x = {0, 1, 0, -1};
		int[] index = {-1, 0, 1};       
		int dir = 0;
		
		// tmap�� �� �׷�������
		for (int i=0;i<smap.length;i++) {
			for (int j=0;j<smap[0].length;j++) {
				if (smap[i][j] != -1) {
					if (tmap[y+index[i]][x+index[j]] == path) {
//						tmap[y+index[i]][x+index[j]] = tmap[y+index[i]][x+index[j]];
					} else {
						tmap[y+index[i]][x+index[j]] = smap[i][j];
					}
				}
			}
		}

		// dir ������ ���� �� �� �ִ� ��(0)�� ��� �ش� ���� ����
		for (int i=0;i<dir_arr.length;i++) {
			if (smap[1+index_y[i]][1+index_x[i]] == 0) {
				if (tmap[y+index_y[i]][x+index_x[i]] == 0) {
					dir = i+1;
					tmap[y][x] = path;
					print_tmap();
					break;
				} else if (tmap[y+index_y[i]][x+index_x[i]] == path) {
					dir = i+1;
					path++;
					tmap[y][x] = path;
					print_tmap();
				}
			}
		}
		return dir;
	}

	public void print_tmap() {
		System.out.println("[tmap]");
		for (int i=0;i<tmap.length;i++) {
			for (int j=0;j<tmap[0].length;j++) {
				System.out.print(tmap[i][j] + " ");
			}
			System.out.println();
		}
	}
}
