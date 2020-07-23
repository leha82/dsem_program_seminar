package maze.mazemaker;

import maze.Maze;
import java.util.*;

public class MazeValidator {
	public int cnt;
	public Maze maze;
	public int map[][];
	public int n;
	public int m;
	public Scanner scanner;
	
	public MazeValidator() {
		cnt = 0;
		n=0;
		m=0;
		maze = new Maze("maps/samplemap100_2.txt");
		map = maze.getMap();
		scanner = new Scanner(System.in);
	}
	
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
	public int find(int x, int y) {
		n++;
		m++;
		if (m%100 == 0) {
			System.out.println();

			if (m%10000 == 0) {
				String a = scanner.nextLine();
			}
			
			System.out.print(cnt + "|" + m + " : ");

		}
				
		System.out.print(x + "," + y + " ");
		
		if (x == maze.getEsc_x() && y == maze.getEsc_y()) {
//			cnt++;
			System.out.println(" end : " + cnt);
			n--;
			return cnt++;
		}
		
		map[y][x] = 2;
		if (y >= 1 && y < maze.getHeight()) {
			if (map[y - 1][x] == 0) {
				find(x, y - 1);
			}
		}

		if (x >= 0 && x < maze.getWidth() - 1) {
			if (map[y][x + 1] == 0) {
				find(x + 1, y);
			}
		}
		if (y >= 0 && y < maze.getHeight() - 1) {
			if (map[y + 1][x] == 0) {
				find(x, y + 1);
			}
		}

		if (x >= 1 && y < maze.getWidth()) {
			if (map[y][x - 1] == 0) {
				find(x - 1, y);
			}
		}
		n--;
		map[y][x] = 0;
		return cnt;
	}

	public void print() {
		for (int i = 0; i < maze.getHeight(); i++) {
			for (int j = 0; j < maze.getWidth(); j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("size : " + maze.getWidth() + ", " + maze.getHeight());
		System.out.println("start : " + maze.getStart_x() + ", " + maze.getStart_y());
		System.out.println("start : " + maze.getEsc_x() + ", " + maze.getEsc_y());
	}

	public boolean root(int cnt) {
		if (cnt >= 1)
			return true;
		return false;
	}
	
	public boolean mapxy() {
		if(map[maze.getStart_y()][maze.getStart_x()] == 0 && map[maze.getEsc_y()][maze.getEsc_x()]==0)
			return true;
		return false;
	}
	
	public static void main(String[] args) {
		MazeValidator mv = new MazeValidator();
		mv.print();
		if(mv.mapxy() && mv.root(mv.find(0, 0))) {
			System.out.println("Ż�� ������ �̷�");
			System.out.println("��� ����: " + mv.cnt);
		}
	}
}
