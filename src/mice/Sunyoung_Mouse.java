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

	// Mouse�� �������� ������ ������ ���Ѵ�.
	// 0: ���ڸ�
	// 1: ����
	// 2: ������
	// 3: �Ʒ���
	// 4: ����
	public int nextMove(int x, int y, int[][] smap) {
		int dir = 0;

		if (smap[1][2] == 0) { // ������
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
		} else if (smap[1][2] != 0 && smap[2][1] == 0) { // �Ʒ���
			if (new_maze[y][x] == 0) {// �ѹ��� �Ȱ����̶�� new_maze�� 1�� �ٲٷ� �Ʒ���
				new_maze[y][x] = 1;
				dir = 3;
			} else if (new_maze[y][x] == 1 && smap[1][0] == 0) {// �̹� �����̰� ���ʷ� �� �� �ִٸ� ��������
				new_maze[y][x] = 2;
				dir = 4;
			} else if (new_maze[y][x] == 1 && smap[0][1] == 0) {// �̹� �����̰� ���ʿ� �� �� ������ ����
				new_maze[y][x] = 2;
				dir = 1;
			}

		} else if (smap[1][2] != 0 && smap[2][1] != 0 && smap[1][0] == 0) {// ����
			if (new_maze[y][x] == 0) {
				new_maze[y][x] = 1;
				dir = 4;
			} else if (new_maze[y][x] == 1 && smap[0][1] == 0) {// �̹� �����̰� ���ʿ� �� �� ������ ����
				new_maze[y][x] = 2;
				dir = 1;
			}

		} else if (smap[1][2] != 0 && smap[2][1] != 0 && smap[1][0] != 0 && smap[0][1] == 0) {// ����
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
