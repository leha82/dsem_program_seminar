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
	public Queue<Integer> qx = new LinkedList<Integer>();
	public Queue<Integer> qy = new LinkedList<Integer>();
	public int[] dx = { 0, 1, 0, -1 };
	public int[] dy = { 1, 0, -1, 0 };
	public int[][] visit;

	public MazeValidator() {
		cnt = 0;
		n = 0;
		m = 0;
		maze = new Maze("maps/samplemap100_2.txt");
		map = maze.getMap();
		scanner = new Scanner(System.in);
		visit =  map;
	}
	public MazeValidator(Maze maze) {
		cnt = 0;
		this.maze = maze;
		map = maze.getMap();
		visit =  map;
	}
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
	
	public void bfs(int x, int y) {
		qx.add(x);
		qy.add(y);
		while (!qx.isEmpty() && !qy.isEmpty()) {
			x = qx.poll();
			y = qy.poll();
			for (int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				if (nx >= 0 && ny >= 0 && nx < map.length && ny < map[0].length) {
					if (map[nx][ny] == 0) {
						qx.add(nx);
						qy.add(ny);
						visit[nx][ny] = visit[x][y] + 1;
					}
				}
			}
		}
		
	}

	public int find(int x, int y) {
//		n++;
//		m++;
////		if (m % 100 == 0) {
//			System.out.println();
//
//			if (m % 10000 == 0) {
//				String a = scanner.nextLine();
//			}
//
//			System.out.print(cnt + "|" + m + " : ");
//		}

		if (x == map.length-1 && y ==map[0].length-1) {
//			cnt++;
//			System.out.println(" end : " + cnt);
//			n--;
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

		if (x >= 1 && x < maze.getWidth()) {
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
		System.out.println("end : " + maze.getEsc_x() + ", " + maze.getEsc_y());
	}

	public boolean root(int cnt) {
		if (cnt >= 1)
			return true;
		return false;
	}

	public boolean mapxy() {
		if (map[maze.getStart_y()][maze.getStart_x()] == 0 && map[maze.getEsc_y()][maze.getEsc_x()] == 0)
			return true;
		return false;
	}

	public boolean visited() {
		bfs(0, 0);
		int cnt1 = 0;
		
		System.out.println("(" + maze.getStart_x() + "," + maze.getStart_y() + ") 에서 도달할 수 없는 곳은 :"  );
		
		for (int i = 0; i < visit.length; i++) {
			for (int j = 0; j < visit.length; j++) {
				if (visit[i][j] == 0) {
					cnt1++;
					System.out.print("(" + j + "," + i + ") ");
				}
			}
		}
		if (cnt1 == 0) {
			System.out.println("없습니다.");
			return true;
		}

		System.out.println("입니다.");
		return false;
	}

	public static void main(String[] args) {
		MazeValidator mv = new MazeValidator();
		mv.print();
//		if (mv.mapxy() && mv.root(mv.find(0, 0)) && mv.visited()) {
//			System.out.println("경로 개수: " + mv.cnt);
//		} else {
//			System.out.println("경로가 없습니다.");
//		}
		if (!mv.mapxy() || !mv.visited()) {
			System.out.println("경로가 없습니다.");
		}
		mv.print();
	
	}
}
