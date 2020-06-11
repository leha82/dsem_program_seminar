package mice;

import java.util.Stack;

public class RandomMouse_sojin extends Mouse {
	public RandomMouse_sojin() {

	}
	static int[][] maze = new int[10][10];
//	static int[][] maze = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
//			{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
	// Mouse가 다음으로 움직일 방향을 정한다.
	// 0: 제자리
	// 1: 위쪽
	// 2: 오른쪽
	// 3: 아래쪽
	// 4: 왼쪽
	int[] index_y = { 0, 1, 2, 1 };
	int[] index_x = { 1, 2, 1, 0 };

	Stack<Integer> stack_x = new Stack<Integer>();
	Stack<Integer> stack_y = new Stack<Integer>();

	
	public void print() {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				System.out.print(maze[i][j] + " ");
			}
			System.out.println();
		}
	}

	public int nextMove(int x, int y, int[][] smap) {
		int dir = 0;
		int path = 1;
		if (x == 0 && y == 0) {
			stack_x.push(0);
			stack_y.push(0);
		}
		int pre_x = stack_x.peek();
		int pre_y = stack_y.peek();
		System.out.println("pre x: " + pre_x);
		System.out.println("pre y: " + pre_y);
		System.out.println(maze[y][x]);
		System.out.println(maze[pre_y][pre_x]);
		for (int i = 0; i < 4; i++) {
			if (smap[index_y[i]][index_x[i]] == 0) {
				if (maze[x][y] == 0) {
					if (index_y[i] == 0 && index_x[i] == 1) {
						stack_x.push(x);
						stack_y.push(y);
						if (maze[pre_y][pre_x] != 0) {
							maze[y][x] = path;
							dir = 3;
							break;
						} else {
							maze[y][x] = 2;
							dir = 1;
							break;
						}
					} else if (index_y[i] == 1 && index_x[i] == 2) {
						stack_x.push(x);
						stack_y.push(y);
						maze[y][x] = path;
						dir = 2;
						break;
					} else if (index_y[i] == 2 && index_x[i] == 1) {
						stack_x.push(x);
						stack_y.push(y);
						
						maze[y][x] = path;
						dir = 3;
						break;
					} else if (index_y[i] == 1 && index_x[i] == 0) {
						stack_x.push(x);
						stack_y.push(y);
						maze[y][x] = path;
						dir = 4;
						break;
					}
				}
			}
		}
		return dir;
	}
}
