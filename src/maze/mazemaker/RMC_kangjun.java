package maze.mazemaker;

import java.util.ArrayList;

public class RMC_kangjun extends RandomMapCreator {
	static final int N = 50;
	static final int MAPSIZE = N * 2 + 1;
	static block[][] mapBlock = new block[MAPSIZE][MAPSIZE];
//	static int[][] map = new int[MAPSIZE][MAPSIZE];
	ArrayList<block> list = new ArrayList<block>();

	public RMC_kangjun() {
		mapName = "RMCKangjun101";

		x_size = MAPSIZE;
		y_size = MAPSIZE;
		
		map = new int[MAPSIZE][MAPSIZE];
	}

	public void init() {
		for (int i = 0; i < MAPSIZE; i++) {
			for (int j = 0; j < MAPSIZE; j++) {
				mapBlock[i][j] = new block(i, j, false);
			}
		}
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

		System.out.println(randX + "  " + randY + "  " + mapBlock.length + " " + N);

		if (randX - 1 != 0) {
			list.add(mapBlock[randY][randX - 1]);
		}
		if (randX + 1 != MAPSIZE) {
			list.add(mapBlock[randY][randX + 1]);
		}
		if (randY - 1 != 0) {
			list.add(mapBlock[randY - 1][randX]);
		}
		if (randY + 1 != MAPSIZE) {
			list.add(mapBlock[randY + 1][randX]);
		}

		while (!list.isEmpty()) {
			int index = (int) (Math.random() * list.size());

			block wall = list.get(index);

			if (wall.y % 2 == 1) { // 2nd row	xÃà ·£´ý
				if (wall.x - 1 != 0 && mapBlock[wall.y][wall.x - 1].visited == false) {
					mapBlock[wall.y][wall.x - 1].visited = true;
					mapBlock[wall.y][wall.x].visited = true;

					if (wall.x - 2 != 0) {
						list.add(mapBlock[wall.y][wall.x - 2]);
					}
					if (wall.y - 1 != 0) {
						list.add(mapBlock[wall.y - 1][wall.x - 1]);
					}
					if (wall.y + 1 != 0) {
						list.add(mapBlock[wall.y + 1][wall.x - 1]);
					}
				} else if (wall.x + 1 <= MAPSIZE - 1 && mapBlock[wall.y][wall.x + 1].visited == false) {
					mapBlock[wall.y][wall.x + 1].visited = true;
					mapBlock[wall.y][wall.x].visited = true;

					if (wall.x + 2 != 0) {
						list.add(mapBlock[wall.y][wall.x + 2]);
					}
					if (wall.y - 1 != 0) {
						list.add(mapBlock[wall.y - 1][wall.x + 1]);
					}
					if (wall.y + 1 != 0) {
						list.add(mapBlock[wall.y + 1][wall.x + 1]);
					}
				}
			} else { // 3rd row  yÃà ·£´ý
				if (wall.y - 1 != 0 && mapBlock[wall.y - 1][wall.x].visited == false) {
					mapBlock[wall.y - 1][wall.x].visited = true;
					mapBlock[wall.y][wall.x].visited = true;

					if (wall.y - 2 != 0) {
						list.add(mapBlock[wall.y - 2][wall.x]);
					}
					if (wall.x - 1 != 0) {
						list.add(mapBlock[wall.y - 1][wall.x - 1]);
					}
					if (wall.x + 1 != 0) {
						list.add(mapBlock[wall.y - 1][wall.x + 1]);
					}
				} else if (wall.y + 1 <= MAPSIZE - 1 && mapBlock[wall.y + 1][wall.x].visited == false) {
					mapBlock[wall.y + 1][wall.x].visited = true;
					mapBlock[wall.y][wall.x].visited = true;

					if (wall.y + 2 != 0) {
						list.add(mapBlock[wall.y + 2][wall.x]);
					}
					if (wall.x - 1 != 0) {
						list.add(mapBlock[wall.y + 1][wall.x - 1]);
					}
					if (wall.x + 1 != 0) {
						list.add(mapBlock[wall.y + 1][wall.x + 1]);
					}
				}
			}
			list.remove(index);
		}
	}

	// i = yÀÎµ¦½º, j = xÀÎµ¦½º
	public void makeRoom() {
		System.out.println();
		System.out.println("Make Room");

		
		for (int i = 0; i < MAPSIZE; i++) {
			for (int j = 0; j < MAPSIZE; j++) {
				if (mapBlock[i][j].visited == false) {
					System.out.print("¡á ");
				} else {
					System.out.print(". ");
				}
					
				if (mapBlock[i][j].visited == false) {
					map[i][j] = 1;
					
					//yÀÎµ¦½º = È¦¼ö, xÀÎµ¦½º = Â¦¼ö
//					if (i % 2 == 1) {
//						if (j % 2 == 0) {
//							m[i][j] = 1;
//						}
					//yÀÎµ¦½º = Â¦¼ö
//					} else {
//						m[i][j] = 1;
//					}
				}
			}
			System.out.println();
		}
	}

	public void makeMap() {
		//Å×µÎ¸® º®
//		for (int i = 0; i < MAPSIZE; i++) {
//			m[i][MAPSIZE - 1] = 1;			//¿ìÃø¸é
//			m[i][0] = 1;					//ÁÂÃø¸é
//
//			m[MAPSIZE - 1][i] = 1;			//¹Ø¸é
//			m[0][i] = 1;					//À­¸é
//		}

		map[0][1] = 0;						//½ÃÀÛÁ¡
		map[MAPSIZE - 1][MAPSIZE - 2] = 0;	//³¡Á¡

		start_x = 1;
		start_y = 0;
		esc_x = MAPSIZE - 2;
		esc_y = MAPSIZE - 1;
				
		System.out.println();
		System.out.println("Make Map");
		
		//0°ú 1·Î Áöµµ ³ªÅ¸³»±â
		for (int i = 0; i < MAPSIZE; i++) {
			for (int j = 0; j < MAPSIZE; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void printMap() {
		String road = "¡¤";
		String block = "¡á";

		for (int i = 0; i < mapBlock.length; i++) {
			for (int j = 0; j < mapBlock[i].length; j++) {
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
		RMC_kangjun rmc = new RMC_kangjun();
		rmc.init();
		rmc.randomRoad();
		rmc.makeRoom();
		rmc.makeMap();
		rmc.printMap();
		rmc.makeMapFile();
	}
}