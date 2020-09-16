package maze.mazemaker;

public class RandomMapCreator {
	private String mapName;
	private int x_size;
	private int y_size;
	private int[][] map;

	public RandomMapCreator() {
		this("",0,0);
	}

	public RandomMapCreator(String mapName, int x_size, int y_size) {
		this.mapName = mapName;
		this.x_size = x_size;
		this.y_size = y_size;
		this.map = new int[y_size][x_size];
	}
	
<<<<<<< HEAD
=======
	
	public void createMap() {
		// Todo: ���⿡�� map�� �����Ѵ�.
		
	}

	public static void main(String[] args) {
		RandomMapCreator rmc = new RandomMapCreator("NewMap", 10, 10);

		rmc.createMap();
		rmc.printMap();
		rmc.makeMapFile();
	}

	public void makeMapFile() {
	// Todo : �� ���Ϸ� ���� testmap.txt�� ������ ��������.
	// ���Ϸ� �����Ҷ� maps ������ .txtȮ���ڸ� �ٿ��� ���Ϸ� ������ ��
		// this.mapName Ȱ��
		String filename = "maps\\" + mapName + ".txt";

		int index = 0;
		boolean loop = true;
		while(loop) {
			File f = new File(filename);
			if (f.exists()) {
				index++;
				filename = "maps\\" + mapName + "_" + index + ".txt";
			} else {
				loop = false;
			}
		}
		
		try {
			FileWriter w = new FileWriter(filename);
			
			w.write(x_size + " " + y_size + "\n");
			w.write(start_x + " " + start_y + "\n");
			w.write(esc_x + " " + esc_y + "\n");
			for (int i=0;i<y_size;i++) {
				for (int j=0;j<x_size;j++) {
					w.write(map[i][j] + " ");
				}
				w.write("\n");
			}
			w.close();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printMap() {
		String road = "��";
		String block = "��";

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

>>>>>>> branch 'master' of https://github.com/leha82/dsem_program_seminar.git
	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public int getX_size() {
		return x_size;
	}

	public void setX_size(int x_size) {
		this.x_size = x_size;
	}

	public int getY_size() {
		return y_size;
	}

	public void setY_size(int y_size) {
		this.y_size = y_size;
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}
	
	public void createMap() {
		
	}
	
	public static void main(String[] args) {
		RandomMapCreator rmc = new RandomMapCreator("NewMap", 10,10);
		
		rmc.createMap();
		rmc.printMap();
	}

	public void printMap() {
		String road = "��";
		String block = "��";
		
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
	
}
