package maze.mazemaker;

import maze.Maze;

public class MazeValidator {
	static int cnt = 0;
	static Maze maze = new Maze("maps/test.txt");
	static int map[][] = maze.getMap();
//Todo : ���� ��Ģ�� ���� �̷ΰ� ����� �ϼ��Ǿ������� Ȯ���ϴ� �ڵ带 �����.
// 1. (0,0)�� (size_x-1, size_y-1)�� 0�� �Ǿ�� �Ѵ�. (������, ����)
// 2. �������� �������� ���� ���� 1���� �̻��� ��Ʈ�� ������ �� �ִ�.
// 3. ��� �� (0���� ������ ��)�� ������������ ������ �� �־�� �Ѵ�.
// option 1, 3�� �̻��� ���� ������ �Ѵ�. (�ִ��� ���� �β��� �ʰ� ����� ���ڴ�...
// 0 1 1 1 1 1 1 0
// 0 1 1 0 1 1 1 0
// 0 1 1 0 1 1 1 0

// option 2, �ִ��� ����� ���� �������� �ʵ���.
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
		System.out.println("Ż�� ������ �̷�: " + mv.root(cnt));
		System.out.println("��� ����: " + cnt);
	}
}
