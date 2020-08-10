package maze.mazemaker;

import java.util.Random;

public class RMC_YBH {
	protected String mapName;
	protected int x_size;
	protected int y_size;
	protected int start_x, start_y;
	protected int esc_x, esc_y;

	protected int[][] map;

	public RMC_YBH(String mapName, int x_size, int y_size) {
		this.mapName = mapName;
		this.x_size = x_size;
		this.y_size = y_size;
		this.map = new int[y_size][x_size];

		this.start_x = 0;
		this.start_y = 0;
		this.esc_x = x_size - 1;
		this.esc_y = y_size - 1;
	}

	static Random rd = new Random();

	public static int[][] chamber(int x, int y, int c_map[][]) {
		if (x <= 2 || y <= 2)
			return c_map;
		// 십자선을 못 그을 정도로작으면 리턴
		int[][] a;
		int mid_x = x / 2, mid_y = y / 2;

		int i, j; // 미로비워두기
		for (i = 0; i < c_map.length; i++) {
			for (j = 0; j < c_map[i].length; j++)
				c_map[i][j] = 0;
		}
		// 십자모양 벽만들기
		for (i = 0; i < c_map[0].length; i++) {
			c_map[mid_y][i] = 1;
		}
		for (i = 0; i < c_map.length; i++) {
			c_map[i][mid_x] = 1;
		}

		// 생긴 벽 중에 3개 선택후 구멍 뚫기
		int rdn = rd.nextInt(4);
		int n;
		switch (rdn) {
		case (0): {
			rdn = rd.nextInt(2);
			n = rdn == 0 ? 0 : mid_y - 1;
			c_map[n][mid_x] = 0;
			rdn = rd.nextInt(2);
			n = rdn == 0 ? 0 : mid_x - 1;
			c_map[mid_y][n] = 0;
			rdn = rd.nextInt(2);
			n = rdn == 0 ? mid_y + 1 : y - 1;
			c_map[n][mid_x] = 0;
			break;
		}
		case (1): {// 제 3사분면과 제4사분면 벽을 제외하고 뚫기
			rdn = rd.nextInt(2);
			n = rdn == 0 ? 0 : mid_x - 1;
			c_map[mid_y][n] = 0; // 2,3 wall
			rdn = rd.nextInt(2);
			n = rdn == 0 ? 0 : mid_y - 1;
			c_map[n][mid_x] = 0; // 1,2 wall
			rdn = rd.nextInt(2);
			n = rdn == 0 ? mid_x + 1 : x - 1;
			c_map[mid_y][n] = 0; // 1,4 wall
			break;

		}
		case (2): {// 제 4사분면과 제 1사분면 벽을 제외하고 뚫기
			rdn = rd.nextInt(2);
			n = rdn == 0 ? 0 : mid_x - 1;
			c_map[mid_y][n] = 0; // 2,3 wall
			rdn = rd.nextInt(2);
			n = rdn == 0 ? 0 : mid_y - 1;
			c_map[n][mid_x] = 0; // 1,2 wall
			rdn = rd.nextInt(2);
			n = rdn == 0 ? mid_y + 1 : y - 1;
			c_map[n][mid_x] = 0; // 3,4 wall
			break;

		}
		case (3): {
			// 제 2사분면과 제 1사분면 벽을제외하고 뚫기
			rdn = rd.nextInt(2);
			n = rdn == 0 ? 0 : mid_x - 1;
			c_map[mid_y][n] = 0; // 2,3 wall
			rdn = rd.nextInt(2);
			n = rdn == 0 ? mid_x + 1 : x - 1;
			c_map[mid_y][n] = 0; // 1,4 wall
			rdn = rd.nextInt(2);
			n = rdn == 0 ? mid_y + 1 : y - 1;
			c_map[n][mid_x] = 0; // 3,4 wall break;

		}
		}

		// 작게 잘라서 다시
		a = chamber(x - mid_x - 1, mid_y, new int[mid_y][x - mid_x - 1]); // 제 1분면
		for (i = 0; i < a.length; i++) {
			for (j = 0; j < a[i].length; j++)
				c_map[i][mid_x + j + 1] = a[i][j];
		} // 합치기
		a = chamber(mid_x, mid_y, new int[mid_y][mid_x]); // 제 2사분면
		for (i = 0; i < a.length; i++) {
			for (j = 0; j < a[i].length; j++)
				c_map[i][j] = a[i][j];
		}
		// 합치기
		a = chamber(mid_x, y - mid_y - 1, new int[y - mid_y - 1][mid_x]); // 제 3사분면
		for (i = 0; i < a.length; i++) {
			for (j = 0; j < a[i].length; j++)
				c_map[mid_y + i + 1][j] = a[i][j];
		} // 합치기
		a = chamber(x - mid_x - 1, y - mid_y - 1, new int[y - mid_y - 1][x - mid_x - 1]); // 제 4사분면
		for (i = 0; i < a.length; i++) {
			for (j = 0; j < a[i].length; j++)
				c_map[mid_y + i + 1][mid_x + j + 1] = a[i][j];
		}
		// 합치기
		return c_map;
	}

	public void createMap() {
		map = chamber(this.x_size, this.y_size, map);

	}

	public void printMap() {
		String road = "·";
		String block = "■";

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0) {
					System.out.print(road);
				} else if (map[i][j] == 1) {
					System.out.print(block);
				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		RMC_YBH rmc = new RMC_YBH("NewMap", 30, 30);

		rmc.createMap();
		rmc.printMap();
	}

}
