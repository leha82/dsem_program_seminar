package mice;

import java.util.Stack;

<<<<<<< HEAD
public class Sunyoung_Mouse extends Mouse{
	int[][] new_maze = new int[7][7];
=======
public class Sunyoung_Mouse extends Mouse {
	int[][] new_maze = new int[10][10];
>>>>>>> refs/remotes/origin/master

	public Sunyoung_Mouse() {

	}

	// Mouse가 다음으로 움직일 방향을 정한다.
	// 0: 제자리
	// 1: 위쪽
	// 2: 오른쪽
	// 3: 아래쪽
	// 4: 왼쪽
	public int nextMove(int x, int y, int[][] smap) {
		int dir = 0;

		if (smap[1][2] == 0) { // 오른쪽
			if (new_maze[y][x] == 0) {
				new_maze[y][x] = 1;
				dir = 2;
			} else if (new_maze[y][x] == 1 && smap[2][1] == 0) {
				new_maze[y][x] = 2;
				dir = 3;
			} else if (new_maze[y][x] == 1 && smap[1][2] == 0) {
				new_maze[y][x] = 2;
				dir = 4;
			} else if (new_maze[y][x] == 1 && smap[0][1] == 0) {
				new_maze[y][x] = 2;
				dir = 1;
			}
		} else if (smap[1][2] != 0 && smap[2][1] == 0) { // 아래쪽
			if (new_maze[y][x] == 0) {// 한번도 안간곳이라면 new_maze를 1로 바꾸로 아래로
				new_maze[y][x] = 1;
				dir = 3;
			} else if (new_maze[y][x] == 1 && smap[1][0] == 0) {// 이미 간곳이고 왼쪽로 갈 수 있다면 왼쪽으로
				new_maze[y][x] = 2;
				dir = 4;
			} else if (new_maze[y][x] == 1 && smap[0][1] == 0) {// 이미 간곳이고 위쪽에 갈 수 있으면 위로
				new_maze[y][x] = 2;
				dir = 1;
			}

		} else if (smap[1][2] != 0 && smap[2][1] != 0 && smap[1][0] == 0) {// 왼쪽
			if (new_maze[y][x] == 0) {
				new_maze[y][x] = 1;
				dir = 4;
			} else if (new_maze[y][x] == 1 && smap[0][1] == 0) {// 이미 간곳이고 위쪽에 갈 수 있으면 위로
				new_maze[y][x] = 2;
				dir = 1;
			}

		} else if (smap[1][2] != 0 && smap[2][1] != 0 && smap[1][0] != 0 && smap[0][1] == 0) {// 위쪽
			if (new_maze[y][x] == 0) {
				new_maze[y][x] = 1;
				dir = 1;
			}

		}
		
		
		
		
		

		for (int i = 0; i < new_maze.length; i++) {
			for (int j = 0; j < new_maze[i].length; j++) {
				System.out.print(new_maze[i][j] + " ");
			}
			System.out.println();
		}
		
		for (int i = 0; i < smap.length; i++) {
			for (int j = 0; j < smap[i].length; j++) {
				System.out.print(smap[i][j] + " ");
			}
			System.out.println();
		}

		return dir;
	}
}
