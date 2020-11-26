package mice;

import boot.*;
import java.util.*;

public class mouse_YBH extends MouseChallenge {
	private int[][] map;
	private int start_x, start_y;
	private int x, y;
	private int dir;
	private Stack<Integer> dirStack;
	private int searchType;

	Scanner sc = new Scanner(System.in);

	private boolean isShortestPath;
	private boolean isPathFinish; // ���� ��� ��Ʈ�� ã�� �ִܰŸ����� �������� true

	public mouse_YBH() {
		super();

		start_x = 1;
		start_y = 1;
		x = start_x;
		y = start_y;

		searchType = 0;
		dir = 1;
		isShortestPath = false;
		isPathFinish = false;

		map = new int[200][200];

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = -1;
			}
		}
	}

	public void initMouse() {
		super.initMouse();
		x = start_x;
		y = start_y;

		searchType++;
		dir = 1;
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

	public void printMap(int[][] pmap, int mx, int my) {
		for (int i = 0; i < pmap.length; i++) {
			for (int j = 0; j < pmap[i].length; j++) {
				String tile = "- ";
				if (mx == j && my == i) {
					tile = "m ";
				} else if (pmap[i][j] == 0) {
					tile = "  ";
				} else if (pmap[i][j] == 1) {
					tile = "�� ";
				} else if (pmap[i][j] == 2) {
					tile = "�� ";
				}

				System.out.print(tile);
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printCheckMap(int[][] pmap) {
		for (int i = 0; i < pmap.length; i++) {
			for (int j = 0; j < pmap[i].length; j++) {
				System.out.printf("%2d ", pmap[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printSMap(int[][] smap) {
		for (int i = 0; i < smap.length; i++) {
			for (int j = 0; j < smap[i].length; j++) {
				System.out.print(smap[i][j]);
			}
			System.out.println();
		}
		System.out.println();
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

	public boolean discoverDone() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0 || map[i][j] == 2) {
					if (map[i - 1][j] == -1 || map[i][j - 1] == -1 || map[i + 1][j] == -1 || map[i][j + 1] == -1) {
//						System.out.println("dd : " + j + "," + i);
						return false;
					}
				}
			}
		}

		// ��� ������ Ž���� �����ٸ�, �ִܰŸ��� ã�� dir�� queue�� ������ �Ѵ�.
		System.out.println("Map Discovery is over. Finding Shortest Path...");
		this.findShortestPath();

		return true;
	}

	public int nextSearch(int[][] smap) {
		int[] rp_X = { 0, 2, 1, 0, 1 }; // right position by current dir
		int[] rp_Y = { 0, 1, 2, 1, 0 };

		int[] sp_X = { 0, 1, 2, 1, 0 }; // straight position by current dir
		int[] sp_Y = { 0, 0, 1, 2, 1 };

		int[] lp_X = { 0, 0, 1, 2, 2 };
		int[] lp_Y = { 0, 1, 0, 1, 1 };

		int[] rt_Dir = { 0, 2, 3, 4, 1 };
		int[] lt_Dir = { 0, 4, 1, 2, 3 };
		int[] ut_Dir = { 0, 3, 4, 1, 2 };

		this.checkMoved();

//		this.printSMap(smap);
		this.overlabMap(smap);
//		this.printMap(map,x,y);

		if (discoverDone()) {
			dir = -1;
			return dir;
		}
		switch (searchType & 3) {
		case (1): {
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
		case (2): {
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
		case(3):{
			// ������ �ִ� �� �� ũ�⸦ �����Ͽ� �� �ȿ� �ִ� ���� �� -1�� ���� ��ǥ�� ã��  �迭 ���� ���� ���� �� �ϳ� �ϳ� bfs�� �ִܰ�� ���� �� �ű⸦ Ž���ؼ� ä��� 
			
		}
		}
		return dir;
	}

	// Ž���� ������ ���������� �������� �ִܰŸ��� ã�� path�� �����.
	private void findShortestPath() {
		// map�� ����
		int[][] traceMap = new int[map.length][map[0].length];

		Queue<Integer> q_x = new LinkedList<Integer>();
		Queue<Integer> q_y = new LinkedList<Integer>();

		int width = map[0].length;
		int height = map.length;

		int goal_x = 0;
		int goal_y = 0;

		int curr_x = start_x;
		int curr_y = start_y;

		// bfs�� �̿��Ͽ� goal���� ����
		q_x.add(curr_x);
		q_y.add(curr_y);
		traceMap[curr_y][curr_x] = 1;

		while (!q_x.isEmpty()) {
			curr_x = q_x.poll();
			curr_y = q_y.poll();

			int index = traceMap[curr_y][curr_x];

			// �������� goal�� �����ϸ� �ִܰŸ��� ã�� ����
			if (map[curr_y][curr_x] == 2) {
				isPathFinish = true;
				goal_x = curr_x;
				goal_y = curr_y;
				break;
			}

			if (curr_y > 0 && map[curr_y - 1][curr_x] != 1 && traceMap[curr_y - 1][curr_x] == 0) {
				q_x.add(curr_x);
				q_y.add(curr_y - 1);
				traceMap[curr_y - 1][curr_x] = index + 1;
			}

			if (curr_x < width - 1 && map[curr_y][curr_x + 1] != 1 && traceMap[curr_y][curr_x + 1] == 0) {
				q_x.add(curr_x + 1);
				q_y.add(curr_y);
				traceMap[curr_y][curr_x + 1] = index + 1;
			}

			if (curr_y < height - 1 && map[curr_y + 1][curr_x] != 1 && traceMap[curr_y + 1][curr_x] == 0) {
				q_x.add(curr_x);
				q_y.add(curr_y + 1);
				traceMap[curr_y + 1][curr_x] = index + 1;
			}

			if (curr_x > 0 && map[curr_y][curr_x - 1] != 1 && traceMap[curr_y][curr_x - 1] == 0) {
				q_x.add(curr_x - 1);
				q_y.add(curr_y);
				traceMap[curr_y][curr_x - 1] = index + 1;
			}

//			printMap(map, curr_x, curr_y);
//			printCheckMap(traceMap);
//			sc.nextLine();
		}

		System.out.println("Goal is found.");
		printCheckMap(traceMap);

		// traceMap�� �̿��Ͽ� ���� ���������� ���� ������ �����.
		dirStack = new Stack<Integer>();

		while (curr_x != start_x || curr_y != start_y) {
			int index = traceMap[curr_y][curr_x];

			if (curr_y > 0 && traceMap[curr_y - 1][curr_x] == index - 1) {
				dirStack.push(3);
				curr_y--;
			} else if (curr_x < width - 1 && traceMap[curr_y][curr_x + 1] == index - 1) {
				dirStack.push(4);
				curr_x++;
			} else if (curr_y < height - 1 && traceMap[curr_y + 1][curr_x] == index - 1) {
				dirStack.push(1);
				curr_y++;
			} else if (curr_x > 0 && traceMap[curr_y][curr_x - 1] == index - 1) {
				dirStack.push(2);
				curr_x--;
			}
		}

		System.out.println("Start : " + start_x + "," + start_y);
		System.out.println("curr : " + curr_x + "," + curr_y);

		System.out.print("path: ");
		for (int i = dirStack.size() - 1; i >= 0; i--) {
			System.out.print(dirStack.get(i) + "->");
		}
		System.out.println();
	}

	// ���� ����� ������ ����
	public int nextMove(int[][] smap) {
		if (isShortestPath) {
			return dirStack.pop();
		} else if (isPathFinish) {
			return dirStack.pop();
		}

		return dir;
	}

	@Override
	public void printClassName() {
		System.out.println("Mouse_SH");
	}
}
