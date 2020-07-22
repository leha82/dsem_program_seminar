package maze.mazemaker;

import maze.Maze;

public class MazeValidator {
	static int cnt = 0;
	static Maze maze = new Maze("maps/test.txt");
	static int map[][] = maze.getMap();
//Todo : 다음 규칙에 따라 미로가 제대로 완성되었는지를 확인하는 코드를 만든다.
// 1. (0,0)과 (size_x-1, size_y-1)은 0이 되어야 한다. (시작점, 끝점)
// 2. 시작점과 끝점까지 가는 길은 1가지 이상의 루트가 존재할 수 있다.
// 3. 모든 길 (0으로 설정된 점)은 시작점서부터 도달할 수 있어야 한다.
// option 1, 3겹 이상의 벽은 없도록 한다. (최대한 벽이 두껍지 않게 만들면 좋겠다...
// 0 1 1 1 1 1 1 0
// 0 1 1 0 1 1 1 0
// 0 1 1 0 1 1 1 0

// option 2, 최대한 어색한 벽이 존재하지 않도록.
// 1 0 0 0 0 0 0
// 0 1 0 1 0 1 1
// 0 0 1 0 1 0 0
// 0 0 0 0 0 0 1
// 0 1 1 1 1 0 0
// 0 1 0 0 1 0 0
// 0 1 1 1 1 0 0
// 0 0 0 0 0 0 0
	public void find(int x, int y) {
		if (x == maze.esc_x && y == maze.esc_y) {
			cnt++;
		}
		map[y][x] = 2;
		if (y >= 1 && y < map.length) {
			if (map[y - 1][x] == 0) {
				find(x, y - 1);
			}
		}

		if (x >= 0 && x < map[0].length - 1) {
			if (map[y][x + 1] == 0) {
				find(x + 1, y);
			}
		}
		if (y >= 0 && y < map.length - 1) {
			if (map[y + 1][x] == 0) {
				find(x, y + 1);
			}
		}

		if (x >= 1 && y < map[0].length) {
			if (map[y][x - 1] == 0) {
				find(x - 1, y);
			}
		}
		map[y][x] = 0;

	}

	public void print(int[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				System.out.print(maze[i][j] + " ");
			}
			System.out.println();
		}
	}

	public boolean root(int cnt) {
		if (cnt >= 1)
			return true;
		return false;
	}

	public static void main(String[] args) {
		MazeValidator mv = new MazeValidator();
		mv.print(map);
		mv.find(0, 0);
		System.out.println("탈출 가능한 미로: " + mv.root(cnt));
		System.out.println("경로 개수: " + cnt);
	}
}
