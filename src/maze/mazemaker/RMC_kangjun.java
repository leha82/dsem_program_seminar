package maze.mazemaker;

import java.util.ArrayList;

public class RMC_kangjun {
	static final int N = 20;
	static final int MAPSIZE = N * 2 + 1;
	static block[][] map = new block[MAPSIZE][MAPSIZE];
	static int[][] m = new int[MAPSIZE][MAPSIZE];
	ArrayList<block> list = new ArrayList<block>();

	public RMC_kangjun() {
	}

	public void init() {
		for (int i = 0; i < MAPSIZE; i++) {
			for (int j = 0; j < MAPSIZE; j++) {
				map[i][j] = new block(i, j, false);
			}
		}
		randomRoad();
	}

	class block {
		int x;
		int y;
		boolean visited;

		public block(int y, int x, boolean visited) {
			this.x = x;
			this.y = y;
			this.visited = visited;
		}
	}

	//·£´ý ±æ¶Õ±â
	public void randomRoad() {
		int randX = (int) (Math.random() * N) * 2 + 1;
		int randY = (int) (Math.random() * N) * 2 + 1;
//		map[randY][randX].visited = true;

		System.out.println(randX + "  " + randY + "  " + map.length + " " + N);

		if (randX - 1 != 0) {
			list.add(map[randY][randX - 1]);
		}
		if (randX + 1 != MAPSIZE) {
			list.add(map[randY][randX + 1]);
		}
		if (randY - 1 != 0) {
			list.add(map[randY - 1][randX]);
		}
		if (randY + 1 != MAPSIZE) {
			list.add(map[randY + 1][randX]);
		}

		while (!list.isEmpty()) {
			int index = (int) (Math.random() * list.size());

			block wall = list.get(index);

			if (wall.y % 2 == 1) { // 2nd row
				if (wall.x - 1 != 0 && map[wall.y][wall.x - 1].visited == false) {
					map[wall.y][wall.x - 1].visited = true;
					map[wall.y][wall.x].visited = true;

					if (wall.x - 2 != 0) {
						list.add(map[wall.y][wall.x - 2]);
					}
					if (wall.y - 1 != 0) {
						list.add(map[wall.y - 1][wall.x - 1]);
					}
					if (wall.y + 1 != 0) {
						list.add(map[wall.y + 1][wall.x - 1]);
					}
				} else if (wall.x + 1 <= MAPSIZE - 1 && map[wall.y][wall.x + 1].visited == false) {
					map[wall.y][wall.x + 1].visited = true;
					map[wall.y][wall.x].visited = true;

					if (wall.x + 2 != 0) {
						list.add(map[wall.y][wall.x + 2]);
					}
					if (wall.y - 1 != 0) {
						list.add(map[wall.y - 1][wall.x + 1]);
					}
					if (wall.y + 1 != 0) {
						list.add(map[wall.y + 1][wall.x + 1]);
					}
				}
			} else { // 3rd row
				if (wall.y - 1 != 0 && map[wall.y - 1][wall.x].visited == false) {
					map[wall.y - 1][wall.x].visited = true;
					map[wall.y][wall.x].visited = true;

					if (wall.y - 2 != 0) {
						list.add(map[wall.y - 2][wall.x]);
					}
					if (wall.x - 1 != 0) {
						list.add(map[wall.y - 1][wall.x - 1]);
					}
					if (wall.x + 1 != 0) {
						list.add(map[wall.y - 1][wall.x + 1]);
					}
				} else if (wall.y + 1 <= MAPSIZE - 1 && map[wall.y + 1][wall.x].visited == false) {
					map[wall.y + 1][wall.x].visited = true;
					map[wall.y][wall.x].visited = true;

					if (wall.y + 2 != 0) {
						list.add(map[wall.y + 2][wall.x]);
					}
					if (wall.x - 1 != 0) {
						list.add(map[wall.y + 1][wall.x - 1]);
					}
					if (wall.x + 1 != 0) {
						list.add(map[wall.y + 1][wall.x + 1]);
					}
				}
			}
			list.remove(index);
		}

	}

	// i = xÀÎµ¦½º, j = yÀÎµ¦½º
	public void makeRoom() {
		for (int i = 0; i < MAPSIZE; i++) {
			for (int j = 0; j < MAPSIZE; j++) {
				if (map[i][j].visited == false) {
					//xÀÎµ¦½º = È¦¼ö, yÀÎµ¦½º = Â¦¼ö
					if (i % 2 == 1) {
						if (j % 2 == 0) {
							m[i][j] = 1;
						}
					//xÀÎµ¦½º = Â¦¼ö
					} else {
						m[i][j] = 1;
					}
				}
			}
		}
	}

	
	public void makeMap() {
		//Å×µÎ¸® º®
		for (int i = 0; i < MAPSIZE; i++) {
			m[i][MAPSIZE - 1] = 1;			//¿ìÃø¸é
			m[i][0] = 1;					//ÁÂÃø¸é

			m[MAPSIZE - 1][i] = 1;			//¹Ø¸é
			m[0][i] = 1;					//À­¸é
		}

		m[0][1] = 0;						//½ÃÀÛÁ¡
		m[MAPSIZE - 1][MAPSIZE - 2] = 0;	//³¡Á¡

		//0°ú 1·Î Áöµµ ³ªÅ¸³»±â
		for (int i = 0; i < MAPSIZE; i++) {
			for (int j = 0; j < MAPSIZE; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void printMap() {
		String road = "¡¤";
		String block = "¡á";

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (m[i][j] == 0) {
					System.out.print(road);
				} else if (m[i][j] == 1) {
					System.out.print(block);
				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		RMC_kangjun rmc = new RMC_kangjun();
		rmc.init();
		rmc.makeRoom();
		rmc.makeMap();
		rmc.printMap();


	}
}